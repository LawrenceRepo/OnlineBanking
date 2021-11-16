package com.lawrence.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.lawrence.model.Appointment;
import com.lawrence.repository.AppointmentRepository;

@RunWith(SpringRunner.class)
public class AppointmentServiceImplTest {

	@InjectMocks
	AppointmentServiceImpl appointmentServiceImpl;

	@Mock
	private AppointmentRepository appointmentRepository;

	@org.junit.Before
	public void init() {

//		appointmentServiceImpl.findAppointment(null);

		appointmentServiceImpl = new AppointmentServiceImpl();
		MockitoAnnotations.initMocks(appointmentServiceImpl);

		ReflectionTestUtils.setField(appointmentServiceImpl, "appointmentRepository", appointmentRepository);
	}

	@Test
	public void testConfirmAppoint() {

		Appointment appointmentNonOptional = new Appointment();

		Optional<Appointment> appointmentWithOptional = Optional.of(new Appointment());

		Mockito.when(appointmentRepository.findById(Mockito.any())).thenReturn(appointmentWithOptional);
		Mockito.when(appointmentRepository.save(Mockito.any())).thenReturn(appointmentNonOptional);

		appointmentServiceImpl.confirmAppointment(123456L);
	}

	@Test
	public void testConfirmAppointException() {

		Mockito.when(appointmentRepository.findById(Mockito.any())).thenReturn(null);

		appointmentServiceImpl.confirmAppointment(123456L);
	}

	@Test
	public void testCreateAppointment() {

		// return appointmentRepository.save(appointment); // this is what we Mocked
		// below

		Appointment appointment = new Appointment();

		Mockito.when(appointmentRepository.save(Mockito.any())).thenReturn(appointment);

		appointmentServiceImpl.createAppointment(appointment);
	}

	@Test
	public void testCreateAppointmentException() {

		Appointment appointment = new Appointment();

		Mockito.when(appointmentRepository.save(Mockito.any())).thenThrow(new RuntimeException());

		appointmentServiceImpl.createAppointment(appointment);
	}

	@Test
	public void testFindAll() {

		List<Appointment> appointments = new ArrayList<>();

		Mockito.when(appointmentRepository.findAll()).thenReturn(appointments);

		appointmentServiceImpl.findAll();

	}
	
	@Test
	public void testFindAllException() {

		Mockito.when(appointmentRepository.findAll()).thenThrow(new RuntimeException());

		appointmentServiceImpl.findAll();

	}
}
