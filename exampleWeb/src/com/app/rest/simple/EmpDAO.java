package com.app.rest.simple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.rest.vo.EmployeeVO;

@Repository
public class EmpDAO {
	public List<EmployeeVO> getAllEmployees() {
		List<EmployeeVO> employees = new ArrayList<EmployeeVO>();
		EmployeeVO vo1 = new EmployeeVO();
		vo1.setId(1);
		vo1.setFirstName("Developer");
		vo1.setLastName("wool");
		employees.add(vo1);
		EmployeeVO vo2 = new EmployeeVO();
		vo2.setId(2);
		vo2.setFirstName("Prayer");
		vo2.setLastName("Yuna");
		employees.add(vo2);
		return employees;
	}
}
