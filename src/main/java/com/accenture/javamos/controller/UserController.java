package com.accenture.javamos.controller;

import com.accenture.javamos.configuration.JwtTokenUtil;
import com.accenture.javamos.entity.User;
import com.accenture.javamos.model.JwtRequest;
import com.accenture.javamos.model.JwtResponse;
import com.accenture.javamos.model.UserResponse;
import com.accenture.javamos.service.JwtUserDetailsService;
import com.accenture.javamos.service.SecurityService;
import com.accenture.javamos.service.UserService;
import java.util.Optional;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService userDetailsService;

  @Autowired
  private SecurityService securityService;

  @PostMapping("/signup")
  @ResponseBody
  public ResponseEntity<UserResponse> add(@RequestBody User user)
    throws JSONException {
    try {
      Optional<User> userExists = userService.findUserByEmail(user.getEmail());
      if (userExists.isPresent()) {
        return new ResponseEntity<UserResponse>(
          new UserResponse(
            false,
            "User already exists.",
            userExists.get().getEmail()
          ),
          HttpStatus.BAD_REQUEST
        );
      }

      userService.add(user);
      return new ResponseEntity<>(
        new UserResponse(true, "User created.", user.getEmail()),
        HttpStatus.CREATED
      );
    } catch (Exception e) {
      return new ResponseEntity<>(
        new UserResponse(false, e.toString(), user.getEmail()),
        HttpStatus.BAD_REQUEST
      );
    }
  }

  @RequestMapping(value = "/signin", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(
    @RequestBody JwtRequest authenticationRequest
  )
    throws Exception {
    authenticate(
      authenticationRequest.getEmail(),
      authenticationRequest.getPassword()
    );
    final UserDetails userDetails = userDetailsService.loadUserByUsername(
      authenticationRequest.getEmail()
    );
    final String token = jwtTokenUtil.generateToken(userDetails);

    // Authentication authentication = SecurityContextHolder
    //   .getContext()
    //   .getAuthentication();
    String authenticatedUserName = userService
      .findUserByEmail(userDetails.getUsername())
      .get()
      .getRealName();
    return ResponseEntity.ok(new JwtResponse(token, authenticatedUserName));
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
      );
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }

  @GetMapping(path = "/whoami")
  public User authenticatedUser() {
    return securityService.getUserAuthenticated();
  }
}
