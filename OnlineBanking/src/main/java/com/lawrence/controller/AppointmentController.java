package com.lawrence.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lawrence.dto.AppointmentDto;
import com.lawrence.model.Appointment;
import com.lawrence.model.User;
import com.lawrence.service.AppointmentService;
import com.lawrence.service.UserService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String createAppointment(Model model) {

        Appointment appointment = new Appointment();
        
        model.addAttribute("appointment", appointment);
        model.addAttribute("dateString", "");

        return "appointment";
    }

    @PostMapping("/create")
    public String createAppointmentPost(@ModelAttribute("appointment") AppointmentDto appointmentDto, @ModelAttribute("dateString") String date, Model model, Principal principal) throws ParseException {

        Appointment appointment = new Appointment();
        
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        
        Date d1 = format1.parse(date);
        appointmentDto.setDate(d1);

        User user = userService.findByUsername(principal.getName());
        appointment.setUser(user);

        appointmentService.createAppointment(appointment);

        return "redirect:/userFront";
    }


}
