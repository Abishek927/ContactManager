package com.contact.manager.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer.PasswordCompareConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.manager.helper.Message;
import com.contact.manager.models.User;
import com.contact.manager.repository.userRepository;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private userRepository userRepo;
	
	@GetMapping("/")
	public String getHome(Model model) {
		model.addAttribute("title","Home-ContactManager");
		return"home";
	}
	@GetMapping("/about")
	public String getAbout(Model model) {
		model.addAttribute("title","About-ContactManager");
		return"about";
		
	}
	
	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("title","Login-ContactManager");
		return"login";
		
	}
	@GetMapping("/signup")
	public String getSignup(Model model) {
		model.addAttribute("title","SignUp-ContactManager");
		model.addAttribute("user",new User());
		return"signup";
		
	}
	
	@PostMapping("/signupProcess")
	public String doProcess(@Valid @ModelAttribute("user")User user,BindingResult result,@RequestParam(value="agreement",defaultValue="false")boolean agreement,Model model,HttpSession httpSession) {
		try {
			if(result.hasErrors()) {
				model.addAttribute("user",user);
				return "signup";
			}
			
			
			if(!agreement) {
				model.addAttribute("user",user);
				throw new  Exception("Terms And Condition should be checked");
			
			}
			
			user.setUserRole("ROLE_USER");
			user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
			
			
			
		
			User user1=userRepo.save(user);
			model.addAttribute("user",new User());
			httpSession.setAttribute("message",new Message("Registered successfully","alert-success"));
			return "signup";
			
			
			
			
		}catch(Exception e) {
			
			model.addAttribute("user",user);
			httpSession.setAttribute("message",new Message("Something went wrong!!"+e.getMessage(),"alert-danger"));
			return"signup";
			
			
			
			
		}
		
		
		
	}
	
	@GetMapping("/signin")
	public String customLoginPage(Model m) {
		m.addAttribute("title", "Login-ContactManager");
		return"login";
	}

}
