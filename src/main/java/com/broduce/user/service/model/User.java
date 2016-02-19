package com.broduce.user.service.model;

public class User {

	private long id;
	private String name;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String phone;
	private String email;
	private String avatarUrl;
	private String birthday;
	private String role;

	private String username;
	private String password;
	private String provider;

	private String chatUsername;
	private String chatPassword;

	private long createdAt;

	public User() {
		name = "";
		firstName = "";
		middleName = "";
		lastName = "";
		gender = "unknown";
		phone = "";
		email = "";
		avatarUrl = "";
		birthday = "";
		role = "asker";
		username = "";
		password = "";
		provider = "";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public String getChatUsername() {
		return chatUsername;
	}

	public void setChatUsername(String chatUsername) {
		this.chatUsername = chatUsername;
	}

	public String getChatPassword() {
		return chatPassword;
	}

	public void setChatPassword(String chatPassword) {
		this.chatPassword = chatPassword;
	}

}
