package com.infy.userdata.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.userdata.dto.UserDTO;
import com.infy.userdata.exception.UserDataException;
import com.infy.userdata.service.UserDataService;

@RestController
@RequestMapping(value="api")
public class UserDataAPI {
	
	@Autowired
	private UserDataService userDataService;
	
	@Autowired
	private Environment env;
	@PostMapping("/user")
	public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) throws UserDataException{
		Integer userId = userDataService.addUser(userDTO);
		return new ResponseEntity<>(env.getProperty("API.ADDITION_SUCCESS")+":" + userId,HttpStatus.CREATED);
	}
	@GetMapping("/user/{userName}")
	public ResponseEntity<List<UserDTO>> getDetailsByUserName(@PathVariable String userName) throws UserDataException {
		return new ResponseEntity<>(userDataService.getDetailsByUserName(userName),HttpStatus.OK);
	}
}
