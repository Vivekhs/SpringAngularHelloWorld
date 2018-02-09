package com.jwt.service;

import java.util.List;

import com.jwt.model.Employee;

public interface EmployeeService {
	public Employee findById(long id);
	
	public void addEmployee(Employee employee);

	public List<Employee> getAllEmployees();

	public void deleteEmployee(Integer employeeId);

	public Employee getEmployee(int employeeid);

	public Employee updateEmployee(Employee employee);
	
	public List<Employee> getEmps();
	
	public void setEmps(List<Employee> emps);

	
}
