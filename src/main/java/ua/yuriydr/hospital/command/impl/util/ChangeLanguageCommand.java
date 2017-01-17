package ua.yuriydr.hospital.command.impl.util;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.utils.PagesManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;


public class ChangeLanguageCommand implements Command {

    private static final Logger logger = Logger.getLogger(ChangeLanguageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object currentPage = session.getAttribute("currentPage");

        String lang = request.getParameter("language");
        logger.debug("Set language: " + lang);
        session.setAttribute("language", new Locale(lang));

        if (currentPage != null) {
            return (String) currentPage;
        }
        return PagesManager.getProperty("path.page.login");
    }

}
