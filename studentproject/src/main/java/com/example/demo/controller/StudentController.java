package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

@Controller
public class StudentController {

    private final StudentRepository repo;

    public StudentController(StudentRepository repo) {
        this.repo = repo;
    }

    // Home page
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Add student form
    @GetMapping("/add")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    // Save student
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student) {
        repo.save(student);
        return "redirect:/students";
    }

    // List students
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", repo.findAll());
        return "students";
    }

    // Open update page
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        Student student = repo.findById(id).orElse(null);
        model.addAttribute("student", student);
        return "update-student";
    }

    // Update student
    @PostMapping("/update")
    public String updateStudent(@ModelAttribute Student student) {
        repo.save(student);
        return "redirect:/students";
    }

    // Delete student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/students";
    }
}
