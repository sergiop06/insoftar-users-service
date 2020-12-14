package com.insoftar.userTest.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.insoftar.userTest.model.User;

@CrossOrigin(origins = "http://localhost:4200")
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	Optional<User> findByCedula(long cedula);
	
	@Query(value= "SELECT COUNT (u.id) FROM User u WHERE u.email=:email") 
	Long countUsersWithEmail(@Param("email") String email);
	
}
