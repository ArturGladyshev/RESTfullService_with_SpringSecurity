package com.service;

import com.entity.Animal;
import com.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsImp implements UserDetails
{

private static final long serialVersionUID = 1L;

		private long id;

		private String username;

		@JsonIgnore
		private String password;

		private List<Animal> animals;

		public Collection<? extends GrantedAuthority> authorities;

		public UserDetailsImp(long id, String name, String password, List<Animal> animals, Collection<? extends GrantedAuthority> authorities)
		{
				this.id = id;
				this.username = name;
				this.password = password;
				this.animals = animals;
				this.authorities = authorities;
		}

		public UserDetailsImp() {

		}

		public static UserDetailsImp build(User user)
		{
				List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(
					role.getName().name())).collect(Collectors.toList());
				return new UserDetailsImp(
					user.getId(),
					user.getUsername(),
					user.getPassword(),
					user.getAnimals(),
					authorities);
		}

		@Override public Collection<? extends GrantedAuthority> getAuthorities()
		{
				return authorities;
		}

		@Override public String getPassword()
		{
				return password;
		}

		@Override public String getUsername()
		{
				return username;
		}

		public long getId()
		{
				return id;
		}

		public List<Animal> getAnimals()
		{
				return animals;
		}

		@Override public boolean isAccountNonExpired()
		{
				return true;
		}

		@Override public boolean isAccountNonLocked()
		{
				return true;
		}

		@Override public boolean isCredentialsNonExpired()
		{
				return true;
		}

		@Override public boolean isEnabled()
		{
				return true;
		}


}
