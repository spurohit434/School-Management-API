package com.wg.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUser {
	String username;
	String password;
	String role;
}
