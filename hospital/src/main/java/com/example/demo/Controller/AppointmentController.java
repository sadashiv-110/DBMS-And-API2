package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Appointment;
import com.example.demo.Repository.AppointmentRepository;
import com.example.demo.Repository.DoctorRepository;

@RestController
@RequestMapping("appointment")
public class AppointmentController {

    @Autowired
    AppointmentRepository appRepo;

    @Autowired
    DoctorRepository docRepo;

    // ✅ GET ALL APPOINTMENTS
    @GetMapping
    public String getAll() {
        String result = "";

        for (Appointment a : appRepo.findAll()) {
            result += "ID : " + a.getId()
                    + " | Patient : " + a.getPatient().getName()
                    + " | Doctor : " + a.getDoctor().getName()
                    + " | Appointment Time : " + a.getAppointmentTime()
                    + " | Details : " + a.getDetails()
                    + "<br>";
        }
        return result;
    }

    // ✅ GET APPOINTMENT BY ID
    @GetMapping("/{id}")
    public String getAppointmentById(@PathVariable Long id) {
        Appointment a = appRepo.findById(id).orElse(null);

        if (a != null) {
            return "ID : " + a.getId()
                    + " | Patient : " + a.getPatient().getName()
                    + " | Doctor : " + a.getDoctor().getName()
                    + " | Appointment Date : " + a.getAppointmentDate()
                    + " | Appointment Time : " + a.getAppointmentTime()
                    + " | Details : " + a.getDetails()
                    + "<br>";
        }
        return "Appointment not found";
    }

    // ✅ ADD APPOINTMENT (NEW & REQUIRED)
    @PostMapping
    public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment) {
        Appointment saved = appRepo.save(appointment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ✅ UPDATE APPOINTMENT
    @PostMapping("/update/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment aDetails) {

        Appointment a = appRepo.findById(id).orElse(null);

        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        a.setPatient(aDetails.getPatient());
        a.setDoctor(aDetails.getDoctor());
        a.setDetails(aDetails.getDetails());
        a.setAppointmentDate(aDetails.getAppointmentDate());
        a.setAppointmentTime(aDetails.getAppointmentTime());

        Appointment updated = appRepo.save(a);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // ✅ DELETE APPOINTMENT
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        return appRepo.findById(id).map(appointment -> {
            appointment.getPatient().removeAppointment(appointment);
            appointment.getDoctor().removeAppointment(appointment);
            appRepo.delete(appointment);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
