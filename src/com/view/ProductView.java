package com.view;

import java.util.List;
import java.util.Scanner;

import com.dao.ProductDaoImpl;
import com.dto.EmployeeDto;
import com.dto.ProductDto;
import com.service.ProductService;

public class ProductView {//만든 클래스 안들어감
	private Scanner scan = new Scanner(System.in);
	ProductDaoImpl pDaoI = new ProductDaoImpl();
	ProductService pServ = new ProductService();

	//1. 로그인 화면 출력 및 입력 메소드
	// ID와 PASS에 입력값이 없으면 다시 입력 받기.
	public void loginInput(EmployeeDto member){
		while(true) {
			System.out.println("======[홈마이너스 제품 관리 로그인]======");
			System.out.print("ID : ");
			member.setE_id(scan.nextLine());
			//사용자가 ID를 입력했는지 여부 확인
			if(member.getE_id().equals("")) {
				System.out.println("ID를 입력하세요.");
				continue;//if가 참이면 아래문이 생략되고 다시 처음으로 돌아감.
			}
			System.out.print("Password : ");
			member.setE_pass(scan.nextLine());
			if(member.getE_pass().equals("")) {
				System.out.println("Password를 입력하세요.");
				continue;
			}
			else {//ID, PASS 정상 입력되면 반복 종료.
				break;
			}
		}
	}//loginInput end

	//2. 타이틀과 메인 메뉴를 출력하는 메소드
	public void showMenu(String user) {
		System.out.println();
		System.out.println("====[홈마이너스 제품 관리 프로그램]====");
		System.out.println("메뉴 > ");
		System.out.println("1. 제품 출력");
		System.out.println("2. 제품 검색");
		if(user.equals("관리자")) {
			System.out.println("3. 제품 등록");
			System.out.println("4. 제품 수정");
			System.out.println("5. 제품 삭제");
			System.out.println("6. 직원 관리");
		}
		System.out.println("0. 종료");
		System.out.println("================================");
	}//showMenu end
	//3. 카테고리 메뉴를 출력하는 메소드
	public void subMenu() {
		System.out.println("=====[카테고리 선택]=====");
		System.out.println("1. 가전제품");
		System.out.println("2. 식품");
		System.out.println("3. 생활필수품");
		System.out.println("0. 이전 메뉴로 돌아가기");
		System.out.println("=====================");
	}
	//4. 출력 메뉴 메소드
	public void printMenu() {
		System.out.println("======[출력 메뉴]======");
		System.out.println("1. 전체출력");
		System.out.println("2. 카테고리별 출력");
		System.out.println("0. 이전 메뉴로 돌아가기");
		System.out.println("====================");
	}
	//5. 숫자 입력 메소드
	public int inputNumber (String str) {
		int num = -1;
		while(true) {
			System.out.print(str);
			try {
				num = Integer.parseInt(scan.nextLine());
				break;//
			} catch (NumberFormatException e) {
				//System.out.println("잘못 입력하셨습니다.");
				printMsg(2);
			}
		}
		return num;
	}
	//6. 메시지 출력용 메소드
	public void printMsg(int m) {
		switch(m) {
		case 0 : //프로그램 종료 메시지
			System.out.println("프로그램 종료");
			break;
		case 1 ://이전메뉴 돌아가기
			System.out.println("이전 메뉴로 돌아갑니다.");
			break;
		case 2 : //메뉴 번호 입력 오류
			System.out.println("잘못 입력하셨습니다.");
			break;
		case 3 : //데이터 없음 메시지
			System.out.println("저장된 데이터가 없습니다.");
			break;
		case 4 : //저장 완료 메시지
			System.out.println("저장 완료");
			break;
		case 5: //삭제 완료 메시지
			System.out.println("삭제 완료");
			break;
		case 6 : //찾는 데이터 없음
			System.out.println("찾는 데이터가 없습니다.");
			break;
		case 7 : //저장 실패 메시지
			System.out.println("저장 실패!");
			break;
		case 8 : //삭제 실패 메시지
			System.out.println("삭제 실패!");
			break;
		case 9 ://아이디 없음
			System.out.println("해당되는 ID가 없습니다.");
			break;
		case 10 ://패스워드 틀림
			System.out.println("비밀번호가 틀립니다.");
			break;
		}//switch end
		System.out.println();//줄바꿈.	
	}//printMsg end

	//7. 제품 전체 출력 메소드
	public void printList(List<ProductDto> pList) {
		System.out.println("======[전체 제품 출력]======");
		if(pList.size()==0) {
			printMsg(3);
			return;			
		}
		else{
			for(ProductDto product:pList) {
				System.out.print(product);
				if(product.getP_kind().equals("가전제품")) {
					System.out.println("A/S기간 : " + product.getP_exp().substring(0,10));
				}
				else if(product.getP_kind().equals("식품")) {
					System.out.println("유통기한 : " + product.getP_exp().substring(0,10));
				}
				else {
					System.out.println("사용용도 : "+product.getP_use());
				}
				System.out.println("--------------------");
			}
		}
	}//printList end

	//8. 카테고리별 출력 메소드
	public void printCategory(List<ProductDto> pList) {
		int count = 0;
		int sub = inputNumber("선택 >> ");
		if(sub==0) {
			printMsg(1);
			return;
		}
		switch(sub) {
		case 1: //가전
			if(pList.size()==0) {
				printMsg(3);
				return;			
			}
			else{
				System.out.println("======[카테고리별 제품 출력]======");
				for(ProductDto product:pList) {
					if(product.getP_kind().equals("가전제품")) {
						System.out.print(product);
						System.out.println("A/S기간 : " + product.getP_exp().substring(0,10));
						System.out.println("--------------------");
					}
					else {
						count++;
					}
				}
				if(pList.size() == count) {
					printMsg(3);
					count = 0;
				}
			}
			break;
		case 2: //식품
			if(pList.size()==0) {
				printMsg(3);
				return;			
			}
			else {
				System.out.println("======[카테고리별 제품 출력]======");
				for(ProductDto product:pList) {
					if(product.getP_kind().equals("식품")) {
						System.out.print(product);

						System.out.println("유통기한 : " + product.getP_exp().substring(0,10));
						System.out.println("--------------------");
					}
					else {
						count++;
					}
				}
				if(pList.size() == count) {
					printMsg(3);
					count = 0;
				}
			}
			break;
		case 3: //생필품
			if(pList.size()==0) {
				printMsg(3);
				return;			
			}
			else{
				System.out.println("======[카테고리별 제품 출력]======");
				for(ProductDto product:pList) {
					if(product.getP_kind().equals("생활필수품")) {
						System.out.print(product);
						System.out.println("사용용도: " + product.getP_use());
						System.out.println("--------------------");
					}
					else {
						count++;
					}
				}
				if(pList.size() == count) {
					printMsg(3);
					count = 0;
				}
			}
			break;
		default:
			printMsg(2);
		}
	}
	//9. 검색결과 출력 메소드
	public void printInfo(ProductDto product) {
		System.out.println("======[검색결과]======");
		System.out.print(product);
		if(product.getP_kind().equals("식품")) {
			System.out.println("유통기한 : " + product.getP_exp().substring(0,10));
		}
		else if(product.getP_kind().equals("가전제품")) {
			System.out.println("A/S기간 : " + product.getP_exp().substring(0,10));
		}
		else {
			System.out.println("사용용도 : " + product.getP_use());
		}
	}

	//10.제품 등록 메소드
	public ProductDto inputProduct() {
		ProductDto product = new ProductDto();

		System.out.println("=====[제품 등록 (없으면 Enter)]=====");
		String str = null;
		while(true) {
			System.out.print("카테고리 (1.가전제품, 2.식품, 3.생활필수품): ");
			str = scan.nextLine();
			if(str.equals("1")) {
				str="가전제품";
				product.setP_kind(str);
				break;	
			}
			else if(str.equals("2")) {
				str="식품";
				product.setP_kind(str);
				break;
			}
			else if(str.equals("3")) {
				str="생활필수품";
				product.setP_kind(str);
				break;
			}
			else if(str.equals("")) {
				System.out.println("카테고리를 입력하세요.");
				System.out.println();
			}
			else {
				printMsg(2);
			}
		}
		while(true){
			System.out.print("제품코드 : ");
			str = scan.nextLine();
			if(!str.equals("")) {
				product.setP_code(str);
				break;
			}
			else if(str.equals("")) {
				System.out.println("제품코드를 입력해주세요.");
			}
		}

		System.out.print("제품명 : ");
		str = scan.nextLine();
		if(!str.equals("")) {
			product.setP_name(str);
		}
		while(true) {
			System.out.print("가격 : ");
			str = scan.nextLine();
			if(!str.equals("")) {
				try {
					product.setP_price(Integer.parseInt(str));
					break;
				} catch (NumberFormatException e) {
					printMsg(2);
				}
			}
			else {
				break;
			}
		}//while end
		while(true) {
			System.out.print("수량 : ");
			str = scan.nextLine();
			if(!str.equals("")) {
				try {
					product.setP_amount(Integer.parseInt(str));
					break;
				} catch (NumberFormatException e) {
					printMsg(2);
				}
			}
			else {
				break;
			}
		}//while end
		while(true) {
			if(product.getP_kind().equals("가전제품")) {
				System.out.print("A/S기간(YYYY-MM-DD): ");
				str = scan.nextLine();
				if(!str.equals("")) {
					product.setP_exp(str);
					break;
				}
			}
			else if(product.getP_kind().equals("식품")) {
				System.out.print("유통기한(YYYY-MM-DD) : ");
				str = scan.nextLine();
				if(!str.equals("")) {
					product.setP_exp(str);
					break;
				}
			}
			else if(product.getP_kind().equals("생활필수품")) {
				System.out.print("사용용도 : ");
				str = scan.nextLine();
				if(!str.equals("")) {
					product.setP_use(str);
					break;
				}
			}
			else {
				break;
			}
		}		
		return product;
	}//inputProduct end

	//11. 수정할 제품 코드 받기
	public String changeCode() {
		String code =null;

		System.out.println("======[제품 수정]======");
		//제품코드 입력 받기
		System.out.println("제품 코드를 입력하세요.");
		System.out.print("제품 코드 : ");
		code = scan.nextLine();

		return code;
	}//changeCode end

	//12. 제품 수정 메소드
	public void productChange(ProductDto product) {
		System.out.println("수정하겠습니까(y/n)?");
		String str = scan.nextLine();

		if(!str.toUpperCase().equals("Y")) {
			return;//여기서 메소드 종료(수정 안함.)
		}//if end
		System.out.println("수정사항을 입력하세요.(없으면 enter)");
		System.out.print("카테고리 : ");
		str = scan.nextLine();
		if(!str.equals("")) {
			product.setP_kind(str);
		}//if kind end
		System.out.print("제품명 : ");
		str = scan.nextLine();
		if(!str.equals("")) {
			product.setP_name(str);
		}//if kind end
		while(true) {//가격은 숫자가 들어올때 까지 반복
			System.out.print("가격 : ");
			str = scan.nextLine();
			if(!str.equals("")) {
				try {
					product.setP_price(Integer.parseInt(str));
					break;
				} catch (NumberFormatException e) {
					//e.printStackTrace();
					System.out.println("잘못 입력하셨습니다.\n숫자를 입력하세요.");               
				}// catch end
			}//if price end
			else {//수정된 가격이 입력 안되도 while 종료
				break;
			}//else end
		}//while price end
		while(true) {//수량은 숫자가 입력될때 까지 반복
			System.out.print("수량 :");
			str = scan.nextLine();
			if(!str.equals("")) {
				try {
					product.setP_amount(Integer.parseInt(str));
					break;
				} catch (NumberFormatException e) {
					//e.printStackTrace();
					System.out.println("잘못 입력하셨습니다.\n숫자를 입력하세요.");
				}
			}//if amount end
			else {
				break;
			}//else end         
		}//while amount end
		if(product.getP_kind().equals("식품")) {//식품일때 유통기한 수정
			System.out.print("유통기한 : ");
			str = scan.nextLine();
			if(!str.equals("")) {
				product.setP_exp(str);
			}//if kind end
		}
		else if(product.getP_kind().equals("가전제품")) {//전자제품 as기간 수정
			System.out.print("A/S기간 : ");
			str = scan.nextLine();
			if(!str.equals("")) {
				product.setP_exp(str);
			}//if kind end
		}
		else {//생활필수품 수정
			System.out.print("사용용도 : ");
			str = scan.nextLine();
			if(!str.equals("")) {
				product.setP_use(str);
			}//if kind end
		}
	}//productChange end

	//13. 제품 삭제 메소드
	public String inputDelPro() {
		String delPro = null;
		System.out.println("======[제품 삭제]======");
		System.out.print("삭제할 제품코드 : ");
		delPro = scan.nextLine();
		return delPro;
	}//inputDelPro end
	//14. 삭제 실행유무 메소드
	public boolean DelPro() {
		boolean delPro = true;
		System.out.println("삭제하겠습니까(y/n)?");
		String str = scan.nextLine();
		if(!str.toUpperCase().equals("Y")) {
			delPro = false;//여기서 메소드 종료(수정 안함.)
		}
		return delPro;
	}
	//15. 전체직원 정보 출력 메소드
	public void printList2(List<EmployeeDto> pList) {
		System.out.println("===[전체직원 정보 보기]===");
		for(EmployeeDto a : pList) {
			System.out.println(a);
			System.out.println("--------------------");
		}
	}
	//16. 직원 수정 메소드
	public void updateInput2(EmployeeDto member) {
		System.out.println();
		System.out.println("====================");
		System.out.println(member);
		System.out.println("====================");
		System.out.println("수정할 비밀번호를 입력해주세요.");
		String str = scan.nextLine();
		if(!str.equals("")) {
			member.setE_pass(str);
		}
		System.out.println("수정할 이름을 입력해주세요.");
		str = scan.nextLine();
		if(!str.equals("")) {
			member.setE_name(str);
		}
		System.out.println("수정할 직급을 입력해주세요.\n 1.관리자 2.알바");
		String gMenu = pServ.gMenu();
		member.setE_grade(gMenu);         
	}
	//17. 직원 메뉴 메소드
	public int employeeInputM() {
		System.out.println("=====[직원 관리 메뉴]====");
		System.out.println("1. 직원등록");
		System.out.println("2. 직원정보삭제");
		System.out.println("3. 직원정보출력");
		System.out.println("4. 직원정보수정");
		System.out.print("선택 >> ");
		int a = scan.nextInt();
		scan.nextLine();
		return a;
	}//employeeInputM end
	
	public void printInfoEmp(EmployeeDto member) {
        System.out.println("======[검색결과]======");
        System.out.print(member);
     }
	
	public String inputDelId() {
	      String delId = null;
	      System.out.println("======[직원ID 삭제]======");
	      System.out.print("삭제할 직원ID : ");
	      delId = scan.nextLine();
	      return delId;
	   }//inputDelPro end
}//class end
