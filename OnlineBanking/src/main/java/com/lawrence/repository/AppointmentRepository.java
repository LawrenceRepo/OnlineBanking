package com.lawrence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lawrence.model.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    List<Appointment> findAll();
}
