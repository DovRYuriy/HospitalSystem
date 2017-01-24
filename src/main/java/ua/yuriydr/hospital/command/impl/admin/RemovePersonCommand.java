package ua.yuriydr.hospital.command.impl.admin;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.command.CommandHelper;
import ua.yuriydr.hospital.model.Person;
import ua.yuriydr.hospital.model.PersonDiagnosis;
import ua.yuriydr.hospital.service.PersonDiagnosisService;
import ua.yuriydr.hospital.service.PersonService;
import ua.yuriydr.hospital.service.factory.ServiceFactory;
import ua.yuriydr.hospital.service.factory.impl.PersonServiceImpl;
import ua.yuriydr.hospital.utils.PagesManager;
import ua.yuriydr.hospital.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RemovePersonCommand implements Command {

    private static final Logger logger = Logger.getLogger(RemovePersonCommand.class);

    private static PersonService personService = ServiceFactory.getPersonService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Try to remove person");

        if (!UserUtils.getInstance().hasAccess(request, PagesManager.getProperty("path.page.adminMainPage"))) {
            return PagesManager.getProperty("path.page.error.accessDenied");
        }

        Long id = Long.valueOf(request.getParameter("id"));

        String page = "";

        Person person = personService.findPersonById(id);
        if (person != null) {
            PersonDiagnosisService personDiagnosisService = ServiceFactory.getPersonDiagnosisService();
            List<PersonDiagnosis> personDiagnosisList = personDiagnosisService.findAllByStaffId(person.getIdPerson());
            boolean openPersonDiagnosis = false;
            for (PersonDiagnosis personDiagnosis : personDiagnosisList) {
                if (personDiagnosis.getDischargeDate() == null) {
                    openPersonDiagnosis = true;
                    break;
                }
            }
            if (openPersonDiagnosis) {
                request.getSession().setAttribute("removeFailed", "removeFailed");
            } else {
                for (PersonDiagnosis personDiagnosis : personDiagnosisList) {
                    personDiagnosisService.deletePatientDiagnosis(personDiagnosis);
                }
                if (personService.deletePerson(person)) {
                    request.getSession().removeAttribute("removeFailed");
                }
            }
        } else {
            page = PagesManager.getProperty("path.page.error");
        }

        if (page.isEmpty()) {
            Command redirect = CommandHelper.getInstance().defineCommand("redirect");
            return redirect.execute(request, response);
        }
        return page;
    }

}
