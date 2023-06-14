package com.security.demo.security;

import com.security.demo.base.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

/**
 * @author farhan
 */
@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;
	@Value("${jwtSecret}")
	private String jwtSecret;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private UserDetailsService userDetailsServiceImpl;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null) {
			String token = bearerToken.replace("Bearer ", "");

			String md5Hex = DigestUtils.md5Hex(token).toUpperCase();
			log.info("md5Hex {}", md5Hex);

			if(redisTemplate.hasKey(md5Hex)) {
				throw new AuthenticationServiceException(ResponseMessage.TOKEN_IS_INVALID);
			}

			String username = jwtUtils.getUserNameFromJwtToken(token);
			try {
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, userDetails.getPassword(), userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		filterChain.doFilter(request, response);
	}

}
