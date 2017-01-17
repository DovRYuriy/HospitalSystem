package ua.yuriydr.hospital.controller;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.command.CommandHelper;
import ua.yuriydr.hospital.utils.PagesManager;
import ua.yuriydr.hospital.utils.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet which receives client's requests.
 */
public class Controller extends HttpServlet {

    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(Controller.class);

    /**
     * Represents an GET request that accepts request and response arguments
     * and returns no result.
     *
     * @param req  HttpServletRequest object that contains all the client's request information.
     * @param resp HttpServletResponse object to return information to the client.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Represents an POST request that accepts request and response arguments
     * and returns no result.
     *
     * @param req  HttpServletRequest object that contains all the client's request information.
     * @param resp HttpServletResponse object to return information to the client.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Represents handler of POST and GET requests that takes two arguments and forward or redirect
     * to one or another page depending on commands from client's requests.
     * Selects command to need to execute.
     * Checks that user has access to page.
     *
     * @param req  HttpServletRequest object that contains all the client's request information.
     * @param resp HttpServletResponse object to return information to the client.
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Command command = CommandHelper.getInstance().defineCommand(req);
        String page = command.execute(req, resp);

        if (!page.isEmpty()) {
            logger.info("Forward to " + page);
            req.getServletContext().getRequestDispatcher(page).forward(req, resp);
        } else {
            page = PagesManager.getProperty("path.page.error");
            logger.info("Redirect to " + page);
            resp.sendRedirect(req.getContextPath() + page);
        }
    }

}
