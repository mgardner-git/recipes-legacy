package org.recipes.filters;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.recipes.users.User;
import org.recipes.util.SessionStuff;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

//http://srlawr.blogspot.com/2011/09/creating-custom-filter-in-spring.html
public class ExpiredPasswordFilter extends OncePerRequestFilter {
	
	@Override
	public void destroy() {
		// ...
	}

	
	public void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain chain)
	throws IOException, ServletException {
		try {
			HttpSession session = ((HttpServletRequest)request).getSession();
			User loggedInUser = (User)session.getAttribute(SessionStuff.USER);
			
			if (loggedInUser == null) {
				session.setAttribute(SessionStuff.LOGIN_ERROR, "You are not logged in. Please log in.");
				((HttpServletResponse)response).setStatus(HttpServletResponse.SC_FORBIDDEN);
			}else {			
				chain.doFilter(request, response);
			}
		} catch (IllegalArgumentException iae) {
			response.setContentType("application/json");
			((HttpServletResponse)response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
			writer.write(iae.getMessage());
			writer.flush();
			writer.close();
		}

	}

}
