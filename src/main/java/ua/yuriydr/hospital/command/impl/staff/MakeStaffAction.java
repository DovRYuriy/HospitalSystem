package ua.yuriydr.hospital.command.impl.staff;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.entity.Person;
import ua.yuriydr.hospital.entity.PersonDiagnosis;
import ua.yuriydr.hospital.entity.Prescription;
import ua.yuriydr.hospital.service.PrescriptionService;
import ua.yuriydr.hospital.service.factory.ServiceFactory;
import ua.yuriydr.hospital.utils.PagesManager;
import ua.yuriydr.hospital.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class MakeStaffAction implements Command {

    private static final Logger logger = Logger.getLogger(MakeStaffAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        if (!UserUtils.getInstance().hasAccess(request, PagesManager.getProperty("path.page.editPatientPage"))
                && !UserUtils.getInstance().hasAccess(request, PagesManager.getProperty("path.page.nurseMainPage"))) {
            return PagesManager.getProperty("path.page.error.accessDenied");
        }

        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("user");
        String action = request.getParameter("whatAction");

        logger.debug("Try to make this action: " + action);

        Long prescriptionId = Long.valueOf(request.getParameter("prescriptionId"));
        List<PersonDiagnosis> diagnosisList = (List<PersonDiagnosis>) session.getAttribute("patientDiagnosis");
        PrescriptionService prescriptionService = ServiceFactory.getPrescriptionService();

        for (PersonDiagnosis personDiagnosis : diagnosisList) {
            Prescription p = personDiagnosis.getPrescription();
            if (personDiagnosis.getPrescription().getIdPrescription().equals(prescriptionId)) {
                switch (action) {
                    case "drugs":
                        p.setDrugs("");
                        break;
                    case "operation":
                        if (person.getRole().getName().equals("doctor")) {
                            p.setOperation("");
                        }
                        break;
                    case "procedure":
                        p.setProcedure("");
                        break;
                }
                if (prescriptionService.updatePrescription(p)) {
                    logger.debug("successful action");
                } else {
                    logger.debug("unsuccussful action");
                }
                break;
            }
        }

        session.setAttribute("patientDiagnosis", diagnosisList);
        return (String) session.getAttribute("currentPage");
    }

}
