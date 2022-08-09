package com.controller;

import com.config.jwt.JwtUtils;
import com.entity.Role;
import com.entity.User;
import com.enums.RoleEnum;
import com.pojo.JwtResponse;
import com.pojo.LoginRequest;
import com.pojo.MessageResponse;
import com.pojo.SignupRequest;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController
{
		private AuthenticationManager authenticationManager;

		private UserRepository userRepository;

		private RoleRepository roleRepository;

		private PasswordEncoder passwordEncoder;

		private JwtUtils jwtUtils;

		@Autowired
		public AuthController(
			AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, JwtUtils jwtUtils)
		{
				this.authenticationManager = authenticationManager;
				this.userRepository = userRepository;
				this.roleRepository = roleRepository;
				this.passwordEncoder = passwordEncoder;
				this.jwtUtils = jwtUtils;
		}


		@PostMapping("/signin")
		public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest)
		{
				Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateJwtToken(authentication);
				UserDetailsImp userDetails = (UserDetailsImp)authentication.getPrincipal();
				List<String> roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());
				return ResponseEntity.ok(new JwtResponse(jwt,
					userDetails.getUsername(),
					userDetails.getId(),
					roles));
		}

		@PostMapping("/signup")
		public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest)
		{
				if(userRepository.existsByUsername(signupRequest.getUsername()))
				{
						return ResponseEntity
							.badRequest()
							.body(new MessageResponse("Error: Username is exist"));
				}
				User user = new User(signupRequest.getUsername(),
					passwordEncoder.encode(signupRequest.getPassword()));
				Set<String> reqRoles = signupRequest.getRoles();
				Set<Role> roles = new HashSet<>();
				if(reqRoles == null)
				{
						Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
						roles.add(userRole);
				}
				else
				{
						reqRoles.forEach(role -> {
								switch(role)
								{
										case "admin":
												Role adminRole = roleRepository
													.findByName(RoleEnum.ROLE_ADMIN)
													.orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
												roles.add(adminRole);
												break;
										case "mod":
												Role modRole = roleRepository
													.findByName(RoleEnum.ROLE_MODERATOR)
													.orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
												roles.add(modRole);
												break;
										default:
												Role userRole = roleRepository
													.findByName(RoleEnum.ROLE_USER)
													.orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
												roles.add(userRole);
								}
						});
				}
				user.setRoles(roles);
				userRepository.save(user);
				return ResponseEntity.ok(new MessageResponse("User CREATED"));
		}
}
