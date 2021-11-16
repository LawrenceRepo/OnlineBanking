package com.lawrence.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.lawrence.model.User;
import com.lawrence.repository.UserRepository;

@RunWith(SpringRunner.class)
public class UserSecurityServiceTest {

	@InjectMocks
	UserSecurityService userSecurityService;

	@Mock
	private UserRepository userRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(userSecurityService);

		ReflectionTestUtils.setField(userSecurityService, "userRepository", userRepository);
	}
	
	@Test
	public void testLoadUserByUsername() {
		
		 User user = new User();
		 user.setUsername("law");
		 
		Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(user);
		
		userSecurityService.loadUserByUsername("law");
	}
	
	@Test
	public void testLoadUserByNullUsername() {

		Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);
		
		userSecurityService.loadUserByUsername("law");
	}
}