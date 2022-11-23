package tinygram;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * A servlet filter redirecting incoming client requests to the serveur root, to let Vue.js handle
 * sub-paths. Maybe improves cache management? It does not seem to have any effet though.
 */
public class RootFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        servletRequest.getRequestDispatcher("/").forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}