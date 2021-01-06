package com.app.rest.full;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.rest.full.EmployeeDAO;
import com.app.rest.vo.EmployeeVO;

@Service
public class EmployeeManagerImpl implements EmployeeManager {
	
	@Autowired
	EmployeeDAO dao;

	public List<EmployeeVO> getAllEmployees() {
		return dao.getAllEmployees();
	}
	
}
