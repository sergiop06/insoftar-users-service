package com.insoftar.userTest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insoftar.userTest.model.User;
import com.insoftar.userTest.repositories.UserRepository;
import com.insoftar.userTest.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;

	public Optional<User> findById(long idUser) {
		return userRepository.findById(idUser);	
	}
	
	public Optional<User> findByCedula(long cedula) {
		return userRepository.findByCedula(cedula);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public boolean deleteUser(long idUser) {
		
		userRepository.deleteById(idUser);
		if(userRepository.findById(idUser).isEmpty() ) {
			return true;
		}
		return false;
	}

	public List<User> findAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User create(User newUser) {
		return userRepository.save(newUser);
	}

	
}
