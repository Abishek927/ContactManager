package com.contact.manager.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter




@Entity
@Table(name="contact_table")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="contact_id")
	
	private Long contactId;
	
	@Column(name="contact_name",nullable = false)
	private String contactName;
	
	
	@Column(name="contact_nick_name",nullable = true)
	private String contactNickName;
	
	@Column(name="contact_work",nullable = false)
	private String contactWork;
	@Column(name="contact_email",unique = false,nullable= false)
	private String contactEmail;
	
	@Column(name="contact_image",nullable = false)
	private String contactImage;
	
	@Column(name="contact_desc",length = 3000)
	private String contactDescription;
	@Column(name="contact_phone",unique = true)
	private String contactPhoneNo;
	
	 @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	  
	  @JoinColumn(name = "user_id_fk",referencedColumnName = "user_id")
	  
	  @JsonBackReference
	  private User user;
	
	
	 
	public Contact() {
		super();
	}
public Contact(Long contactId, String contactName, String contactNickName, String contactWork, String contactEmail,
			String contactImage, String contactDescription, String contactPhoneNo, User user) {
		super();
		this.contactId = contactId;
		this.contactName = contactName;
		this.contactNickName = contactNickName;
		this.contactWork = contactWork;
		this.contactEmail = contactEmail;
		this.contactImage = contactImage;
		this.contactDescription = contactDescription;
		this.contactPhoneNo = contactPhoneNo;
		this.user = user;
	}
	
	
	
	

}
