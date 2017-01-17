package ua.yuriydr.hospital.command.impl.admin;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.command.CommandHelper;
import ua.yuriydr.hospital.command.impl.staff.RegistrationPatientCommand;
import ua.yuriydr.hospital.dto.IncorrectInputData;
import ua.yuriydr.hospital.model.Person;
import ua.yuriydr.hospital.model.Role;
import ua.yuriydr.hospital.service.PersonService;
import ua.yuriydr.hospital.service.factory.ServiceFactory;
import ua.yuriydr.hospital.utils.PagesManager;
import ua.yuriydr.hospital.utils.UserFormChecker;
import ua.yuriydr.hospital.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegistrationPersonCommand implements Command {

    private static final Logger logger = Logger.getLogger(RegistrationPatientCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Try to register staff");

        if (!UserUtils.getInstance().hasAccess(request, PagesManager.getProperty("path.page.registration"))) {
            return PagesManager.getProperty("path.page.error.accessDenied");
        }

        String initPassword = "1111";
        HttpSession session = request.getSession();

        boolean invalid = false;
        IncorrectInputData.Builder builder = IncorrectInputData.newBuilder("incorrect");

        Person person = new Person();
        person.setName(request.getParameter("name"));
        person.setSurname(request.getParameter("surname"));
        person.setPhone(request.getParameter("phone"));
        Date date = null;
        try {
            date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthday")).getTime());
        } catch (ParseException exc) {
            logger.error("Error parse date: " + exc);
            invalid = true;
            builder.setIncorrectDateAttribute();
        }
        person.setBirthday(date);
        person.setEmail(request.getParameter("email"));
        person.setPassword(initPassword);

        Role role = new Role();
        role.setIdRole(Long.valueOf(request.getParameter("role")));
        person.setRole(role);

        String s = request.getParameter("chambers");
        Long idChamber = null;
        if (s != null) {
            idChamber = Long.valueOf(s);
        }

        person.setIdChamber(idChamber);

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
        if (personService.checkEmail(person.getEmail()) || person.getEmail().isEmpty()) {
            builder.setIncorrectEmailAttribute();
            invalid = true;
        }
        String page;
        if (invalid) {
            logger.debug("Invalid data");

            session.setAttribute("incorrectData", builder.build());
            session.setAttribute("person", person);
            page = (String) session.getAttribute("currentPage");
        } else {
            logger.debug("valid data");

            if (personService.insertPerson(person)) {
                logger.debug("successful added");
            } else {
                logger.debug("unsuccessful added");
            }
            Command redirect = CommandHelper.getInstance().defineCommand("redirect");
            return redirect.execute(request, response);
        }

        return page;
    }
}
