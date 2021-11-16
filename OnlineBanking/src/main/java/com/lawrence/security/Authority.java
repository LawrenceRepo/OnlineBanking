package com.lawrence.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = -5122648368492789001L;
	
	private final String permission;

	public Authority(String authority) {
		this.permission = authority;
	}

	@Override
	public String getAuthority() { return permission; }
}
