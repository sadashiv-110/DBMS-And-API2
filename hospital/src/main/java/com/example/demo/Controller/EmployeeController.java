package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.Employee;
import com.example.demo.Repository.EmployeeRepository;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository empRep;

    // ================= LIST ALL (ID ADDED) =================
    @GetMapping
    public String getAll() {
        String result = "";

        for (Employee e : empRep.findAll()) {
            result += "ID : " + e.getId()
                    + " | Name : " + e.getName()
                    + " | Position : " + e.getPosition()
                    + " | Hire Date : " + e.getHireDate()
                    + "<br>";
        }

        return result;
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id) {

        Employee e = empRep.findById(id).orElse(null);

        if (e == null) {
            return "Employee not found";
        }

        return "ID : " + e.getId()
                + " | Name : " + e.getName()
                + " | Position : " + e.getPosition()
                + " | Hire Date : " + e.getHireDate()
                + "<br>";
    }

    // ================= ADD =================
    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return empRep.save(employee);
    }

    // ================= UPDATE =================
    @PostMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {

        Employee emp = empRep.findById(id).orElse(null);

        if (emp == null) {
            return null;
        }

        emp.setName(employee.getName());
        emp.setPosition(employee.getPosition());
        emp.setHireDate(employee.getHireDate());

        return empRep.save(emp);
    }

    // ================= DELETE =================
    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        empRep.deleteById(id);
        return "Employee Deleted";
    }
}
