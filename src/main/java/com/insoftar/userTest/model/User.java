package com.insoftar.userTest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
	
	@Column(name = "id")
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(name = "first_name")
    private String firstName;
	
	
	@Column(name = "last_name")
    private String lastName;
	
	@Column(name = "cedula", unique = true)
    private long cedula;
    
    @NonNull
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "phone_number")
    private long phoneNumber; 
}