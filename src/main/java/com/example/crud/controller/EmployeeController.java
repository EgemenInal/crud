package com.example.crud.controller;


import com.example.crud.exeption.ResourceNotFoundException;
import com.example.crud.model.Employee;
import com.example.crud.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/1")
public class EmployeeController {

    @Autowired
    private EmployeeRepo employeeRepo;

    //

    @GetMapping("/employees")
    public List<Employee>getAllEmployees(){
        return employeeRepo.findAll();
    }
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee)
    {
        return employeeRepo.save(employee);
    }

    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id")long id) throws ResourceNotFoundException {
        Employee employee = employeeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(id+" Not Found"));

        return ResponseEntity.ok().body(employee);

    }
    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id")long id, @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
        Employee employee = employeeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(id+" Not Found"));
        employee.setName(employeeDetails.getName());
        employee.setSurname(employeeDetails.getSurname());
        employee.setIncome(employeeDetails.getIncome());
        employee.setOfficeLocation(employeeDetails.getOfficeLocation());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setWinner(employeeDetails.isWinner());
        employeeRepo.save(employee);
        return ResponseEntity.ok().body(employee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id")long id)throws ResourceNotFoundException{
        employeeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(id+" Not Found"));

        employeeRepo.deleteById(id);
        return ResponseEntity.ok().build();

    }
}
