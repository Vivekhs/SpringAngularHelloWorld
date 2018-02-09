package com.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwt.dao.EmployeeDAO;
import com.jwt.model.Employee;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDAO employeeDAO;
	 private static List<Employee> emps;

	@Override
	@Transactional
	public void addEmployee(Employee employee) {
		employeeDAO.addEmployee(employee);
	}
	
	@Override
	 public  List<Employee> getEmps() {
		return emps;
	}
	
	@Override
	public  void setEmps(List<Employee> emps) {
		EmployeeServiceImpl.emps = emps;
	}

	public Employee findById(long id) {
	        for(Employee emp : emps){
	            if(emp.getId() == id){
	                return emp;
	            }
	        }
	        return null;
	    }

	@Override
	@Transactional
	public List<Employee> getAllEmployees() {
		System.out.println("services::::");
		return employeeDAO.getAllEmployees();
	}

	@Override
	@Transactional
	public void deleteEmployee(Integer employeeId) {
		employeeDAO.deleteEmployee(employeeId);
	}

	public Employee getEmployee(int empid) {
		return employeeDAO.getEmployee(empid);
	}

	public Employee updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return employeeDAO.updateEmployee(employee);
	}

	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

}
