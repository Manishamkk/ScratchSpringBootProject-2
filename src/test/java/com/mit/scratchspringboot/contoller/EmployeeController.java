package com.mit.scratchspringboot.contoller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.mit.scratchspringboot.controller.EmployeeController;
import com.mit.scratchspringboot.dto.EmployeeDto;
import com.mit.scratchspringboot.exception.RecordNotFoundException;
import com.mit.scratchspringboot.service.EmployeeService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private EmployeeService empService;

	// ================= SAVE EMPLOYEE Posivive test case=================

	@Test
	void saveEmployee_RuntimeExceptionTest() throws Exception {

	    EmployeeDto employeeDto = new EmployeeDto();

	    employeeDto.setName("Manisha");
	    employeeDto.setEmail("manisha@gmail.com");
	    employeeDto.setMobileNo("9876543210");
	    employeeDto.setPassword("Manisha@123");

	    when(empService.saveEmp(any(EmployeeDto.class)))
	            .thenThrow(new RuntimeException("Something went wrong"));

	    mockMvc.perform(
	            post("/employee/saveEmp")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(objectMapper.writeValueAsString(employeeDto))
	    )
	    .andExpect(status().isBadRequest());

	    verify(empService, times(1))
	            .saveEmp(any(EmployeeDto.class));
	}
	// negative test case for saveEmployee

	@Test
	void saveEmployee_NegativeTest() throws Exception {

	    EmployeeDto employeeDto = new EmployeeDto();

	    employeeDto.setName("Manisha");
	    employeeDto.setEmail("manisha@gmail.com");
	    employeeDto.setMobileNo("9876543210");
	    employeeDto.setPassword("Manisha@123");

	    when(empService.saveEmp(any(EmployeeDto.class)))
	            .thenThrow(new RuntimeException("Something went wrong"));

	    mockMvc.perform(
	            post("/employee/saveEmp")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(objectMapper.writeValueAsString(employeeDto))
	    )
	    .andExpect(status().isBadRequest());

	    verify(empService, times(1))
	            .saveEmp(any(EmployeeDto.class));
	}
	// ================= GET EMPLOYEE Positive test case=================

	@Test
	void getByEmployee_PositiveTest() throws Exception {

		Integer id = 1;

		EmployeeDto employeeDto = new EmployeeDto();

		employeeDto.setName("Manisha");
		employeeDto.setEmail("manisha@gmail.com");

		when(empService.getById(id)).thenReturn(employeeDto);

		mockMvc.perform(get("/employee/get/{id}", id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Manisha"))
				.andExpect(jsonPath("$.email").value("manisha@gmail.com"));

		verify(empService, times(1)).getById(id);
	}

	// negative test case for getByEmployee

	@Test
	void getByEmployee_NegativeTest() throws Exception {

		when(empService.getById(999)).thenThrow(new RecordNotFoundException("record not found"));

		mockMvc.perform(get("/employee/get/{id}", 999)).andExpect(status().isNotFound());
	}

	// ================= GET ALL Employee Positive Test Case=================

	@Test
	void getAllEmp_PositiveTest() throws Exception {

		EmployeeDto employee1 = new EmployeeDto();
		employee1.setName("Manisha");
		employee1.setEmail("manisha@gmail.com");

		EmployeeDto employee2 = new EmployeeDto();
		employee2.setName("Rahul");
		employee2.setEmail("rahul@gmail.com");

		when(empService.getAllEmployee()).thenReturn(Arrays.asList(employee1, employee2));

		mockMvc.perform(get("/employee/getAll")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].name").value("Manisha"))
				.andExpect(jsonPath("$[0].email").value("manisha@gmail.com"))
				.andExpect(jsonPath("$[1].name").value("Rahul"))
				.andExpect(jsonPath("$[1].email").value("rahul@gmail.com"));

		verify(empService, times(1)).getAllEmployee();

	}

	// negative test case for getAll Employee

	@Test
	void getAllEmp_NegativeTest() throws Exception {

		when(empService.getAllEmployee()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/employee/getAll")).andExpect(status().isNotFound());

		verify(empService, times(1)).getAllEmployee();
	}

	// ================= Positive test case for update employee =================

	// ================= UPDATE =================

	@Test
	void updateEmployee_PositiveTest() throws Exception {

		Integer id = 1;

		EmployeeDto employeeDto = new EmployeeDto();

		employeeDto.setName("Manisha");
		employeeDto.setEmail("manisha@gmail.com");
		employeeDto.setMobileNo("9876543210");
		employeeDto.setPassword("Manisha@123");

		when(empService.updateEmployee(any(Integer.class), any(EmployeeDto.class))).thenReturn(employeeDto);

		mockMvc.perform(put("/employee/update/{id}", id).contentType("application/json")
				.content(objectMapper.writeValueAsString(employeeDto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Manisha"))
				.andExpect(jsonPath("$.email").value("manisha@gmail.com"));

		verify(empService, times(1)).updateEmployee(any(Integer.class), any(EmployeeDto.class));
	}
	// negative test case for update employee

	@Test
	void updateEmployee_NegativeTest() throws Exception {

		EmployeeDto employeeDto = new EmployeeDto();

		employeeDto.setName("Manisha");
		employeeDto.setEmail("manisha@gmail.com");
		employeeDto.setMobileNo("9876543210");
		employeeDto.setPassword("Manisha@123");

		when(empService.updateEmployee(anyInt(), any(EmployeeDto.class)))
				.thenThrow(new RecordNotFoundException("record not found with id :999"));

		mockMvc.perform(put("/employee/update/{id}", 999).contentType("application/json")
				.content(objectMapper.writeValueAsString(employeeDto))).andExpect(status().isNotFound());

		verify(empService, times(1)).updateEmployee(anyInt(), any(EmployeeDto.class));
	}

	// positive test case for patially update record
	@Test
	void partiallyUpdate_PositiveTest() throws Exception {

		Integer id = 1;

		Map<String, Object> update = new HashMap<>();

		update.put("name", "Manisha");
		update.put("email", "manisha@gmail.com");

		EmployeeDto employeeDto = new EmployeeDto();

		employeeDto.setName("Manisha");
		employeeDto.setEmail("manisha@gmail.com");
		employeeDto.setMobileNo("9876543210");
		employeeDto.setPassword("Manisha@123");

		when(empService.partiallyUpdate(any(Integer.class), any(Map.class))).thenReturn(employeeDto);

		mockMvc.perform(patch("/employee/patch/{id}", id).contentType("application/json")
				.content(objectMapper.writeValueAsString(update))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Manisha"))
				.andExpect(jsonPath("$.email").value("manisha@gmail.com"));

		verify(empService, times(1)).partiallyUpdate(any(Integer.class), any(Map.class));
	}

	// negative test case for partially update record
	@Test
	void partiallyUpdate_NegativeTest() throws Exception {

		Map<String, Object> update = new HashMap<>();

		update.put("name", "Manisha");

		when(empService.partiallyUpdate(anyInt(), any(Map.class)))
				.thenThrow(new RecordNotFoundException("record not found with id :999"));

		mockMvc.perform(patch("/employee/patch/{id}", 999).contentType("application/json")
				.content(objectMapper.writeValueAsString(update))).andExpect(status().isNotFound());

		verify(empService, times(1)).partiallyUpdate(anyInt(), any(Map.class));
	}

//positive test case for delete employee id	

	@Test
	void deleteEmployee_PositiveTest() throws Exception {

		Integer id = 1;

		when(empService.deleteById(id)).thenReturn(true);

		mockMvc.perform(delete("/employee/delete/{id}", id)).andExpect(status().isNoContent());

		verify(empService, times(1)).deleteById(id);
	}

	@Test
	void deleteEmployee_NegativeTest() throws Exception {

		Integer id = 100;

		when(empService.deleteById(id)).thenReturn(false);

		mockMvc.perform(delete("/employee/delete/{id}", id)).andExpect(status().isNotFound());

		verify(empService, times(1)).deleteById(id);
	}

}