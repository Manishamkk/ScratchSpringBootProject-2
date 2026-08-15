
package com.mit.scratchspringboot.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mit.scratchspringboot.dto.EmployeeDto;

import com.mit.scratchspringboot.exception.RecordNotFoundException;
import com.mit.scratchspringboot.service.EmployeeService;

import jakarta.validation.Valid;

@RequestMapping("/employee")
@RestController
public class EmployeeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService empService;

	@PostMapping("/saveEmp")
	public ResponseEntity<EmployeeDto> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto) {

		EmployeeDto employeeResponse = empService.saveEmp(employeeDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponse);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<EmployeeDto> getByEmployee(@PathVariable Integer id) {

		LOGGER.info("Fetching the EMployee details with id: {}", id);

		EmployeeDto employeeDto = empService.getById(id);

		LOGGER.info("Emplyee details fetched with id: {}", id);

		return new ResponseEntity<>(employeeDto, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<EmployeeDto>> getAllEmp() {

		List<EmployeeDto> employeeDtoList = empService.getAllEmployee();
		if (CollectionUtils.isEmpty(employeeDtoList)) {
			throw new RecordNotFoundException("Employee list is empty ");
		}

		return new ResponseEntity<>(employeeDtoList, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {
		boolean deleted = empService.deleteById(id);
		if (!deleted) {
			throw new RecordNotFoundException("Record not found" + id);
		}
		return new ResponseEntity<>("Employee deleted successfully", HttpStatus.NO_CONTENT);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDto employee) {

		EmployeeDto employeeResponse = empService.updateEmployee(id, employee);

		return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
	}

	@PatchMapping("/patch/{id}")
	public ResponseEntity<EmployeeDto> partiallyUpdate(@PathVariable Integer id,
			@RequestBody Map<String, Object> update) {

		EmployeeDto employeeResponse = empService.partiallyUpdate(id, update);
		return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
	}

}
