package com.app.rest.simple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.rest.vo.EmployeeVO;

@Service
public class EmpManager{
	
	@Autowired
	EmpDAO dao;

	public List<EmployeeVO> getAllEmployees() {
		return dao.getAllEmployees();
	}
	
}