package com.lawrence.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawrence.model.Appointment;
import com.lawrence.repository.AppointmentRepository;
import com.lawrence.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment createAppointment(Appointment appointment) {

        log.info("Going to createAppointment by appointment : {}", appointment);

        try {
        	
            return appointmentRepository.save(appointment);
            
        } catch (Exception e) {

            log.error("Exception while createAppointment, err msg : {}", e.getMessage());
        }
        return null;

    }

    @Override
    public List<Appointment> findAll() {

        log.info("Going to createAppointment by appointment");

        List<Appointment> appointments = new ArrayList<>();

        try {
        	
            appointments = appointmentRepository.findAll();
            
        } catch (Exception e) {

            log.error("Exception while createAppointment, err msg : {}", e.getMessage());
        }
        return appointments;

    }

    @Override
    public Appointment findAppointment(Long id) {

        log.info("Going to findAppointment by id : {}", id);
        
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        
        try {
            if (appointment.isPresent()) {
                
                return appointment.get();
            }
        } catch (Exception e) {

            log.error("Exception while findAppointment, err msg : {}", e.getMessage());
        }
        return null;

    }

    @Override
    public void confirmAppointment(Long id) {

        log.info("Going to confirmAppointment by id : {}", id);

        try {
            Appointment appointment = findAppointment(id);
            appointment.setConfirmed(true);

            appointmentRepository.save(appointment);
            
        } catch (Exception e) {

            log.error("Exception while confirmAppointment, err msg : {}", e.getMessage());
        }

    }
}
