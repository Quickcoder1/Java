package com.infy.userdata;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.userdata.entity.User;
import com.infy.userdata.exception.UserDataException;
import com.infy.userdata.repository.UserDataRepository;
import com.infy.userdata.service.UserDataService;
import com.infy.userdata.service.UserDataServiceImpl;

@SpringBootTest
public class UserDataApplicationTests {

	@Mock
	private UserDataRepository userDataRepository;
	


	@InjectMocks
	private UserDataService userDataService = new UserDataServiceImpl();
	
	
	@Test
	public void getDetailsByUserNameNoDetailsFound() {
		List<User>ul = new ArrayList<User>();
		String name= "Chris";
		Mockito.when(userDataRepository.findByUserName(name)).thenReturn(ul);
	    UserDataException ude =Assertions.assertThrows(UserDataException.class,()->userDataService.getDetailsByUserName(name));
	    Assertions.assertEquals("service.NO_DETAILS_FOUND", ude.getMessage());
	}
}
