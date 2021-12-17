package com.episen.ms.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.episen.ms.model.Authentication;
import com.episen.ms.model.UserContext;
import com.episen.ms.security.JwTokenGenerator;
import com.episen.ms.security.JwTokenValidator;
import com.episen.ms.service.UserService;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwTokenGenerator jwtTokenUtil;

    @Autowired
    private JwTokenValidator jwtTokenUtilValidator;

    /** First Endpoint using JWT to return the username of the subject connected */
   
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)  
    public String getUser(@RequestBody Authentication authentication) {
    	
        return "username : " + jwtTokenUtilValidator.transforme(authentication.getJwt()).getSubject();
   
    }
    
    /** Second endpoint using username and password to authenticate and generate a JWT */
    
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserContext userContext) throws Exception {
        
    	try {
    		
            authenticationManager.authenticate(
            		
            		new UsernamePasswordAuthenticationToken(userContext.getSubject(), userContext.getPassword())
            
            );
            
        }
        
    	catch (BadCredentialsException e) {
    		
    		throw new Exception("Incorrect username or password", e);
    	
    	}
    	
    	
    	final UserDetails user = userService.loadUserByUsername(userContext.getSubject());
    	
    	final String jwt = jwtTokenUtil.generateToken(user.getUsername(),new ArrayList<>());

        return ResponseEntity.ok(new Authentication(jwt));
        
    }
    
}
