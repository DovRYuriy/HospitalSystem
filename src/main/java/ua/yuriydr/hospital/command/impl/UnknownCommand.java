package ua.yuriydr.hospital.command.impl;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.utils.PagesManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownCommand implements Command {

    private static final Logger logger = Logger.getLogger(UnknownCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("unknownCommand", "unknownCommand");
        logger.debug("Unknown command");
        return PagesManager.getProperty("path.page.noCommand");
    }
}
