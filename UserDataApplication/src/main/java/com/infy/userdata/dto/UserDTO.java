package com.infy.userdata.dto;

public class UserDTO {
	
	private Integer userId;
	private String userName;
	private String password;
	private Long phoneNo;
	private String city;
	
	public UserDTO() {
		super();
	}
	
	public UserDTO(Integer userId, String userName, String password, Long phoneNo, String city) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.phoneNo = phoneNo;
		this.city = city;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	

}
