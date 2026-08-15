package com.mit.scratchspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.mit.scratchspringboot.entity.EmployeeEntity;
@Service
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Integer> {

	

}
