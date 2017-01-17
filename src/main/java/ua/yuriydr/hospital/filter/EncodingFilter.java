package ua.yuriydr.hospital.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter that sets encoding.
 */
public class EncodingFilter implements Filter {

    private static final Logger logger = Logger.getLogger(EncodingFilter.class);

    private String encoding = "UTF-8";

    /**
     * Takes init parameter from filter config.
     *
     * @param filterConfig FilterConfig object, that has init params.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
        logger.debug(encoding);
    }

    /**
     * Sets character encoding to servlet request.
     *
     * @param servletRequest  defines an object to provide client request information to a servlet.
     * @param servletResponse defines an object to assist a servlet in sending a response to the client.
     * @param filterChain     invoke the next filter in the chain, or if the calling filter is the last filter
     *                        in the chain, to invoke the resource at the end of the chain.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Filter");
        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {
        //do nothing
    }
}
