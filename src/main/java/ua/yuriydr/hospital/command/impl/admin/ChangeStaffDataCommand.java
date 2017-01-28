package ua.yuriydr.hospital.command.impl.admin;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.command.CommandHelper;
import ua.yuriydr.hospital.dto.IncorrectInputData;
import ua.yuriydr.hospital.entity.Person;
import ua.yuriydr.hospital.service.PersonService;
import ua.yuriydr.hospital.service.factory.ServiceFactory;
import ua.yuriydr.hospital.utils.PagesManager;
import ua.yuriydr.hospital.utils.UserFormChecker;
import ua.yuriydr.hospital.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeStaffDataCommand implements Command {

    private static final Logger logger = Logger.getLogger(ChangeStaffDataCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Try to change staff data");


        if (!UserUtils.getInstance().hasAccess(request, PagesManager.getProperty("path.page.editStaffPage"))) {
            return PagesManager.getProperty("path.page.error.accessDenied");
        }

        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("person");

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String email_old = person.getEmail();

        person.setName(name);
        person.setSurname(surname);
        person.setPhone(phone);
        person.setEmail(email);

        IncorrectInputData.Builder builder = IncorrectInputData.newBuilder("incorrect");
        boolean invalid = false;

        if (!UserFormChecker.userNameCheck(person.getName())) {
            builder.setIncorrectNameAttribute();
            invalid = true;
        }
        if (!UserFormChecker.userSurnameCheck(person.getSurname())) {
            builder.setIncorrectSurnameAttribute();
            invalid = true;
        }
        if (!UserFormChecker.userPhoneCheck(person.getPhone())) {
            builder.setIncorrectPhoneAttribute();
            invalid = true;
        }
        PersonService personService = ServiceFactory.getPersonService();

        if (!email_old.equals(person.getEmail())) {

            if (personService.checkEmail(person.getEmail()) || person.getEmail().isEmpty()) {
                builder.setIncorrectEmailAttribute();
                invalid = true;
            }
        }

        String page;
        if (invalid) {
            logger.debug("Invalid input data");

            session.setAttribute("incorrectData", builder.build());
            session.setAttribute("person", person);
            page = (String) session.getAttribute("currentPage");
        } else {
            if (personService.updatePerson(person)) {
                logger.debug("successful");
            } else {
                logger.debug("unsuccessful updated");
            }
            Command redirect = CommandHelper.getInstance().defineCommand("redirect");
            return redirect.execute(request, response);
        }

        return page;
    }
}
