package com.episen.ms.setting;


import com.episen.ms.model.UserContext;
import com.episen.ms.security.JwTokenValidator;
import com.episen.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter which intercepts every request once and examines the header
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private JwTokenValidator jwtUtilValidator;

    /**
     * Examine incoming requests for the JWT in the header and checks if it is valid
     * If it is, it gets the user details out of the userDetailsService and save it in the security context
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
    		FilterChain filterChain) throws ServletException, IOException { final String 
    	header = request.getHeader("Authorization");

        String username = null;
        
        String jwt = null;

        if (header != null && header.startsWith("Bearer ")) {
        	
            jwt = header.substring(7);
            
            UserContext user = jwtUtilValidator.transforme(jwt);
            
            username = user.getSubject();
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
            
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
            		.buildDetails(request));
            
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }
        
        filterChain.doFilter(request, response);
        
    }
}
