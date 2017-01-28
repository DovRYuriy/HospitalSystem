package ua.yuriydr.hospital.command.impl.profile;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.dto.IncorrectInputData;
import ua.yuriydr.hospital.entity.Person;
import ua.yuriydr.hospital.service.PersonService;
import ua.yuriydr.hospital.service.factory.ServiceFactory;
import ua.yuriydr.hospital.utils.UserFormChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateProfileCommand implements Command {

    private static final Logger logger = Logger.getLogger(ChangePasswordCommand.class);

    private static final PersonService personService = ServiceFactory.getPersonService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Update profile command");

        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("user");

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String phone = request.getParameter("phone");

        person.setName(name);
        person.setSurname(surname);
        person.setPhone(phone);

        boolean invalid = false;
        IncorrectInputData.Builder builder = IncorrectInputData.newBuilder("incorrect");

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

        if (invalid) {
            logger.debug("Invalid data");
            session.setAttribute("incorrectData", builder.build());
        } else {
            logger.debug("Valid data");

            session.removeAttribute("incorrectData");
            personService.updatePerson(person);
        }

        session.setAttribute("user", person);
        return (String) session.getAttribute("currentPage");

    }

}
