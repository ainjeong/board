package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.JoinUserRequest;
import com.example.demo.dto.LoginUserRequest;
import com.example.demo.service.UserDetailService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserApiController {
	@Autowired
	UserService userService;
	
	@Autowired
	UserDetailService userDetailService;
	
	@PostMapping("/signup")
	public String signup(JoinUserRequest req) {
		userService.save(req);
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(Model model, HttpServletRequest req, HttpServletResponse res) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(req, res, auth);
            model.addAttribute("isLoggedIn", false);
        }
        return "login";
	}
	
	/*
	 * 시큐리티 처리할꺼면 login - logout 은 개발자가 추가 설정하지 않는다. 자체적으로 지원함.
	@PostMapping("/login")
	public String login(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth.isAuthenticated();
        model.addAttribute("isLoggedIn", isLoggedIn);
		return "redirect:/home";
	}
	*/
	
	
	
	
	

}
