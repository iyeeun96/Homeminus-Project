package com.controller;

import java.util.List;

import com.dto.EmployeeDto;
import com.dto.ProductDto;
import com.service.ProductService;
import com.view.ProductView;

public class ProductController {
	ProductView pView = new ProductView();
	ProductService pServ = new ProductService();

	public void controll() {
		//메뉴 번호 저장 변수
		int menu = 0;	
		//회원 정보 저장 인스턴스
		EmployeeDto member = null;
		//제품 정보 저장 인스턴스 
		ProductDto product = null;
		//로그인 성공/실패 상태 정보 저장 변수
		int lr =0;
		//로그인 성공 시 사용자 ID 저장 변수 
		String user = null;

		while(true) {
			//1. 로그인 처리
			if(member == null) {
				member = new EmployeeDto();
				pView.loginInput(member);
				lr = pServ.loginCheck(member);				
			}//if end
			if(lr == 0) {//로그인이 성공한 경우
				user = member.getE_grade();//성공한 회원의 직급저장				
			}
			else {//로그인 실패한 경우
				//로그인 실패 메시지 출력-> view에서 출력
				pView.printMsg(lr);
				//member 인스턴스 제거
				member = null;
				continue;
			}//else end	
			//로그인 성공 시 처리할 내용들..
			//메뉴 출력(직급에 따라서 다른 메뉴 출력)
			pView.showMenu(user);
			menu = pView.inputNumber("선택 >> ");

			if(menu == 0) {//프로그램 종료
				pView.printMsg(0);
				break;
			}//if end
			//여기서부터 각 메뉴에 해당하는 기능 처리(switch)
			switch(menu) {
			case 1://제품 출력
				pView.printMenu();
				menu = pView.inputNumber("선택>> ");
				if(menu==0) {//이전메뉴로 돌아가기
					pView.printMsg(1);
					return;
				}
				List<ProductDto> pList = pServ.productList();	
				switch(menu) {
				case 1://전체출력
					pView.printList(pList);
					break;
				case 2://카테고리별 출력
					pView.subMenu();
					pView.printCategory(pList);
					break;
				default:
					pView.printMsg(2);
				}
				break;
			case 2://제품 검색
				System.out.print("검색할 제품코드 : ");
				product = pServ.ProductInfo();
				if(product!=null) {
					pView.printInfo(product);
				}
				else {
					pView.printMsg(6);
				}
				break;
			case 3://제품 등록
				if(!user.equals("관리자")) {
					pView.printMsg(2);
					break;
				}
				product = pView.inputProduct();
				int r = pServ.insertInfo(product);
				pView.printMsg(r);
				break;
			case 4://제품 수정
				if(!user.equals("관리자")) {
					pView.printMsg(2);
					break;
				}
				String code = pView.changeCode();
				product = pServ.pIofo(code);
				if(product!=null) {
					pView.printInfo(product);
					pView.productChange(product);

					pView.printInfo(product);
					r = pServ.updateInfo(product);
					pView.printMsg(r);
				}
				else {
					pView.printMsg(3);
				}
				break;
			case 5://제품삭제
				if(!user.equals("관리자")) {
					pView.printMsg(2);
					break;
				}

				String delPro = pView.inputDelPro();
				product = pServ.pIofo(delPro);
				if(product!=null) {
					pView.printInfo(product);
					if(pView.DelPro()) {
						pView.printMsg(pServ.delProduct(delPro));
					}
					else {
						pView.printMsg(8);
					}
				}
				else {
					pView.printMsg(6);
				}
				break;

			case 6://직원 등록및 삭제 컨트롤러 스위치부분
				if(!user.equals("관리자")) {
					pView.printMsg(2);
					break;
				}
				int a = pView.employeeInputM();

				switch(a) {
				case 1:  
					pServ.inpEmployee();
					break;
				case 2:
					String delId = pView.inputDelId();
					EmployeeDto delete = pServ.eIofo(delId);
					if(delete != null) {
						pView.printInfoEmp(delete);
						if(pView.DelPro()) {
							pView.printMsg(pServ.delEmployee(delId));
						}
						else {
							pView.printMsg(8);
						}
					}
					else {
						pView.printMsg(9);
					}
					break;
				case 3:
					List<EmployeeDto> outEmpl = pServ.outEmployee();
					pView.printList2(outEmpl);
					break;
				case 4:
					String uId = null;
					uId = pServ.inputUpdateUser();
					EmployeeDto serach = pServ.employeeInfo(uId);
					if(serach != null) {
						pView.updateInput2(serach);
						r = pServ.updateInfo(serach);
						System.out.println(serach);
						break;
					}
					else {
						pView.printMsg(9);
					}
					continue;
				default:
					System.out.println("번호를 입력해주세요.");
					continue; 
				}
				break;
			default:
				pView.printMsg(2);

			}
		}//while end
	}//controll end
}//class end
