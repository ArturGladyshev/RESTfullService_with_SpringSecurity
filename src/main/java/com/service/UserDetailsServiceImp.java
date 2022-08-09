package com.service;

import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImp implements UserDetailsService
{

		private UserService userService;

		public UserDetailsServiceImp(@Autowired UserService userService) {
				this.userService = userService;
		}

		@Override
		@Transactional
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
		{
				User user = userService.getUserByUsername(username).
					orElseThrow(() -> new UsernameNotFoundException("User Not Found with name: " + username));
				return UserDetailsImp.build(user);
		}


}
