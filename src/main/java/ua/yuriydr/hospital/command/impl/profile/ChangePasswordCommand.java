package ua.yuriydr.hospital.command.impl.profile;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.model.Person;
import ua.yuriydr.hospital.service.PersonService;
import ua.yuriydr.hospital.service.factory.ServiceFactory;
import ua.yuriydr.hospital.utils.PagesManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements Command {

    private static final Logger logger = Logger.getLogger(ChangePasswordCommand.class);

    private static PersonService personService = ServiceFactory.getPersonService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {


        logger.debug("Change password command");

        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("user");

        String newPassword = request.getParameter("newpass");
        String newPasswordRepeat = request.getParameter("newrepeat");

        if (newPassword.equals(newPasswordRepeat) && !newPassword.isEmpty()) {
            logger.debug("Passwords are equal");

            person.setPassword(newPassword);
            personService.changePersonPassword(person);

            session.invalidate();

            return PagesManager.getProperty("path.page.login");
        } else {
            logger.debug("Passwords are not equal");
            session.setAttribute("error", "error");
            return (String) session.getAttribute("currentPage");
        }
    }

}
