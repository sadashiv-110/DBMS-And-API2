package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.AppointmentRepository;

import jakarta.transaction.Transactional;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appRep;

    @Transactional
    public void deleteAppointment(Long id) {
        appRep.deleteById(id);
    }
}