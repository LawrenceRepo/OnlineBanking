package com.lawrence.controller;

import java.security.Principal;
import java.text.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import com.lawrence.dto.AppointmentDto;
import com.lawrence.model.Appointment;
import com.lawrence.model.User;
import com.lawrence.service.AppointmentService;
import com.lawrence.service.UserService;

@RunWith(SpringRunner.class)
public class AppointmentControllerTest {

    // First we mock our Attributes
    @InjectMocks
    AppointmentController appointmentController;

    @Mock
    private UserService userService;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    Model model;

    @Mock
    Principal principal;


    // Second we Initialize our Attributes
    @Before
    public void init() {

        MockitoAnnotations.initMocks(appointmentController);

        ReflectionTestUtils.setField(appointmentController, "userService", userService);

        ReflectionTestUtils.setField(appointmentController, "appointmentService", appointmentService);

    }

    @Test
    public void testCreateAppointment() {

        appointmentController.createAppointment(model);
    }

    @Test
    public void testCreateAppointmentPost() throws ParseException {

        AppointmentDto appointmentDto = new AppointmentDto();

        Mockito.when(principal.getName()).thenReturn("Lawrence");

        User user = new User();

        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(user);

        Appointment appointment = new Appointment();

        Mockito.when(appointmentService.createAppointment(Mockito.any())).thenReturn(appointment);


        appointmentController.createAppointmentPost(appointmentDto, "2021-03-12 10:34", model, principal);
    }

}
