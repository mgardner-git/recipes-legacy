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
public class ErrorFilter extends OncePerRequestFilter {
	
	@Override
	public void destroy() {
		// ...
	}

	
	public void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain chain)
	throws IOException, ServletException {
		try {			
			chain.doFilter(request, response);
			
		} catch (Exception e) {
			response.setContentType("application/json");
			((HttpServletResponse)response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
			writer.write(e.getMessage());
			writer.flush();
			writer.close();
		}

	}

}
