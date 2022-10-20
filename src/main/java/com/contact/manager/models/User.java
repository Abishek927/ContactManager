package com.contact.manager.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString


@Entity
@Table(name="user_table")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;
	@NotBlank(message = "UserName is required!!")
	@Size(min = 3,max = 15,message = "UserName should be 3to15 character...")
	@Column(name="user_name",nullable = false)
	private String userName;
	@Column(name="user_email",nullable = false)
	@Email(regexp ="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$",message = "Email must be of correct type!!")
	@NotBlank(message="Email is required!!")
	private String userEmail;
	@NotBlank(message = "Password is required!!")
	
	@Column(name="user_password",nullable =false)
	private String userPassword;
	
	@AssertTrue(message="Terms And Condition should be checked!!")
	private boolean agreement;
	
	@Column(name="user_image",nullable = true)
	private String userImage;
	@Column(name="user_desc",length = 5000)
	@NotBlank(message="please say something about your")
	private String userDescription;
	@Column(name="user_role",nullable=true)
	private String userRole;
	@Column(name="user_enable",nullable = true)
	private boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy ="user")
	  
	  @JsonManagedReference
	  
	  
	  private List<Contact> contact;
	
	
	  
	 
	
	public User() {
		super();
	}





	public User(Long userId, String userName, String userEmail, String userPassword, String userImage,
			String userDescription, String userRole, boolean enabled, List<Contact> contact) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userImage = userImage;
		this.userDescription = userDescription;
		this.userRole = userRole;
		this.enabled = enabled;
		this.contact = contact;
	}


	
	

	
	
	
	

}