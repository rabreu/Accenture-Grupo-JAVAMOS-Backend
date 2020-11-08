package com.accenture.javamos.controller;

import com.accenture.javamos.configuration.JwtTokenUtil;
import com.accenture.javamos.controller.exception.EmailAlreadyTakenException;
import com.accenture.javamos.controller.exception.UnauthorizedException;
import com.accenture.javamos.converter.user.UserConverter;
import com.accenture.javamos.dto.UserDTO;
import com.accenture.javamos.entity.User;
import com.accenture.javamos.model.JwtRequest;
import com.accenture.javamos.model.JwtResponse;
import com.accenture.javamos.service.JwtUserDetailsService;
import com.accenture.javamos.service.SecurityService;
import com.accenture.javamos.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  private UserConverter userConverter;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService userDetailsService;

  @Autowired
  private SecurityService securityService;

  @PostMapping("/signup")
  @ResponseBody
  public ResponseEntity<?> add(@RequestBody @Valid UserDTO user)
    throws EmailAlreadyTakenException {
    User added = userService.createNewUser(
      userConverter.toEntity().convert(user)
    );

    return new ResponseEntity<UserDTO>(
      userConverter.toDTO().convert(added),
      HttpStatus.CREATED
    );
  }

  @RequestMapping(value = "/signin", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(
    @RequestBody JwtRequest authenticationRequest
  ) {
    securityService.authenticate(
      authenticationRequest.getEmail(),
      authenticationRequest.getPassword()
    );

    final UserDetails userDetails = userDetailsService.loadUserByUsername(
      authenticationRequest.getEmail()
    );

    final String token = jwtTokenUtil.generateToken(userDetails);

    String authenticatedUserName = userService
      .findUserByEmail(userDetails.getUsername())
      .get()
      .getRealName();

    return ResponseEntity.ok(new JwtResponse(token, authenticatedUserName));
  }

  @GetMapping(path = "/whoami")
  public ResponseEntity<UserDTO> authenticatedUser()
    throws UnauthorizedException {
    return ResponseEntity.ok(
      userConverter.toDTO().convert(securityService.getUserAuthenticated())
    );
  }
}
