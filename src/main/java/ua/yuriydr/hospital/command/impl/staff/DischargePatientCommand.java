package ua.yuriydr.hospital.command.impl.staff;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.command.CommandHelper;
import ua.yuriydr.hospital.model.Diagnosis;
import ua.yuriydr.hospital.model.PersonDiagnosis;
import ua.yuriydr.hospital.model.Prescription;
import ua.yuriydr.hospital.service.DiagnosisService;
import ua.yuriydr.hospital.service.PersonDiagnosisService;
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

public class DischargePatientCommand implements Command {

    private static final Logger logger = Logger.getLogger(DischargePatientCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        if (!UserUtils.getInstance().hasAccess(request, PagesManager.getProperty("path.page.dischargePatientPage"))) {
            return PagesManager.getProperty("path.page.error.accessDenied");
        }

        logger.debug("Start discharge patient command");

        HttpSession session = request.getSession();
        PersonDiagnosis personDiagnosisFinal = (PersonDiagnosis) session.getAttribute("personDiagnosis");

        String finalDiagnosisName = request.getParameter("diagnosis");
        String finalDescription = request.getParameter("description");

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName(finalDiagnosisName);
        diagnosis.setDescription(finalDescription);

        DiagnosisService diagnosisService = ServiceFactory.getDiagnosisService();
        diagnosisService.insertDiagnosis(diagnosis);

        personDiagnosisFinal.setDiagnosis(diagnosis);
        personDiagnosisFinal.setDate(new Timestamp(System.currentTimeMillis()));
        personDiagnosisFinal.setDischargeDate(new Timestamp(System.currentTimeMillis()));

        Prescription prescription = new Prescription();
        prescription.setDrugs(personDiagnosisFinal.getPrescription().getDrugs());
        prescription.setProcedure(personDiagnosisFinal.getPrescription().getProcedure());
        prescription.setOperation(personDiagnosisFinal.getPrescription().getOperation());

        PrescriptionService prescriptionService = ServiceFactory.getPrescriptionService();
        prescriptionService.insertPrescription(prescription);

        personDiagnosisFinal.setPrescription(prescription);

        PersonDiagnosisService personDiagnosisService = ServiceFactory.getPersonDiagnosisService();
        if (personDiagnosisService.insertPatientDiagnosis(personDiagnosisFinal)) {
            logger.debug("successful add record about discharge");
        } else {
            logger.debug("discharge record was not added");
        }

        session.removeAttribute("personDiagnosis");

        Command redirect = CommandHelper.getInstance().defineCommand("redirect");
        return redirect.execute(request, response);
    }
}