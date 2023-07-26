package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinUserRequest {
	String email;
	String nickname;
	String password;

}
