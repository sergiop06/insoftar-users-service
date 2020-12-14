package com.insoftar.userTest.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;

import com.insoftar.userTest.model.User;
import com.insoftar.userTest.repositories.UserRepository;
import com.insoftar.userTest.service.UserService;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/users")
	public ResponseEntity<?> addNewUser(@RequestBody User newUser) {
		logger.info("creating user with cedula " + newUser.getCedula());

		if (!userRepository.findByCedula(newUser.getCedula()).isEmpty()) {
			logger.info("cant create as user with cedula  " + newUser.getCedula() + " already exist");
			return new ResponseEntity<>("cedula already exist in db", HttpStatus.CONFLICT);
		} else if (!userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
			logger.info("cant create as user with email  " + newUser.getEmail() + " already exist");
			return new ResponseEntity<>("email already exist in db", HttpStatus.CONFLICT);
		}

		logger.info("creating user with cedula" + newUser.getCedula());
		return new ResponseEntity<>(userRepository.save(newUser).getId(), HttpStatus.OK);

	}

	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User newUser) {
		
		Optional<User> foundUser = userRepository.findById((id));
		logger.info("newUserid===="+newUser.getId());
		
		if (!foundUser.isEmpty()) {

			Optional<User> foundUserWithEmail = userRepository.findByEmail(newUser.getEmail());

			if (!foundUserWithEmail.isEmpty() && foundUserWithEmail.get().getId() != id) {
				logger.info("cant update user as user with email  " + newUser.getEmail() + " already exist");
				return new ResponseEntity<>("email already exist in db", HttpStatus.CONFLICT);

			}
			foundUser.get().setFirstName(newUser.getFirstName());
			foundUser.get().setLastName(newUser.getLastName());
			foundUser.get().setEmail(newUser.getEmail());
			foundUser.get().setPhoneNumber(newUser.getPhoneNumber());
			logger.info("Updating User with id " + id);
			return new ResponseEntity<>(userRepository.save(foundUser.get()).getId(), HttpStatus.OK);

		}
		logger.info("User with id  " + newUser.getId() + " does not exist");
		return new ResponseEntity<>("couldn find user with id" + newUser.getId(), HttpStatus.NOT_FOUND);
	}

	@GetMapping(path = "/users")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}

	@GetMapping(path = "/users/{id}")
	public @ResponseBody User getUser(@PathVariable("id") long id) {

		Optional<User> foundUser = userRepository.findById(id);

		if (foundUser.isEmpty()) {
			logger.info("User with id " + id + " does not exists");
			return null;
		}
		logger.info("Found User:: " + foundUser.get());
		return foundUser.get();
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("deleting user with id " + id);

		if (!userRepository.findById(id).isEmpty()) {
			logger.info("deleting user with id" + id);
			userRepository.deleteById(id);
			if (userRepository.findById(id).isEmpty()) {
				return new ResponseEntity<>("deleted user with id" + id, HttpStatus.OK);
			}
		}
		logger.info("failed to delete user with id " + id);
		return new ResponseEntity<>("failed to deleted user with id" + id, HttpStatus.NOT_FOUND);

	}
}
