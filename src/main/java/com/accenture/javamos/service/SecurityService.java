package com.accenture.javamos.service;

import com.accenture.javamos.controller.exception.UnauthorizedException;
import com.accenture.javamos.entity.User;
import com.accenture.javamos.repository.UserRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  private UserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  public SecurityService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserAuthenticated() throws UnauthorizedException {
    try {
      Authentication authentication = SecurityContextHolder
        .getContext()
        .getAuthentication();
      String name = authentication.getName();
      return userRepository.findUserByEmail(name).get();
    } catch (NoSuchElementException e) {
      throw new UnauthorizedException();
    }
  }

  public void authenticate(String username, String password)
    throws DisabledException, BadCredentialsException {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(username, password)
    );
  }
}
