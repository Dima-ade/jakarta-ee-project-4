package org.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.entities.UserTokenRole;

import java.io.IOException;
import java.util.Base64;
import java.util.Set;

@WebFilter("/api") // Filter applies to /api/hello/ endpoints
public class LoginFilter implements Filter {

    public LoginFilter() {
        System.out.println("BasicAuthFilter.BasicAuthFilter");
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = httpRequest.getRequestURI();

        if(url.equals("/application-1.0-SNAPSHOT/api/hello/login")) {
            String authHeader = httpRequest.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Basic ")) {
                // Extract credentials
                String base64Credentials = authHeader.substring("Basic ".length()).trim();
                String credentials = new String(Base64.getDecoder().decode(base64Credentials));
                String[] values = credentials.split(":", 2);
                String username = values[0];
                String password = values[1];

                // Validate user (Example: Hardcoded check)
                if ("user".equals(username) && "password".equals(password)) {

                    HttpSession session = ((HttpServletRequest) request).getSession(true);

                    UserTokenRole principal = new UserTokenRole(username, authHeader, Set.of("USER"));

                    session.setAttribute("principal", principal);
                    chain.doFilter(request, response);
                    return;
                } else {
                    // Authentication failed or not provided
                    httpResponse.setHeader("WWW-Authenticate", "Basic realm=\"Secure Area\"");
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                }
            }
            } else {
                // User authorized, continue
                chain.doFilter(request, response);
                return;
            }
        // Authentication failed or not provided
        httpResponse.setHeader("WWW-Authenticate", "Basic realm=\"Secure Area\"");
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}