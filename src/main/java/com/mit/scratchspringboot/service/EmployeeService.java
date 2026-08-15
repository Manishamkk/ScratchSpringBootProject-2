package com.mit.scratchspringboot.service;

import java.util.List;
import java.util.Map;

import com.mit.scratchspringboot.dto.EmployeeDto;
public interface EmployeeService {

	public EmployeeDto saveEmp(EmployeeDto employeeDto);

	public EmployeeDto getById(Integer id);

	public List<EmployeeDto> getAllEmployee();

	public EmployeeDto updateEmployee(Integer id, EmployeeDto employee);

	public EmployeeDto partiallyUpdate(Integer id, Map<String, Object> update);

	public boolean deleteById(Integer id);
}
