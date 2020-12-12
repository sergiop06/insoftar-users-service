package com.insoftar.userTest.service;

import java.util.List;
import java.util.Optional;

import com.insoftar.userTest.model.User;

public interface UserService {
	
	public Optional<User> findById(long idUser);
	public Optional<User> findByCedula(long cedula);
	public Optional<User> findByEmail(String email);
	public boolean deleteUser(long idUser);
	public List<User> findAllUsers();
	public User create(User newUser);
}
