package ua.yuriydr.hospital.command.impl.staff;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.command.CommandHelper;
import ua.yuriydr.hospital.model.Diagnosis;
import ua.yuriydr.hospital.model.Person;
import ua.yuriydr.hospital.model.PersonDiagnosis;
import ua.yuriydr.hospital.model.Prescription;
import ua.yuriydr.hospital.service.DiagnosisService;
import ua.yuriydr.hospital.service.PersonDiagnosisService;
import ua.yuriydr.hospital.service.PersonService;
import ua.yuriydr.hospital.service.PrescriptionService;
import ua.yuriydr.hospital.service.factory.ServiceFactory;
import ua.yuriydr.hospital.service.factory.impl.DiagnosisServiceImpl;
import ua.yuriydr.hospital.service.factory.impl.PersonDiagnosisServiceImpl;
import ua.yuriydr.hospital.service.factory.impl.PrescriptionServiceImpl;
import ua.yuriydr.hospital.utils.PagesManager;
import ua.yuriydr.hospital.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SetDiagnosisCommand implements Command {

    private static final Logger logger = Logger.getLogger(SetDiagnosisCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        if (!UserUtils.getInstance().hasAccess(request, PagesManager.getProperty("path.page.setDiagnosisPage"))) {
            return PagesManager.getProperty("path.page.error.accessDenied");
        }

        logger.debug("Try to set diagnosis");
        HttpSession session = request.getSession();

        List<PersonDiagnosis> diagnosisList = new ArrayList<>();
        PersonDiagnosis personDiagnosis = new PersonDiagnosis();

        Person person = (Person) session.getAttribute("newPerson");
        if (person == null) {
            diagnosisList = (List<PersonDiagnosis>) session.getAttribute("patientDiagnosis");
            personDiagnosis.setPatient((Person) session.getAttribute("person"));
        } else {
            String s = request.getParameter("chambers");
            Long idChamber = null;
            if (s != null) {
                idChamber = Long.valueOf(s);
            }

            person.setIdChamber(idChamber);
            personDiagnosis.setPatient(person);
        }

        boolean invalid = false;
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName(request.getParameter("diagnosis"));
        if(diagnosis.getName().isEmpty()){
            invalid = true;
        }
        diagnosis.setDescription(request.getParameter("description").trim());

        Prescription prescription = new Prescription();
        prescription.setDrugs(request.getParameter("drugs"));
        prescription.setProcedure(request.getParameter("procedure"));
        prescription.setOperation(request.getParameter("operation"));

        DiagnosisService diagnosisService = ServiceFactory.getDiagnosisService();
        diagnosisService.insertDiagnosis(diagnosis);

        PrescriptionService prescriptionService = ServiceFactory.getPrescriptionService();
        prescriptionService.insertPrescription(prescription);

        personDiagnosis.setDoctor((Person) session.getAttribute("user"));
        personDiagnosis.setDiagnosis(diagnosis);
        personDiagnosis.setPrescription(prescription);
        personDiagnosis.setDate(new Timestamp(System.currentTimeMillis()));
        personDiagnosis.setDischargeDate(null);

        if(invalid){
            session.setAttribute("prescript", prescription);
            session.setAttribute("diagnosis", diagnosis);
            session.setAttribute("incorrectDiagnosis", "incorrectDiagnosis");
            return (String) session.getAttribute("currentPage");
        }

        PersonDiagnosisService personDiagnosisService = ServiceFactory.getPersonDiagnosisService();
        if (personDiagnosisService.insertPatientDiagnosis(personDiagnosis)) {
            logger.debug("successfull added");
            diagnosisList.add(0, personDiagnosis);
            session.setAttribute("patientDiagnosis", diagnosisList);

            if (person != null) {
                PersonService service = ServiceFactory.getPersonService();
                service.updateChamber(person);
            }
        }

        String page;
        if (person == null) {
            page = PagesManager.getProperty("path.page.editPatientPage");
            session.removeAttribute("removeNotAllowed");
        } else {
            Command redirect = CommandHelper.getInstance().defineCommand("redirect");
            return redirect.execute(request, response);
        }
        session.removeAttribute("prescript");
        session.removeAttribute("diagnosis");
        session.removeAttribute("incorrectDiagnosis");
        session.setAttribute("currentPage", page);
        return page;
    }

}
