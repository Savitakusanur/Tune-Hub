package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Users;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {

	@Autowired
	UsersService service;

	@PostMapping("/register")
	public String addUser(@ModelAttribute Users user) {
		boolean userStatus = service.emailExists(user.getEmail());
		if (userStatus == false) {
			service.addUser(user);
			System.out.println("Users Added");
		}
		// System.out.println(user.getUsername()+" "+user.getEmail()+"
		// "+user.getPassword()+" "+user.getGender()+" "+user.getRole()+"
		// "+user.getAddress());
		else {
			System.out.println("User already exists");
		}
		return "home";
	}

	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session,Model model) {
		if (service.validateUser(email, password) == true) {
			String role = service.getRole(email);
			session.setAttribute("email", email);
			if (role.equals("Customer")) {
				Users user=service.getUser(email);
				boolean userStatus=user.isPremium();
				model.addAttribute("isPremium", userStatus);
				return "customerHome";
			} else {
				return "adminHome";
			}
			// return "home";
		} else {
			return "login";
		}
	}

	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
}
