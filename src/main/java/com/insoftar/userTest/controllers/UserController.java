package com.insoftar.userTest.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insoftar.userTest.model.User;
import com.insoftar.userTest.repositories.UserRepository;
import com.insoftar.userTest.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	final static Logger logger = LoggerFactory.getLogger(UserController.class);
	

	@PostMapping("/add") 
	public @ResponseBody long addNewUser(@RequestBody User newUser) {
		logger.info("creating user with cedula " + newUser.getCedula());
		
		if(!userRepository.findByCedula(newUser.getCedula()).isEmpty()) {
			logger.info("cant create as user with cedula  " + newUser.getCedula()+ " already exist");
			return -1;
		}else if (!userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
			logger.info("cant create as user with email  " + newUser.getEmail()+ " already exist");
			return -2;
		}
		
		logger.info("creating user with cedula" + newUser.getCedula());
		return userRepository.save(newUser).getId();
		
	}
	
	 
	@PutMapping("/update") 
	public @ResponseBody long updateUser(@RequestBody User newUser) {
				
		if(!userRepository.findByCedula(newUser.getCedula()).isEmpty()) {
			logger.info("cant update as user with cedula  " + newUser.getCedula()+ " already exist");
			return -1;
		}else if (!userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
			logger.info("cant update as user with email  " + newUser.getEmail()+ " already exist");
			return -2;
		}
		
		Optional<User> foundUser = userRepository.findByCedula((newUser.getCedula()));
		
		if(!foundUser.isEmpty()) {
			logger.info("Updating User with id " + newUser.getId());
			return userRepository.save(newUser).getId();
		}
		logger.info("User with id  " + newUser.getId() + " does not exist");
		return 0;
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
	
	@GetMapping(path = "/{id}")
	public @ResponseBody User getUser(@PathVariable ("id") long id) {
		
		Optional<User> foundUser = userRepository.findById(id);
		
		if (foundUser.isEmpty()) {
			logger.info("User with id " + id + " does not exists");
			return null;
		}
		logger.info("Found User:: " + foundUser.get());
		return foundUser.get();
	}
	
}
