package com.example.demo.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.Patient;
import com.example.demo.Repository.PatientRepository;

@RestController
@RequestMapping("patient")
public class PatientController {

    @Autowired
    private PatientRepository patRep;

    // ✅ GET ALL PATIENTS (ID ADDED)
    @GetMapping
    public String getAll() {
        String result = "";

        for (Patient p : patRep.findAll()) {
            result += "ID : " + p.getId()
                    + " | Name : " + p.getName()
                    + " | Gender : " + p.getGender()
                    + " | Date of Birth : " + p.getDob()
                    + "<br>";
        }
        return result;
    }

    // ✅ GET PATIENT BY ID (ID SHOWN)
    @GetMapping("/{id}")
    public String getPatientById(@PathVariable Long id) {

        Optional<Patient> optionalPatient = patRep.findById(id);

        if (optionalPatient.isPresent()) {
            Patient p = optionalPatient.get();
            return "ID : " + p.getId()
                    + " | Name : " + p.getName()
                    + " | Gender : " + p.getGender()
                    + " | Date of Birth : " + p.getDob();
        }

        return "Patient not found";
    }

    // ✅ ADD PATIENT
    @PostMapping("/add")
    public ResponseEntity<?> createPatient(@RequestBody Patient p) {

        if (p.getName() == null || p.getGender() == null || p.getDob() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Missing required fields");
        }

        Patient savedPatient = patRep.save(p);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPatient);
    }

    // ✅ UPDATE PATIENT
    @PostMapping("/update/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient pDetails) {

        Patient p = patRep.findById(id).orElse(null);

        if (p != null) {
            p.setName(pDetails.getName());
            p.setGender(pDetails.getGender());
            p.setDob(pDetails.getDob());
            patRep.save(p);
        }

        return p;
    }

    // ✅ DELETE PATIENT
    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable Long id) {
        patRep.deleteById(id);
    }
}
