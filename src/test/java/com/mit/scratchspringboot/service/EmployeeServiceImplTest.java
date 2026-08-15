package com.mit.scratchspringboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mit.scratchspringboot.dto.EmployeeDto;
import com.mit.scratchspringboot.entity.EmployeeEntity;
import com.mit.scratchspringboot.exception.RecordNotFoundException;
import com.mit.scratchspringboot.repository.EmployeeRepository;
import com.mit.scratchspringboot.service.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
 class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	// positive test case by saveEmployee
	@Test
	void saveEmp_shouldReturnEmployeeDto_whenEmployeeIsSaved() {
		// Arrange
		EmployeeDto employeeDto = new EmployeeDto();

		employeeDto.setName("Manisha");
		employeeDto.setEmail("manisha@gmail.com");
		employeeDto.setMobileNo("9876543210");
		employeeDto.setPassword("Manisha@123");

		// Mock repository save()

		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setName(employeeDto.getName());
		employeeEntity.setEmail(employeeDto.getEmail());
		employeeEntity.setPassword(employeeDto.getPassword());
		employeeEntity.setMobileNo(employeeDto.getMobileNo());

		when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);

		// Act
		EmployeeDto result = employeeService.saveEmp(employeeDto);

		// Assert
		assertNotNull(result);

		assertEquals("Manisha", result.getName());
		assertEquals("manisha@gmail.com", result.getEmail());
		assertEquals("9876543210", result.getMobileNo());
		assertEquals("Manisha@123", result.getPassword());

		// Verify repository save() was called once
		verify(employeeRepository, times(1)).save(any(EmployeeEntity.class));
	}

	//negative test case for saveEmployee
	@Test
	void testSaveEmp_Negative() {

	    // Arrange
	    EmployeeDto employeeDto = new EmployeeDto();
	    employeeDto.setName("Manisha");
	    employeeDto.setEmail("manisha@gmail.com");
	    employeeDto.setPassword("Password@123");
	    employeeDto.setMobileNo("9876543210");

	    when(employeeRepository.save(any(EmployeeEntity.class)))
	            .thenThrow(new RuntimeException("Database error"));

	    // Act & Assert
	    assertThrows(RuntimeException.class, () -> {
	        employeeService.saveEmp(employeeDto);
	    });

	    // Verify repository was called
	    verify(employeeRepository, times(1))
	            .save(any(EmployeeEntity.class));
	}
	
	//getAll employee positive test case
	@Test
	void testGetAllEmployee_Success() {

	    // Arrange
	    EmployeeEntity employee1 = new EmployeeEntity();
	    employee1.setName("Manisha");
	    employee1.setEmail("manisha@gmail.com");
	    employee1.setPassword("Password@123");
	    employee1.setMobileNo("9876543210");

	    EmployeeEntity employee2 = new EmployeeEntity();
	    employee2.setName("Rahul");
	    employee2.setEmail("rahul@gmail.com");
	    employee2.setPassword("Password@456");
	    employee2.setMobileNo("9876543211");

	    List<EmployeeEntity> employeeList =
	            Arrays.asList(employee1, employee2);

	    when(employeeRepository.findAll()).thenReturn(employeeList);

	    // Act
	    List<EmployeeDto> result = employeeService.getAllEmployee();

	    // Assert
	    assertNotNull(result);
	    assertEquals(2, result.size());

	    assertEquals("Manisha", result.get(0).getName());
	    assertEquals("manisha@gmail.com", result.get(0).getEmail());
	    assertEquals("Password@123", result.get(0).getPassword());
	    assertEquals("9876543210", result.get(0).getMobileNo());

	    assertEquals("Rahul", result.get(1).getName());
	    assertEquals("rahul@gmail.com", result.get(1).getEmail());
	    assertEquals("Password@456", result.get(1).getPassword());
	    assertEquals("9876543211", result.get(1).getMobileNo());

	    // Verify
	    verify(employeeRepository, times(1)).findAll();
	}
	
	//negative testcase for get all employee
	@Test
	void testGetAllEmployee_RepositoryException() {

	    // Arrange
	    when(employeeRepository.findAll())
	            .thenThrow(new RuntimeException("Database error"));

	    // Act & Assert
	    assertThrows(RuntimeException.class, () -> {
	        employeeService.getAllEmployee();
	    });

	    // Verify
	    verify(employeeRepository, times(1)).findAll();
	}
	
	
	
	
	
	
	
	
	
	// Positive Test Case for getByID
	@Test
	void getById_shouldReturnEmployeeDto_whenEmployeeExists() {

		// Arrange
		Integer id = 101;

		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setName("Manisha");
		employeeEntity.setEmail("manisha@gmail.com");
		employeeEntity.setMobileNo("9876543210");
		employeeEntity.setPassword("Manisha@123");

		when(employeeRepository.findById(id)).thenReturn(Optional.of(employeeEntity));

		// Act
		EmployeeDto result = employeeService.getById(id);

		// Assert
		assertNotNull(result);
		assertEquals("Manisha", result.getName());
		assertEquals("manisha@gmail.com", result.getEmail());
		assertEquals("9876543210", result.getMobileNo());
		assertEquals("Manisha@123", result.getPassword());

		// Verify repository was called once
		verify(employeeRepository, times(1)).findById(id);
	}

	// Negative Test Case
	@Test
	void getById_shouldThrowRecordNotFoundException_whenEmployeeDoesNotExist() {

		// Arrange
		Integer id = 101;

		when(employeeRepository.findById(id)).thenReturn(Optional.empty());

		// Act + Assert
		RecordNotFoundException exception = assertThrows(RecordNotFoundException.class,
				() -> employeeService.getById(id));

		// Verify exception message
		assertEquals("Record not found with  id :101", exception.getMessage());

		// Verify repository was called once
		verify(employeeRepository, times(1)).findById(id);
	}
	
	//positive test case for delete employee
	@Test
	void deleteById_PositiveTest() {

	    Integer id = 1;

	    when(employeeRepository.existsById(id)).thenReturn(true);

	    boolean result = employeeService.deleteById(id);

	    assertTrue(result);

	    verify(employeeRepository, times(1)).existsById(id);
	    verify(employeeRepository, times(1)).deleteById(id);
	}
	//negative test case for delete employee
	
	@Test
	void deleteById_NegativeTest() {

	    Integer id = 100;

	    when(employeeRepository.existsById(id)).thenReturn(false);

	    boolean result = employeeService.deleteById(id);

	    assertFalse(result);

	    verify(employeeRepository, times(1)).existsById(id);
	    verify(employeeRepository, never()).deleteById(id);
	}

	//positive test case for update all record
	
	@Test
	void updateEmployee_PositiveTest() {

	    Integer id = 1;

	    EmployeeDto employeeDto = new EmployeeDto();
	    employeeDto.setName("Manisha");
	    employeeDto.setEmail("manisha@gmail.com");
	    employeeDto.setMobileNo("9876543210");
	    employeeDto.setPassword("Manisha@123");

	    EmployeeEntity employeeEntity = new EmployeeEntity();
	    employeeEntity.setId(id);
	    employeeEntity.setName("Old Name");
	    employeeEntity.setEmail("old@gmail.com");
	    employeeEntity.setMobileNo("9999999999");
	    employeeEntity.setPassword("Old@123");

	    when(employeeRepository.findById(id))
	            .thenReturn(Optional.of(employeeEntity));

	    when(employeeRepository.save(any(EmployeeEntity.class)))
	            .thenReturn(employeeEntity);

	    EmployeeDto result = employeeService.updateEmployee(id, employeeDto);

	    assertNotNull(result);
	    assertEquals("Manisha", result.getName());
	    assertEquals("manisha@gmail.com", result.getEmail());
	    assertEquals("9876543210", result.getMobileNo());
	    assertEquals("Manisha@123", result.getPassword());

	    verify(employeeRepository, times(1)).findById(id);
	    
	    // Change times(1) to times(2) if your service method saves twice, 
	    // or use any(EmployeeEntity.class) if the object instance differs:
	    verify(employeeRepository, times(2)).save(any(EmployeeEntity.class));
	}
	
	//negative test case for update employee
	
	@Test
	void updateEmployee_NegativeTest() {

	    Integer id = 100;

	    EmployeeDto employeeDto = new EmployeeDto();
	    employeeDto.setName("Manisha");
	    employeeDto.setEmail("manisha@gmail.com");
	    employeeDto.setMobileNo("9876543210");
	    employeeDto.setPassword("Manisha@123");

	    when(employeeRepository.findById(id))
	            .thenReturn(Optional.empty());

	    RecordNotFoundException exception = assertThrows(
	            RecordNotFoundException.class,
	            () -> employeeService.updateEmployee(id, employeeDto)
	    );

	    assertEquals(
	            "record not found with id :" + id,
	            exception.getMessage()
	    );

	    verify(employeeRepository, times(1))
	            .findById(id);

	    verify(employeeRepository, never())
	            .save(any(EmployeeEntity.class));
	}
	
	//positive test case for partially update the data
	@Test
	void partiallyUpdate_PositiveTest() {

	    Integer id = 1;

	    Map<String, Object> update = new HashMap<>();
	    update.put("name", "Manisha");
	    update.put("email", "manisha@gmail.com");

	    EmployeeEntity employeeEntity = new EmployeeEntity();
	    employeeEntity.setId(id);
	    employeeEntity.setName("Old Name");
	    employeeEntity.setEmail("old@gmail.com");
	    employeeEntity.setMobileNo("9876543210");
	    employeeEntity.setPassword("Old@123");

	    when(employeeRepository.findById(id))
	            .thenReturn(Optional.of(employeeEntity));

	    when(employeeRepository.save(any(EmployeeEntity.class)))
	            .thenReturn(employeeEntity);

	    EmployeeDto result =
	            employeeService.partiallyUpdate(id, update);

	    assertNotNull(result);

	    assertEquals("Manisha", result.getName());
	    assertEquals("manisha@gmail.com", result.getEmail());

	    // These fields were not included in update,
	    // so they should remain unchanged.
	    assertEquals("9876543210", result.getMobileNo());
	    assertEquals("Old@123", result.getPassword());

	    verify(employeeRepository, times(1))
	            .findById(id);

	    verify(employeeRepository, times(1))
	            .save(employeeEntity);
	}
	
	//negative test case for partially update the data
	@Test
	void partiallyUpdate_NegativeTest() {

	    Integer id = 100;

	    Map<String, Object> update = new HashMap<>();
	    update.put("name", "Manisha");
	    update.put("email", "manisha@gmail.com");

	    when(employeeRepository.findById(id))
	            .thenReturn(Optional.empty());

	    RecordNotFoundException exception = assertThrows(
	            RecordNotFoundException.class,
	            () -> employeeService.partiallyUpdate(id, update)
	    );

	    assertEquals(
	            "record not found with id :" + id,
	            exception.getMessage()
	    );

	    verify(employeeRepository, times(1))
	            .findById(id);

	    verify(employeeRepository, never())
	            .save(any(EmployeeEntity.class));
	}
	
}