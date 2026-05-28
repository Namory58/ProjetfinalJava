package org.example.projetfinal.Security;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.projetfinal.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private UsersService usersService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {

        //String urlRequest = request.getRequestURI();

        try {
            String header = request.getHeader("Authorization");
            // Authentification
            // Authorization: bearer eyolfdilfsdkfdslkflsdkfjjdslfhlsdfjlsdfjlk
            String token = header.substring(7); //eyolfdilfsdkfdslkflsdkfjjdslfhlsdfjlsdfjlk
            int userId = JwtTokenManager.getUser(token);
            UserDetails user = usersService.getUserById(userId);

            if (user == null){
                filterChain.doFilter(request, response);
                return;
            }
            // On le passe aux controllers grâce au context
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            //logger.info("Trying parse token but failed");
        }

        filterChain.doFilter(request, response);
    }

}
