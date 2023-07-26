package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.demo.dto.AddBoardRequest;
import com.example.demo.dto.BoardRequestDto;
import com.example.demo.dto.UpdateBoardRequest;
import com.example.demo.entity.Board;
import com.example.demo.entity.User;
import com.example.demo.service.BoardService;
import com.example.demo.service.UserDetailService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Controller
public class BoardApiController {
	@Autowired
	BoardService boardService;
	@Autowired
	UserService userService;
	@Autowired
	UserDetailService userDetailService;
	
	@PostMapping("/write")
	public String writeBoard(BoardRequestDto dto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자인 경우에만 작성자 닉네임을 설정
        if (auth.isAuthenticated()) {
            User user = (User) auth.getPrincipal();
          String nickname =  user.getNickname();
          dto.setWriter(nickname);
          dto.setUser(user);
          System.out.println("boardapicontroller" + user);
          Board board = boardService.save(dto);
          System.out.println(board.getUser());
        }
	
		
		return "redirect:/home";
		
	}
//	@GetMapping("/home")
//	public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
//			String searchKeyword) {
//	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	    if (auth.isAuthenticated()) {
//	        User user = userDetailService.loadUserByUsername(auth.getName());
//	        model.addAttribute("isLoggedIn", true);
//	        model.addAttribute("nickname", user.getNickname());
//	       
//	    } else {
//	        model.addAttribute("isLoggedIn", false);
//	   
//	    }
//	    
//	    List<Board> boards = boardService.findAll();
//	    model.addAttribute("boards", boards);
//	    Page<Board> list = null;
//	    if(searchKeyword == null) {
//	    	list = boardService.pageList(pageable);
//	    }else {
//	    	list = boardService.boardSearchList(searchKeyword, pageable);
//	    }
//	    int nowPage = list.getPageable().getPageNumber() + 1;
//	    int startPage = Math.max(nowPage - 4, 1);
//	    int endPage = Math.min(nowPage + 5, list.getTotalPages());
//	    
//	    model.addAttribute("list", list);
//	    model.addAttribute("nowPage", nowPage);
//	    model.addAttribute("startPage", startPage);
//	    model.addAttribute("endPage", endPage);
//	    model.addAttribute("startPageGreaterThanOne", startPage > 1);
//	    model.addAttribute("previous", list.hasPrevious());
//	    model.addAttribute("next", list.hasNext());
//	    model.addAttribute("endPageLessThanTotalPages", endPage < list.getTotalPages());
//	    
//	    return "home";
//	  
//	}
	@PostMapping("/update/{id}")
	public String changeBoard(@PathVariable long id, @RequestParam("title") String title, @RequestParam("content") String content)
	{
		UpdateBoardRequest request = new UpdateBoardRequest();
		request.setTitle(title);
		request.setContent(content);

		Board board = boardService.update(id, request);
		return "redirect:/board/" + id; 
	}
	@PostMapping("/delete/{id}")
	public String deleteBoard(@PathVariable long id) {
		boardService.delete(id);
		return "redirect:/home";
	}

	
	@GetMapping("/home")
	public String boardList(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth.isAuthenticated()) {
	        User user = userDetailService.loadUserByUsername(auth.getName());
	        model.addAttribute("isLoggedIn", true);
	        model.addAttribute("nickname", user.getNickname());
	        model.addAttribute("id", user.getId());
	    } else {
	        model.addAttribute("isLoggedIn", false);
	    }

	    List<Board> boards = boardService.findAll();
	    model.addAttribute("boards", boards);
	    
	    Page<Board> boardList = boardService.getBoardList(pageable);
	    model.addAttribute("boardList", boardService.getBoardList(pageable));
	    
	    
	    model.addAttribute("boardList", boardService.getBoardList(pageable));
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
	    
	    
	    return "home";
	}
	
	@GetMapping("/search")
	public String search(String keyword, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    if (auth.isAuthenticated()) {
		        User user = userDetailService.loadUserByUsername(auth.getName());
		        model.addAttribute("isLoggedIn", true);
		        model.addAttribute("nickname", user.getNickname());
		    } else {
		        model.addAttribute("isLoggedIn", false);
		    }
		Page<Board> boardSearched = boardService.search(keyword, pageable);
		model.addAttribute("boardSearched", boardSearched);
		
		 model.addAttribute("boardList", boardService.getBoardList(pageable));
	        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
	        model.addAttribute("next", pageable.next().getPageNumber());
		return "search";
	}


}
