package com.contact.manager.controller;

import java.io.File;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.manager.helper.FileUploadHandler;
import com.contact.manager.helper.Message;
import com.contact.manager.models.Contact;
import com.contact.manager.models.User;
import com.contact.manager.repository.ContactRepository;
import com.contact.manager.repository.userRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private userRepository repository;
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private FileUploadHandler fileUploadHandler;
	@RequestMapping("/index")
public String dashboard(Model model,Principal principal
) {
		
	String name=principal.getName();
	
	User user=repository.getUserByUserName(name);
	System.out.print(user);
	model.addAttribute("user",user);
	model.addAttribute("title","Dashboard-ContactManager");
	return"normal/user_dashboard";
}

@GetMapping("/addContact")	
public String addContact(Model model) {
	model.addAttribute("title","AddContact-ContactManager");
	model.addAttribute("contact",new Contact());
	return "normal/add_contact_form";
}

@PostMapping("/process-contact")
public String  processContact(@Valid @ModelAttribute("contact")Contact contact,@RequestParam("contactimage")MultipartFile file,Principal principal,Model model,HttpSession session) {
	
	try {
	String userName=principal.getName();
	User user=this.repository.getUserByUserName(userName);
	if(file.isEmpty()) {
		System.out.print("file is empty");
		contact.setContactImage("contact.png");
	}
	
	
	 boolean b=fileUploadHandler.uploadImage(file);
	 if(b) {
		 contact.setContactImage(file.getOriginalFilename());
	 }
	 contact.setUser(user);
	
		
		user.getContact().add(contact);
		User user1=this.repository.save(user);
		System.out.println(contact);
		model.addAttribute("contact",new Contact());
		session.setAttribute("message", new Message("Contact added successfully!! Add More","success"));
		
	}catch(Exception e) {
		e.printStackTrace();
		session.setAttribute("message",new Message("Something went wrong","danger"));
	}
	return "normal/add_contact_form";

	
	

}

@GetMapping("/view-contacts/{page}")
public String showContacts(@PathVariable("page")int page,Model model,Principal principal) {
	
	String name=principal.getName();
	User user=this.repository.getUserByUserName(name);
	 Pageable pageable=PageRequest.of(page,4);
	
	
	Page<Contact> contact=this.contactRepository.findContactByUserId(user.getUserId(),pageable);
	
	model.addAttribute("title","ViewContact-ContactManager");
	
	model.addAttribute("contactList", contact);
	
	model.addAttribute("currentPage",page);
	
	model.addAttribute("totalPages",contact.getTotalPages());
	return "normal/view_contact_page";
	
}


@GetMapping("/contactDetails/{cId}")
public String contactDetail(@PathVariable("cId") String contactId,Model model,Principal principal) {
	Optional<Contact> contactOpt=this.contactRepository.findById(Long.parseLong(contactId));
	
	Contact contact=contactOpt.get();
	String userName=principal.getName();
	User user=this.repository.getUserByUserName(userName);
	
	if(user.getUserId()==contact.getUser().getUserId())
	{
	model.addAttribute("title",contact.getContactName());
	model.addAttribute("contact", contact);
	}
	
	return "normal/contact_detail";
}

@GetMapping("/delete/{cId}")
public String deleteContact(@PathVariable("cId")String contactId,Principal principal,HttpSession session) {
	
	Optional<Contact> contactOpt=this.contactRepository.findById(Long.parseLong(contactId));
	Contact contact=contactOpt.get();
	 String userName=principal.getName();
	 User user=this.repository.getUserByUserName(userName);
	 if(user.getUserId()==contact.getUser().getUserId()) {
		 contact.setUser(null);
		 this.contactRepository.delete(contact);
		 session.setAttribute("message",new Message("Contact deleted successfully","success"));
		 
	 }
	
	
	return"redirect:/user/view-contacts/0";
	
}

@GetMapping("/updateContact/{cId}")
public String updateContact(@PathVariable("cId") String contactId,Model model,Principal principal) {
	Contact contact=this.contactRepository.findById(Long.parseLong(contactId)).get();
	String userName=principal.getName();
	User user=this.repository.getUserByUserName(userName);
	if(user.getUserId()==contact.getUser().getUserId()) {
		model.addAttribute("contact",contact);
		model.addAttribute("title","UpdateContact-ContactManager");
	}
	
	return "normal/update_contact";
}


@PostMapping("/update-contact")
public String processUpdateContact(@ModelAttribute("contact")Contact contact,@RequestParam("contactimage")MultipartFile file,HttpSession session,Principal principal ){
	
			Contact contactOld=this.contactRepository.findById(contact.getContactId()).get();
			System.out.print(contactOld);
	
	try {
		if(!file.isEmpty()) {
			//delete old file
			File deleteFile=new ClassPathResource("static/image").getFile();
			File file1=new File(deleteFile,contactOld.getContactImage());
			file1.delete();
			
			
			//update new file
			 boolean b=fileUploadHandler.uploadImage(file);
			 if(b) {
				 contact.setContactImage(file.getOriginalFilename());
			 }
			
			
			
			
			
		}
		else {
			contact.setContactImage(contactOld.getContactImage());
		}
		
		
		String userName=principal.getName();
		User user=this.repository.getUserByUserName(userName);
		contact.setUser(user);
		
		
		
		this.contactRepository.save(contact);
		session.setAttribute("message",new Message("Contact updated successfully","success"));
		
		
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	return"redirect:/user/contactDetails/"+contact.getContactId();
	
	
}




}
