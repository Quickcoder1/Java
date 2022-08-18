package com.infy.userdata.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.userdata.dto.UserDTO;
import com.infy.userdata.entity.User;
import com.infy.userdata.exception.UserDataException;
import com.infy.userdata.repository.UserDataRepository;
import com.infy.userdata.validator.UserValidator;

@Service(value="userDataService")
@Transactional
public class UserDataServiceImpl implements UserDataService {

	@Autowired
	private UserDataRepository userDataRepository;

	@Override
	public Integer addUser(UserDTO userDTO) throws UserDataException  {
		UserValidator uv = new UserValidator();
		uv.validate(userDTO);
		User u = new User();
		u.setUserId(userDTO.getUserId());
		u.setUserName(userDTO.getUserName());
		u.setPassword(userDTO.getPassword());
		u.setPhoneNo(userDTO.getPhoneNo());
		u.setCity(userDTO.getCity());
		userDataRepository.save(u);
		return u.getUserId();
	}

	@Override
	public List<UserDTO> getDetailsByUserName(String userName) throws UserDataException {
		List<User> uList= userDataRepository.findByUserName(userName);
		if (uList.isEmpty()) {
			throw new UserDataException("service.NO_DETAILS_FOUND");
		}
	    List<UserDTO> userDTO= new ArrayList<UserDTO>();
	    for(User users:uList) {
	    	UserDTO u = new UserDTO();
	    	u.setUserId(users.getUserId());
	    	u.setUserName(users.getUserName());
	    	u.setPassword(users.getPassword());
	    	u.setPhoneNo(users.getPhoneNo());
	    	u.setCity(users.getCity());
	    	userDTO.add(u);
	    }
		return userDTO;
	}

	
	
}



