package resources;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.api.client.repackaged.com.google.common.base.Strings;

@Component
public class MockUserHeaderFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String user = request.getHeader("user");
		if (!Strings.isNullOrEmpty(user)) {
			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(user);
			Authentication authToken = new UsernamePasswordAuthenticationToken(
					userDetails, userDetails, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);

			// request.setSecurityContext(new SecurityContext() {
			// @Override
			// public boolean isUserInRole(String role) {
			// return userDetails.getAuthorities().contains(role);
			// }
			//
			// @Override
			// public boolean isSecure() {
			// return false;
			// }
			//
			// @Override
			// public Principal getUserPrincipal() {
			// return new Principal() {
			//
			// @Override
			// public String getName() {
			// return userDetails.getUsername();
			// }
			// };
			// }
			//
			// @Override
			// public String getAuthenticationScheme() {
			// return null;
			// }
			// });
		}
		filterChain.doFilter(request, response);
	}
}
