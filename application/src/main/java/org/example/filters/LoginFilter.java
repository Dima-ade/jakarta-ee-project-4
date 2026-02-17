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
import java.util.UUID;

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
        //httpRequest.getMethod()

        if (url.equals("/application-1.0-SNAPSHOT/api/hello/login")) {
            //TODO Adela check if httpMethod=POST
            String authHeader = httpRequest.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Basic ")) {
                // Extract credentials
                String base64Credentials = authHeader.substring("Basic ".length()).trim();
                String credentials = new String(Base64.getDecoder().decode(base64Credentials));
                String[] values = credentials.split(":", 2);
                String username = values[0];
                String password = values[1];

                // Validate user (Example: Hardcoded check)
                boolean isValidUser = ("user".equals(username) || "user2".equals(username));
                boolean isValidPassword = ("password".equals(password));
                if (isValidUser && isValidPassword) {

                    HttpSession session = ((HttpServletRequest) request).getSession(true);
                    System.out.println("LoginFilter.doFilter session.hascode="+session.hashCode());

//                    String token = UUID.randomUUID().toString();
                    long nowMillis = System.currentTimeMillis();
                    String token = JWTToken.createJWT("111", "acoravu", "token", nowMillis);
                    UserTokenRole principal = new UserTokenRole(username, token, Set.of("USER"));

                    session.setAttribute("principal", principal);
                    httpResponse.setHeader("Token", token);
                    httpResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    //chain.doFilter(request, response);
                    return;
                } else {
                    // Authentication failed or not provided
                    httpResponse.setHeader("WWW-Authenticate", "Basic realm=\"Secure Area\"");
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                }
            } else {
                // Authentication failed or not provided
                httpResponse.setHeader("WWW-Authenticate", "Basic realm=\"Secure Area\"");
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }
        } else {
            // User authorized, continue
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}