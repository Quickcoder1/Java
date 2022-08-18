package com.infy.userdata.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infy.userdata.entity.User;

public interface UserDataRepository extends CrudRepository<User, String> {
	List<User> findByUserName(String UserName);
	
}
