package com.airline.security;

import com.airline.entity.UserEntity;
import com.airline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public static final String USER_ATTR = "authenticatedUser";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.isTokenValid(token)) {
                Long userId = jwtUtil.getUserIdFromToken(token);
                Optional<UserEntity> user = userRepository.findById(userId);
                if (user.isPresent()) {	
                	UserEntity authenticatedUser = user.get();
                    request.setAttribute(USER_ATTR, authenticatedUser);

                    request.setAttribute("userRole", authenticatedUser.getRole());
                    request.setAttribute("userId", authenticatedUser.getId());
                    request.setAttribute("userEmail", authenticatedUser.getEmail());
                }
            }
        }
        
//        System.out.println(">>> JWT Filter is running for path: " + request.getRequestURI());

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Không cần thiết, để trống
    }

    @Override
    public void destroy() {
        // Không cần thiết, để trống
    }
}
