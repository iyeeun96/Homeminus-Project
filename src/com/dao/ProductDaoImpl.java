package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.EmployeeDto;
import com.dto.ProductDto;

public class ProductDaoImpl implements ProductDao{
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "DEV";
	private String password = "1234";
	//Connection, PreparedStatement, ResultSet 등
	Connection conn = null;
	PreparedStatement pstmt =null;
	ResultSet rs = null;

	private static ProductDaoImpl pDao = null;

	//드라이버 로드 작업은 생성자로 처리
	public ProductDaoImpl() {//생성자 메소드는 클래스와 같은 이름으로 만들기
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
		}
	}
	//인스턴스를 넘겨주는 메소드
	public static ProductDaoImpl getInstance() {
		if(pDao == null) {//아직 인스턴스가 만들어지지 않았을때 
			pDao = new ProductDaoImpl();//새 인스턴스 생성	
		}
		return pDao;//null이 아니면 기존의 것을 넘겨준다.
	}
	//ID에 해당하는 비밀번호 조회
	@Override
	public String selectPass(EmployeeDto emp) {
		//DB에서 해당 ID(입력값)에 대한 PASS 컬럼의 값을 조회
		String result = null;
		//SQL 쿼리문
		String query = "SELECT e_pass, e_level FROM employee WHERE e_id=?";
		try {
			//접속
			conn = DriverManager.getConnection(url, user, password);
			//SQL 실행 처리
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, emp.getE_id());
			rs = pstmt.executeQuery();//executeQuery는 무조건 Resultset으로 결과값이 넘어온다.

			//로그인의 경우 결과 1개 또는 없음. while로 반복할필요x
			//rs.next() 결과는 값이 있으면 true or 없으면 false 			
			if(rs.next()) {
				result = rs.getString(1);
				//result = rs.getString("m_pass");
				emp.setE_grade(rs.getString(2));
			}

		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("DB 처리 실패!");
		} finally {
			close();
		}
		return result;
	}//selectPass end

	//해제 관련 메소드(Connection, PreparedStatement, ResultSet Close)
	private void close() {
		try {
			if (rs!=null) rs.close();
			if (pstmt!=null) pstmt.close();
			if (conn!=null) conn.close();
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}//close end

	//code에 맞는 쿼리 가져오기
	public ProductDto selectInfo(String mid) {
		String query = "SELECT * FROM product WHERE p_code=?";
		ProductDto product = null;

		try {
			//접속
			conn = DriverManager.getConnection(url, user, password);
			//쿼리실행
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mid);

			rs = pstmt.executeQuery();

			//결과처리(결과는 1행이거나 없거나)
			if(rs.next()) {
				product = new ProductDto();//정보저장 인스턴스
				product.setP_kind(rs.getString("p_kind"));//카테고리
				product.setP_code(rs.getString("p_code"));//제품코드
				product.setP_name(rs.getString("p_name"));//제품명
				product.setP_price(rs.getInt("p_price"));//제품가격
				product.setP_amount(rs.getInt("p_amount"));//제품수량
				product.setP_exp(rs.getString("p_exp"));//날짜(유통기한&as기간)
				product.setP_use(rs.getString("p_use"));//용도


			}//if end

		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("DB 처리 실패!");
		} finally {
			//접속해제
			close();
		}
		return product;
	}//selectInfo end


	//제품 검색
	@Override
	public ProductDto selectPro(String search) {
		String query = "SELECT * FROM product WHERE p_code = ?";

		ProductDto product = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, search);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				product = new ProductDto();
				product.setP_kind(rs.getString(1));
				product.setP_code(rs.getString(2));
				product.setP_name(rs.getString(3));
				product.setP_price(rs.getInt(4));
				product.setP_amount(rs.getInt(5));
				if(product.getP_kind().equals("식품") ||
						product.getP_kind().equals("가전제품")) product.setP_exp(rs.getString(6));
				if(product.getP_kind().equals("생활필수품")) product.setP_use(rs.getString(7));
			}
		} catch (SQLException e) {
			
		} finally {
			close();
		}
		return product;
	}//selectPro end

	//제품 수정
	@Override
	public int updateProduct(ProductDto product) {
		int result = 0;

		String query = "UPDATE product SET p_kind = ?, p_name = ?, p_price = ?, p_amount = ?,"
				+ " p_exp = ?, p_use = ? WHERE p_code = ?";

		try {
			conn = DriverManager.getConnection(url, user, password);

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, product.getP_kind());
			pstmt.setString(2, product.getP_name());
			pstmt.setInt(3, product.getP_price());
			pstmt.setInt(4, product.getP_amount());
			String date = null; 
			if(!product.getP_kind().equals("생활필수품")) {
				date = product.getP_exp().substring(0, 10);
			}
			pstmt.setString(5, date);
			pstmt.setString(6, product.getP_use());
			pstmt.setString(7, product.getP_code());

			result = pstmt.executeUpdate();
			conn.commit();         
		} catch(SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				//e1.printStackTrace();
			}

		} finally {
			close();
		}
		return result;
	}//updateProduct end

	//List에 DB 정보 저장하기
	@Override
	public List<ProductDto> selectList() {
		//제품 정보 목록 저장 List 
		ArrayList<ProductDto>pList = new ArrayList<ProductDto>();

		//쿼리
		String query = "select * from product order by p_code";

		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(query);
			rs= pstmt.executeQuery();

			while(rs.next()) {
				ProductDto p = new ProductDto();//1명의 정보 저장 인스턴스
				//만들어서 정보를 하나하나 저장하고 나서
				p.setP_kind(rs.getString(1));
				p.setP_code(rs.getString(2));
				p.setP_name(rs.getString(3));
				p.setP_price(rs.getInt(4));
				p.setP_amount(rs.getInt(5));
				p.setP_exp(rs.getString(6));
				p.setP_use(rs.getString(7));
				//하나하나 정보를 mList배열에 그대로 추가
				pList.add(p);
			}
		} catch(SQLException e) {

		} finally {
			close();
		}
		return pList;
	}//selectList end

	//제품 등록 
	@Override
	public int insertProduct(ProductDto product) {
		int result = 0;
		String query = "INSERT INTO PRODUCT VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, product.getP_kind());
			pstmt.setString(2, product.getP_code());
			pstmt.setString(3, product.getP_name());
			pstmt.setInt(4, product.getP_price());
			pstmt.setInt(5, product.getP_amount());
			pstmt.setString(6, product.getP_exp());
			pstmt.setString(7, product.getP_use());

			result = pstmt.executeUpdate();
			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				//e1.printStackTrace();
			}
		} finally {
			close();
		}
		return result;

	}
	//제품 삭제
	@Override
	   public int deleteProduct(String delPro) {
	      int result = 0;
	      
	      String query = "DELETE FROM product WHERE p_code = ?";
	      
	      try {
	         conn = DriverManager.getConnection(url, user, password);
	         
	         pstmt = conn.prepareStatement(query);
	         pstmt.setString(1, delPro);
	         result = pstmt.executeUpdate();
	         conn.commit();
	         
	      } catch (SQLException e) {
	         //e.printStackTrace();
	         try {
	            conn.rollback();
	         } catch (SQLException e1) {
	            //e1.printStackTrace();
	         }
	      } finally {
	         close();
	      }
	      
	      return result;
	   }//deleteMember end
	
	
@Override
   public int insertEmployee(EmployeeDto empl) {
      int result = 0;
      String query = "insert into employee values (?,?,?,?)";


      try {
         conn = DriverManager.
               getConnection(url,user,password);
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, empl.getE_id());
         pstmt.setString(2, empl.getE_pass());
         pstmt.setString(3, empl.getE_name());
         pstmt.setString(4, empl.getE_grade());

         result = pstmt.executeUpdate();

         conn.commit();
      } catch (SQLException e) {
         System.out.println("sql접속실패했습니다. 롤백합니다.");
         try {
            conn.rollback();
         } catch (SQLException e1) {
            // TODO Auto-generated catch block
            //e1.printStackTrace();
         }
      }finally {
         close();
      }
      return result;
   }

   @Override
   public int deleteEmployee(String delId) {
      String query = "delete from employee where e_id = ? ";
      int result = 0;

      try {
         conn = DriverManager.getConnection(url, user, password);
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, delId);
         result = pstmt.executeUpdate();//데이터베이스에서 넘어오는
         //값은 숫자값임.
         conn.commit();

      } catch (SQLException e) {
         try {
            conn.rollback();
         } catch (SQLException e1) {
         }
      }

      // TODO Auto-generated method stub
      return result;
   }

   @Override
   public List<EmployeeDto> selectAllList() {
      ArrayList<EmployeeDto> eList 
      = new ArrayList<EmployeeDto>();

      String query = "select * from employee";

      try {
         conn = DriverManager.getConnection(url,user,password);

         pstmt = conn.prepareStatement(query);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            EmployeeDto e = new EmployeeDto();
            e.setE_id(rs.getString(1));//pList에 rs테이블의 1번째
            //컬럼의 데이터를 pList 배열에 입력
            e.setE_pass(rs.getString(2));
            e.setE_name(rs.getString(3));
            e.setE_grade(rs.getString(4));

            eList.add(e);
         }
      } catch (SQLException e) {
         System.out.println("드라이버가 연결되지 않았습니다.");
      } finally {
         close();
      }
      return eList;
   }


public int updateEmployee(EmployeeDto empl) {
      int result = 0;
      
      String query = "update employee set e_pass=?,"
            + "e_name = ?, e_level = ? where e_id = ?";
      try {
         conn = DriverManager.getConnection(url, user, password);
         
         pstmt = conn.prepareStatement(query);
         
         pstmt.setString(1, empl.getE_pass());
         pstmt.setString(2, empl.getE_name());
         pstmt.setString(3, empl.getE_grade());
         pstmt.setString(4, empl.getE_id());
         
         result = pstmt.executeUpdate();
         conn.commit();
      } catch (SQLException e) {
         System.out.println("데이터 입력 실패");
         try {
            conn.rollback();
         } catch (SQLException e1) {
            System.out.println("롤백 실패");
         }
      }finally {
         close();
      }
      return result;
   }
	
   public EmployeeDto selectInfo2(String mid) {
      String query = "select * from employee where e_id = ?";
      
      EmployeeDto empl = null;
      
      try {
         conn = DriverManager.getConnection(url, user, password);
         
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, mid);
         
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            empl = new EmployeeDto();
            empl.setE_id(mid);
            empl.setE_pass(rs.getString(2));
            empl.setE_name(rs.getString(3));
            empl.setE_grade(rs.getNString(4));
         }
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         //e.printStackTrace();
      }finally {
         close();
      }
      return empl;
   }
}//class end
