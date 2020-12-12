package com.insoftar.userTest.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.insoftar.userTest.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	Optional<User> findByCedula(long cedula);
	
}
