package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CommentRequestDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentApiController {
	@Autowired
	CommentService commentService;
	@Autowired
	BoardService boardService;

@PostMapping("/board/{id}/comment")
public String save(@PathVariable Long id, CommentRequestDto dto) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // 인증된 사용자인 경우에만 작성자 닉네임을 설정
    if (auth.isAuthenticated()) {
    	User user = (User) auth.getPrincipal();
        dto.setUser(user);
        commentService.saveComment(id, dto);
    }
    return "redirect:/board/" + id;
}

}
