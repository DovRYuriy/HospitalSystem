package ua.yuriydr.hospital.filter;


import ua.yuriydr.hospital.model.Person;
import ua.yuriydr.hospital.utils.PagesManager;
import ua.yuriydr.hospital.utils.UserUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * This filter used for check that user authenticated.
 */
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing
    }


    /**
     * Checks that user authenticated or he want to logout.
     *
     * @param servletRequest  defines an object to provide client request information to a servlet.
     * @param servletResponse defines an object to assist a servlet in sending a response to the client.
     * @param filterChain     invoke the next filter in the chain, or if the calling filter is the last filter
     *                        in the chain, to invoke the resource at the end of the chain.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        String path = request.getRequestURI();
        if (path.indexOf("/image") > 0) {
            filterChain.doFilter(request, response);
            return;
        } else if (path.endsWith(".ico")) {
            filterChain.doFilter(request, response);
            return;
        } else if (path.endsWith(".css")) {
            filterChain.doFilter(request, response);
            return;
        }
        String page;
        if (request.getParameter("command") == null) {
            if (session != null) {
                Person person = (Person) session.getAttribute("user");
                if (person == null) {
                    page = PagesManager.getProperty("path.page.login");
                } else {
                    page = UserUtils.getInstance().getPageByRole(person.getRole().getName().toUpperCase());
                }
                request.getRequestDispatcher(page).forward(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //do nothing
    }

}
