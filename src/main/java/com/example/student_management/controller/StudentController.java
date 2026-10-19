package com.example.student_management.controller;

import com.example.student_management.entity.Student;
import com.example.student_management.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {

        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        return studentRepository.findById(id)
                .map(existing -> {

                    existing.setName(student.getName());
                    existing.setEmail(student.getEmail());
                    existing.setBranch(student.getBranch());
                    existing.setYear(student.getYear());

                    return ResponseEntity.ok(
                            studentRepository.save(existing)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable Long id) {

        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        studentRepository.deleteById(id);

        return ResponseEntity.ok("Student deleted successfully");
    }
}