package com.contact.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.manager.models.User;

;

public interface userRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.userEmail= :email")
	public User getUserByUserName(@Param("email")String email);
}
