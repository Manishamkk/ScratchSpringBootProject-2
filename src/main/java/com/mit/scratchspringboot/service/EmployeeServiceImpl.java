package com.mit.scratchspringboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mit.scratchspringboot.dto.EmployeeDto;
import com.mit.scratchspringboot.entity.EmployeeEntity;
import com.mit.scratchspringboot.exception.RecordNotFoundException;
import com.mit.scratchspringboot.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	// save employee use EmployeeDto

	public EmployeeDto saveEmp(EmployeeDto employeeDto) {

		EmployeeEntity employeeEntity = new EmployeeEntity();

		employeeEntity.setName(employeeDto.getName());
		employeeEntity.setEmail(employeeDto.getEmail());
		employeeEntity.setPassword(employeeDto.getPassword());
		employeeEntity.setMobileNo(employeeDto.getMobileNo());

		employeeRepository.save(employeeEntity);
		return employeeDto;
	}

//get employee with id

	public EmployeeDto getById(Integer id) {
		EmployeeEntity employeeEntity = employeeRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Record not found with  id :" + id));

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName(employeeEntity.getName());
		employeeDto.setEmail(employeeEntity.getEmail());
		employeeDto.setMobileNo(employeeEntity.getMobileNo());
		employeeDto.setPassword(employeeEntity.getPassword());
		return employeeDto;
	}

	// delete employee with id
	public boolean deleteById(Integer id) {
		if (employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
			return true;
		}

		return false;
	}

	// update employe record
	public EmployeeDto updateEmployee(Integer id, EmployeeDto employee) {

		EmployeeEntity emp = employeeRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("record not found with id :" + id));

		emp.setName(employee.getName());
		emp.setEmail(employee.getEmail());
		emp.setMobileNo(employee.getMobileNo());
		emp.setPassword(employee.getPassword());

		// convert Entity to DTO
		employeeRepository.save(emp);
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName(emp.getName());
		employeeDto.setEmail(emp.getEmail());
		employeeDto.setMobileNo(emp.getMobileNo());
		employeeDto.setPassword(emp.getPassword());
		employeeRepository.save(emp);
		return employeeDto;

	}

	// partially update the record
	public EmployeeDto partiallyUpdate(Integer id, Map<String, Object> update) {

		EmployeeEntity emp = employeeRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("record not found with id :" + id));

		if (update.containsKey("name")) {
			emp.setName((String) update.get("name"));
		}
		if (update.containsKey("email")) {
			emp.setEmail((String) update.get("email"));
		}
		if (update.containsKey("mobileNo")) {
			emp.setMobileNo((String) update.get("mobileNo"));
		}
		if (update.containsKey("password")) {
			emp.setPassword((String) update.get("password"));
		}
		employeeRepository.save(emp);
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName(emp.getName());
		employeeDto.setEmail(emp.getEmail());
		employeeDto.setMobileNo(emp.getMobileNo());
		employeeDto.setPassword(emp.getPassword());
		return employeeDto;
	}

	// get all employee using EmployeeDto

	public List<EmployeeDto> getAllEmployee() {
		List<EmployeeEntity> emp = employeeRepository.findAll();
		List<EmployeeDto> employeeDtoList = new ArrayList<>();

		for (EmployeeEntity employee : emp) {

			EmployeeDto dto = new EmployeeDto();
			dto.setName(employee.getName());
			dto.setEmail(employee.getEmail());
			dto.setMobileNo(employee.getMobileNo());
			dto.setPassword(employee.getPassword());
			employeeDtoList.add(dto);
		}
		return employeeDtoList;
	}

}
