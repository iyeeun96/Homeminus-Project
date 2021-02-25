package com.service;

import java.util.List;
import java.util.Scanner;

import com.dao.ProductDaoImpl;
import com.dto.EmployeeDto;
import com.dto.ProductDto;

public class ProductService {//Dao만 출력/스캐너 x  view에서 return값으로 controller로 보냄 
	Scanner scan = new Scanner(System.in);

	//Dao 인스턴스 사용
	ProductDaoImpl pDao = null;
	public ProductService() {//DAO 인스턴스 주소 얻어오기
		pDao = ProductDaoImpl.getInstance();
	}
	//로그인 처리용 메소드
	//비교 처리만!, DB에서 데이터를 가져오는 작업은 DAO가!
	public int loginCheck(EmployeeDto member) {
		int result = 0;
		//DAO로부터 패스워드 얻어오기
		String pass = pDao.selectPass(member);
		//로그인 처리
		if(pass != null) {
			if(pass.equals(member.getE_pass())) {
				//로그인 성공 
				result = 0;
			}
			else {
				//패스워드가 틀린 경우
				result = 10;//오류 코드
			}
		}
		else {
			//입력한 ID가 DB에 없는 경우
			result = 9;
		}
		return result;
	}//loginCheck end

	//검색한 제품 정보 가져오기
	public ProductDto ProductInfo() {
		Scanner scan = new Scanner(System.in);
		String search = scan.nextLine();
		ProductDto product = pDao.selectPro(search);
		return product;
	}

	//제품 전체 정보 가져오기
	public List<ProductDto> productList() {
		List<ProductDto> pList = null;
		//DAO의 DB에서 회원 전체 정보를  가져오는 메소드 실행
		pList = pDao.selectList();
		return pList;
	}//productList end

	public ProductDto pIofo(String code) {
		ProductDto product = pDao.selectPro(code);
		return product;
	}
	
	public int updateInfo(ProductDto product) {
		int result = -1;
		//DB에 수정 작업은 DAO가 한다.
		result = pDao.updateProduct(product);
		if(result > 0) {
			result = 4;
		}
		else {
			result = 7;
		}
		return result;
	}//updateInfo end

	public int regProduct(ProductDto product) {
		int result = -1;
		//DAO로 DB에 insert 처리. (insert 용 메소드 사용)
		result = pDao.insertProduct(product);
		//result는 insert 성공시 1, 실패시 0
		if(result >0) {
			result =4;//DB저장 성공 메시지 코드
		}
		else {
			result = 7;//DB저장 실패 메시지 코드
		}
		return result;
	}

	public int insertInfo(ProductDto product) {
		int result = -1;
		//DB에 수정 작업은 DAO가 한다.
		result = pDao.insertProduct(product);
		if(result >0) {
			result = 4;
		}
		else {
			result = 7;
		}
		return result;
	}

	public int delProduct(String delPro) {
		int result = 0;
		//DAO로 삭제처리하고 처리 건수를 받는다.
		result = pDao.deleteProduct(delPro);

		if(result > 0) {
			result = 5;//삭제 성공 메시지
		}
		else {
			result = 8;//삭제 실패 메시지
		}
		return result;
	}

	public void delEmployee() {
		System.out.println("삭제할 직원의 아이디를 입력하세요.");
		System.out.print("ID : ");
		String delId = scan.nextLine();
		pDao.deleteEmployee(delId);
		System.out.println("데이터가 삭제되었습니다.");
	}

	public void inpEmployee() {
		String gMenu = null;
		EmployeeDto eDto = new EmployeeDto();
		System.out.print("ID : ");
		eDto.setE_id(scan.nextLine());
		System.out.print("PASS : ");
		eDto.setE_pass(scan.nextLine());
		System.out.print("NAME : ");
		eDto.setE_name(scan.nextLine());
		System.out.println(" 직급 선택\n 1.관리자, 2.알바");
		System.out.print("GRADE : ");
		gMenu = gMenu();
		eDto.setE_grade(gMenu);
		System.out.println("등록되었습니다.");
		System.out.println();
		System.out.println(eDto);
		pDao.insertEmployee(eDto);
	}


	public List<EmployeeDto> outEmployee(){
		List<EmployeeDto> pList = null;
		pList = pDao.selectAllList();
		return pList;
	}

	public int updateInfo(EmployeeDto empl) {
		int result = -1;

		result = pDao.updateEmployee(empl);

		if(result > 0) {
			System.out.println("직원정보 수정 완료");
		}
		else {
			System.out.println("직원정보 수정 실패");
		}
		return result;
	}
	
	public EmployeeDto employeeInfo(String mid) {
		EmployeeDto empl = pDao.selectInfo2(mid);
		return empl;
	}

	public String inputUpdateUser() {
		System.out.println("수정할 직원의 ID를 입력해주세요.");
		String user = scan.nextLine();
		return user;
	}
	
	public String gMenu() {
	      String gMenu = null;
	      int menu = scan.nextInt();
	      scan.nextLine();
	      switch(menu) {
	      case 1:
	         gMenu = "관리자";
	         break;
	      case 2:
	         gMenu = "알바";
	         break;
	      }
	      return gMenu;
	   }
	
	public EmployeeDto eIofo(String mid) {
	      EmployeeDto member = pDao.selectInfo2(mid);
	      return member;
	   }
	public int delEmployee(String delId) {
	      int result = 0;

	      //DAO로 삭제처리하고 처리 건수를 받는다.
	      result = pDao.deleteEmployee(delId);
	      if(result > 0) {
	         result = 5;//삭제 성공 메시지
	      }
	      else {
	         result = 8;//삭제 실패 메시지
	      }

	      return result;
	   }//delMember end
}//class end

