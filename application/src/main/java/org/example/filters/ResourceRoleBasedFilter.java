package org.example.filters;

import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.beans.AllowedRoles;
import org.example.entities.UserTokenRole;

import java.io.IOException;

public class ResourceRoleBasedFilter implements Filter {

    @Inject
    private AllowedRoles allowedRoles;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("\n\tRoleBasedAuthFilter.doFilter allowedRoles="+allowedRoles);

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String token = req.getHeader("Token");

        HttpSession session = req.getSession(false);
        System.out.println("ResourceRoleBasedFilter.doFilter session.hascode="+session.hashCode());

        // luam principalul (care contine user, token, lista de roluri) din sesiune
        UserTokenRole userPassRole = null;
        if (session != null) {
            userPassRole = (UserTokenRole) session.getAttribute("principal");
        }
        String url = req.getRequestURI();
        
        if (userPassRole!= null && token.equals(userPassRole.getToken()) && allowedRoles.hasPermission(userPassRole, url)) {
            chain.doFilter(request, response); // end point is logged and has permission
        } else {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        }
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}