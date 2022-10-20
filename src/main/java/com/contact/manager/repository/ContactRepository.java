package com.contact.manager.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.manager.models.Contact;

public interface ContactRepository extends JpaRepository<Contact,Long> {

	@Query("from Contact c where c.user.userId=:userId")
	public Page<Contact> findContactByUserId(@Param("userId") Long userId,Pageable pageable);	
}
