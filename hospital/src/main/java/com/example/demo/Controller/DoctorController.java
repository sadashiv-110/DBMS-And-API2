package com.example.demo.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.Doctor;
import com.example.demo.Repository.DoctorRepository;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorRepository docRep;

    // ===============================
    // ADD DOCTOR
    // ===============================
    @PostMapping("/add")
    public ResponseEntity<?> addDoctor(@RequestBody Doctor d) {

        if (d.getName() == null || d.getSpeciality() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Name and speciality are required");
        }

        Doctor savedDoctor = docRep.save(d);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedDoctor);
    }

    // ===============================
    // LIST ALL DOCTORS (WITH ID)
    // ===============================
    @GetMapping
    public String getAllDoctors() {

        String result = "";

        for (Doctor d : docRep.findAll()) {
            result += "ID : " + d.getId()
                    + " | Name : " + d.getName()
                    + " | Specialty : " + d.getSpeciality()
                    + "<br>";
        }

        return result;
    }

    // ===============================
    // GET DOCTOR BY ID (IDOR READY)
    // ===============================
    @GetMapping("/{id}")
    public String getDoctorById(@PathVariable Long id) {

        Optional<Doctor> optionalDoctor = docRep.findById(id);

        if (optionalDoctor.isPresent()) {
            Doctor d = optionalDoctor.get();

            return "ID : " + d.getId()
                    + " | Name : " + d.getName()
                    + " | Specialty : " + d.getSpeciality();
        }

        return "Doctor not found";
    }

    // ===============================
    // UPDATE DOCTOR (IDOR)
    // ===============================
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor d1) {

        Doctor d = docRep.findById(id).orElse(null);

        if (d == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Doctor not found");
        }

        d.setName(d1.getName());
        d.setSpeciality(d1.getSpeciality());

        docRep.save(d);

        return ResponseEntity.ok(d);
    }

    // ===============================
    // DELETE DOCTOR (IDOR)
    // ===============================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {

        if (!docRep.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Doctor not found");
        }

        docRep.deleteById(id);
        return ResponseEntity.ok("Doctor deleted");
    }
}
