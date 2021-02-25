package com.dao;

import java.util.List;

import com.dto.EmployeeDto;
import com.dto.ProductDto;

public interface ProductDao {
	//로그인 처리를 위한 메소드의 선언부 정의(select)
	public String selectPass(EmployeeDto emp);
	//제품등록을 위한 메소드의 선언부 정의(insert)
	public int insertProduct(ProductDto product);
	//전체 제품 목록 가져오는 메소드의 선언부 정의 (select)
	public List<ProductDto> selectList();
	//제품검색을 위한 메소드의 선언부 정의(select) 
	public ProductDto selectPro(String search);
	//제품 수정을 위한 메소드의 선언부 정의(update)
	public int updateProduct(ProductDto product);
	//제품 삭제를 위한 메소드의 선언부 정의(delete)
	public int deleteProduct(String delPro);
	//직원 등록을 위한 메소드의 선언부 정의(insert)
	public int insertEmployee(EmployeeDto member);
	//직원 삭제를 위한 메소드의 선언부 정의(delete)
	public int deleteEmployee(String delId);
	//전체 회원 가져오는 메소드의 선언부 정의(select)
	public List<EmployeeDto> selectAllList();
}