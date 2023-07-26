package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.HeartRequestDto;
import com.example.demo.entity.Heart;
import com.example.demo.entity.User;
import com.example.demo.service.HeartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HeartController {
	@Autowired
	HeartService heartService;
	
	@PostMapping("/board/{id}/heart")
	public String saveHeart(HeartRequestDto heartRequestDto, @PathVariable Long id, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		heartRequestDto.setBoardId(id);
		heartRequestDto.setUserId(user.getId());
		System.out.println(heartRequestDto);
		heartService.saveHeart(heartRequestDto);
		model.addAttribute("liked", true);
		
		return "redirect:/board/" + id;
		
	}
	
	@PostMapping("/board/{id}/delete")
	public String deleteHeart(HeartRequestDto heartRequestDto, Model model, @PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		heartRequestDto.setBoardId(id);
		heartRequestDto.setUserId(user.getId());
		heartService.deleteHeart(heartRequestDto);
		model.addAttribute("liked", false);
		return "redirect:/board/" + id;
	}

}
