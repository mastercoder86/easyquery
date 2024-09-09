package com.shopping_centre.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping_centre.entities.OtpDetails;
import java.util.List;


public interface OtpDetailsRepository extends JpaRepository<OtpDetails,Long> {
	OtpDetails findByEmail(String email);
}
