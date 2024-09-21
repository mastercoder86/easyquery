package com.shopping_centre.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping_centre.entities.Passcode;

public interface PasscodeRepository extends JpaRepository<Passcode, Long> {
	Passcode findFirstByOrderByIdDesc();

}
