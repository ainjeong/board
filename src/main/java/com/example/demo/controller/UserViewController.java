package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.BoardViewResponse;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Heart;
import com.example.demo.entity.User;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import com.example.demo.service.HeartService;
import com.example.demo.service.UserService;


@Controller
public class UserViewController {
	
	@Autowired
	UserService userService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	CommentService commentService;
	@Autowired
	BoardService boardService;
	@Autowired
	HeartService heartService;
	
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/user/{id}")
	public String userInfo(@PathVariable Long id, Model model) {
		User user = userService.findById(id);
		List<Board> boards = userService.findByUserId(id);
		List<Comment> comments = commentService.commentByUserId(id);
		model.addAttribute("user", user);
		model.addAttribute("boards", boards);
		List<BoardViewResponse> bvrs = new ArrayList<>(); // bvrs 리스트를 먼저 선언

		for (int i = 0; i < comments.size(); i++) {
		    BoardViewResponse boardViewResponse = boardService.findById(comments.get(i).getBoard().getId());
		    bvrs.add(boardViewResponse); // bvrs 리스트에 값을 추가
		}

		model.addAttribute("bvrs", bvrs); // 완성된 bvrs 리스트를 모델에 추가;
		
		List<Heart> hearts = heartService.findByUserId(id);
		List<BoardViewResponse> likes = new ArrayList<>();
		for(int i = 0; i < hearts.size(); i++) {
			BoardViewResponse boardViewResponse = boardService.findById(hearts.get(i).getBoard().getId());
			likes.add(boardViewResponse);
		}
		model.addAttribute("likes", likes);
			
			
		
		
		return "user";
	}
	
	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable Long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		System.out.println(user);
		System.out.println(user.getPassword());
		return "delete";
		
	}
	
	@PostMapping("/user/delete/{id}")
	public String deleteUserById(@PathVariable Long id, String password) {
	    User user = userService.findById(id);
	    if (user == null) {
	        return "userNotFound"; // 사용자가 존재하지 않는 경우 처리
	    }
	    String pass = user.getPassword();
	    if (bCryptPasswordEncoder.matches(password, pass)) {
	        userService.deleteById(id);
	        return "redirect:/home";
	    } else {
	        return "deletefail";
	    }
	}

	
}
