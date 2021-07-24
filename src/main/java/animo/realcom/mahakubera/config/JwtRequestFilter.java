package animo.realcom.mahakubera.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.function.Function;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import animo.realcom.mahakubera.exception.InvalidAuthenticatonException;
import animo.realcom.mahakubera.modal.UserDetailsDTO;
import animo.realcom.mahakubera.modal.response.ErrorResponse;
import animo.realcom.mahakubera.service.UserDetailsFacade;
import animo.realcom.mahakubera.util.AppConstants;
import animo.realcom.mahakubera.util.ExceptionMessageConstants;
import animo.realcom.mahakubera.util.JwtTokenUtil;
import animo.realcom.mahakubera.util.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsFacade userDetailsFacade;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AbstractPropertiesReader errorMessageReader;

	@Autowired
	private ObjectMapper mapper;

	public JwtRequestFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			Function<Claims, String> userRoleClaim = (claim) -> (String) claim.get(AppConstants.USER_TYPE_LABEL);
			String userRole = jwtTokenUtil.getClaimFromToken(jwtToken, userRoleClaim);
			UserDetailsDTO.UserDetailsDTOBuilder userDetailsDTOBuilder = UserDetailsDTO.builder();
			userDetailsDTOBuilder.emailId(username);
			userDetailsDTOBuilder.userType(UserType.getUserTypeFromValue(userRole));
			UserDetailsDTO userDetails = userDetailsDTOBuilder.build();
			userDetails = this.userDetailsFacade.getUserDetails(userDetails);
			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				try {
					checkUserAccountActivity(userDetails);
				} catch (InvalidAuthenticatonException ex) {
					handleInvalidAuthenticationException(response, ex);
					return;
				}

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

	private void checkUserAccountActivity(UserDetailsDTO userDetails) {
		// Check user account is Disabled
		if (!userDetails.isEnabled()) {
			if (userDetails.getUserType().equals(UserType.VENDOR)) {
				throw new InvalidAuthenticatonException(ExceptionMessageConstants.VENDOR_ACOUNT_LOCKED);
			}
		}
	}

	private void handleInvalidAuthenticationException(HttpServletResponse response, InvalidAuthenticatonException e) {
		ErrorResponse.ErrorResponseBuilder errorResponseBuilder = ErrorResponse.builder();

		errorResponseBuilder.status(HttpStatus.UNAUTHORIZED.name()).error(e.getErrorId().split("_")[1])
				.developerMessage(e.getErrorId().split("_")[0]).userMessage(errorMessageReader.getValue(e.getErrorId()))
				.timestamp(LocalDateTime.now());
		ErrorResponse errorResponse = errorResponseBuilder.build();

		response.setContentType("application/json");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		try {
			response.getWriter().write(mapper.writeValueAsString(errorResponse));
			logger.error(errorResponse);
		} catch (IOException e1) {
			// TODO Auto-generated catch block

		}

	}

}
