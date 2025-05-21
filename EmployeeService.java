package com.employeemanagement.service;

import com.employeemanagement.entity.Employees;
import com.employeemanagement.entity.Users;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;

    public Employees saveEmployee(Employees employees ,Long userId) {
        Users user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));
        employees.setUsers(user);
        return employeeRepository.save(employees);
    }

    public List<Employees> getAllemployees() {
        return employeeRepository.findAll();
    }
    public List<Employees> getemployeesByUserid(Long userId){
        return employeeRepository.findByUsersId(userId);
    }

    public Employees updateEmployeeById(long id, Employees updateEmp) {
        Employees exitEmp=employeeRepository.findById(id).orElseThrow(()->new RuntimeException("employee not found!"));
        exitEmp.setName(updateEmp.getName());
        exitEmp.setDepartment(updateEmp.getDepartment());
        return employeeRepository.save(exitEmp);
    }

    public ResponseEntity<String> deleteByid(long id) {
         employeeRepository.deleteById(id);
         return ResponseEntity.ok("Deleted successfully");
    }
}
