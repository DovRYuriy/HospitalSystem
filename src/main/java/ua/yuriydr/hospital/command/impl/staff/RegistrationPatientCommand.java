package ua.yuriydr.hospital.command.impl.staff;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.command.CommandHelper;
import ua.yuriydr.hospital.dto.IncorrectInputData;
import ua.yuriydr.hospital.model.*;
import ua.yuriydr.hospital.service.*;
import ua.yuriydr.hospital.service.factory.ServiceFactory;
import ua.yuriydr.hospital.utils.PagesManager;
import ua.yuriydr.hospital.utils.UserFormChecker;
import ua.yuriydr.hospital.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegistrationPatientCommand implements Command {

    private static final Logger logger = Logger.getLogger(RegistrationPatientCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        if (!UserUtils.getInstance().hasAccess(request, PagesManager.getProperty("path.page.registrationPatient"))) {
            return PagesManager.getProperty("path.page.error.accessDenied");
        }

        logger.debug("Try to register patient");

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
        } catch (ParseException e) {
            logger.error("Error parse date: " + e);
            invalid = true;
            builder.setIncorrectDateAttribute();
        }
        person.setBirthday(date);
        person.setEmail(request.getParameter("email"));
        person.setPassword(initPassword);

        RoleService roleService = ServiceFactory.getRoleService();

        Role role = roleService.findRoleByName("patient");
        person.setRole(role);

        String s = request.getParameter("chambers");
        Long idChamber = null;
        if (s != null) {
            idChamber = Long.valueOf(s);
        }

        person.setIdChamber(idChamber);

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName(request.getParameter("diagnosis"));
        diagnosis.setDescription(request.getParameter("description"));

        Prescription prescription = new Prescription();
        prescription.setDrugs(request.getParameter("drugs"));
        prescription.setProcedure(request.getParameter("procedure"));
        prescription.setOperation(request.getParameter("operation"));

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
        if (!UserFormChecker.diagnosisCheck(diagnosis.getName())) {
            builder.setIncorrectDiagnosisAttribute();
            invalid = true;
        }
        String page;
        if (invalid) {
            logger.debug("Invalid input data. Try again.");

            session.setAttribute("incorrectData", builder.build());
            session.setAttribute("person", person);
            session.setAttribute("diagnosis", diagnosis);
            session.setAttribute("prescription", prescription);
            page = (String) session.getAttribute("currentPage");
        } else {

            logger.debug("All input data are valid");

            DiagnosisService diagnosisService = ServiceFactory.getDiagnosisService();
            diagnosisService.insertDiagnosis(diagnosis);

            PrescriptionService prescriptionService = ServiceFactory.getPrescriptionService();
            prescriptionService.insertPrescription(prescription);

            personService.insertPerson(person);

            PersonDiagnosis personDiagnosis = new PersonDiagnosis();
            personDiagnosis.setPatient(person);
            personDiagnosis.setDoctor((Person) session.getAttribute("user"));
            personDiagnosis.setDiagnosis(diagnosis);
            personDiagnosis.setPrescription(prescription);
            personDiagnosis.setDate(new Timestamp(System.currentTimeMillis()));
            personDiagnosis.setDischargeDate(null);

            PersonDiagnosisService personDiagnosisService = ServiceFactory.getPersonDiagnosisService();
            personDiagnosisService.insertPatientDiagnosis(personDiagnosis);

            Command redirect = CommandHelper.getInstance().defineCommand("redirect");
            return redirect.execute(request, response);
        }

        return page;
    }
}
