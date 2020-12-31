package com.app;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.EmployeeDAO;
import com.app.EmployeeVO;

@Service
public class EmployeeManagerImpl implements EmployeeManager {
	@Autowired
	EmployeeDAO dao;

	public List<EmployeeVO> getAllEmployees() {
		return dao.getAllEmployees();
	}
}
