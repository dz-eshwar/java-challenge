package jp.co.axa.apidemo.config;


import jp.co.axa.apidemo.services.UserInfoServiceImpl;
import jp.co.axa.apidemo.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
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

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    public UserInfoServiceImpl userService;

    @Autowired
    public JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = fetchHeader(request);
        System.out.println(header);
        if(StringUtils.isBlank(header)||!header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        else{
            String token = fetchToken(header);
            System.out.println(token);
            if(StringUtils.isNotBlank(token)){
                String userName = fetchUserName(token);
                System.out.println(userName);
                if(StringUtils.isNotBlank(userName) &&
                        SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = userService.loadUserByUsername(userName);

                    if(jwtUtil.validateToken(token,userDetails)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }

        }
        filterChain.doFilter(request,response);
    }

    public String fetchHeader(HttpServletRequest request){
        if(StringUtils.isNotBlank(request.getHeader("Authorization"))){
            return request.getHeader("Authorization");
        }
        else{
            return "";
        }
    }

    public String fetchToken(String header){
        if(header.startsWith("Bearer ")){
            return header.replace("Bearer ","");
        }
        else{
            return "";
        }
    }

    public String fetchUserName(String token){
        return jwtUtil.getUsernameFromToken(token);
    }
}