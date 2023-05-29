package jp.co.axa.apidemo.config;


import jp.co.axa.apidemo.entities.UserInfo;
import jp.co.axa.apidemo.exception.APIAbortedException;
import jp.co.axa.apidemo.services.UserInfoServiceImpl;
import jp.co.axa.apidemo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    public UserInfoServiceImpl userService;

    @Autowired
    public JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = fetchHeader(request);
        log.debug(header);
        if(StringUtils.isBlank(header)||!header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        else{
            String token = fetchToken(header);
            log.debug(token);
            if(StringUtils.isNotBlank(token)){
                String userName = fetchUserName(token);
                log.debug(userName);
                if(StringUtils.isNotBlank(userName) &&
                        SecurityContextHolder.getContext().getAuthentication() == null){
                    UserInfo userDetails = userService.loadUserByUsername(userName);
                    if(userDetails.getTokenExpired()==Boolean.TRUE){
                        throw new APIAbortedException(HttpStatus.BAD_REQUEST,"user logged out please login");
                    }
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
