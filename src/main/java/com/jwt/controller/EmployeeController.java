package com.jwt.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.jwt.model.Employee;
import com.jwt.service.EmployeeService;

@RestController
@RequestMapping("/rest")
public class EmployeeController {

	private static final Logger logger = Logger
			.getLogger(EmployeeController.class);

	public EmployeeController() {
		System.out.println("EmployeeController()");
	}

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/emp", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> listEmployee() throws IOException {
		List<Employee> listEmployee = employeeService.getAllEmployees();
		System.out.println(listEmployee.toString()+"listEmployee");
		employeeService.setEmps(listEmployee);
		 if(listEmployee.isEmpty()){
	            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
	        }
	        return new ResponseEntity<List<Employee>>(listEmployee, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/newEmployee", method = RequestMethod.GET)
	public ModelAndView newContact(ModelAndView model) {
		Employee employee = new Employee();
		model.addObject("employee", employee);
		model.setViewName("EmployeeForm");
		return model;
	}

	@RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
	public ResponseEntity<Void> saveEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
		System.out.println(employee.toString()+"employee::::::");
		if (employee.getId() == 0) { // if employee id is 0 then creating the
			// employee other updating the employee
			employeeService.addEmployee(employee);
		} else {
			employeeService.updateEmployee(employee);
		}
		  HttpHeaders headers = new HttpHeaders();
	       headers.setLocation(ucBuilder.path("/emp/{id}").buildAndExpand(employee.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);	
	}

	@RequestMapping(value = "/deleteEmployee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Integer employeeId) {
		
		 if (employeeId == null) {
	            System.out.println("Unable to delete. User with id " + employeeId + " not found");
	            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	        }
		employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/editEmployee/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Employee> editContact(@PathVariable("id") Integer employeeId,@RequestBody Employee emp) {
		
		   Employee currentUser = employeeService.findById(emp.getId());
	          
	        if (currentUser==null) {
	            System.out.println("User with id " + emp.getId() + " not found");
	            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	        }
		
	        currentUser.setName(emp.getName());
	        currentUser.setAddress(emp.getAddress());
	        currentUser.setEmail(emp.getEmail());
	          
	        employeeService.updateEmployee(currentUser);
	        return new ResponseEntity<Employee>(currentUser, HttpStatus.OK);
	}

}