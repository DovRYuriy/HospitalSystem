package ua.yuriydr.hospital.command.impl.util;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.model.*;
import ua.yuriydr.hospital.service.*;
import ua.yuriydr.hospital.service.factory.ServiceFactory;
import ua.yuriydr.hospital.service.factory.impl.ChamberServiceImpl;
import ua.yuriydr.hospital.utils.PagesManager;
import ua.yuriydr.hospital.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Consumer;

public class RedirectToPagesCommand implements Command {

    private static final Logger logger = Logger.getLogger(RedirectToPagesCommand.class);

    private Map<String, Consumer<HttpServletRequest>> controllers = new HashMap<>();

    public RedirectToPagesCommand() {
        controllers.put(PagesManager.getProperty("path.page.patientMainPage"), this::fillPatientMainPage);
        controllers.put(PagesManager.getProperty("path.page.doctorMainPage"), this::fillDoctorMainPage);
        controllers.put(PagesManager.getProperty("path.page.patientStaff"), this::fillPatientStaffPage);
        controllers.put(PagesManager.getProperty("path.page.adminMainPage"), this::fillAdminMainPage);
        controllers.put(PagesManager.getProperty("path.page.editStaffPage"), this::fillStaffForEdit);
        controllers.put(PagesManager.getProperty("path.page.registration"), this::fillRegistrationForm);
        controllers.put(PagesManager.getProperty("path.page.registrationPatient"), this::fillPatientRegistrationForm);
        controllers.put(PagesManager.getProperty("path.page.dischargePatientPage"), this::fillDischargePatientPage);
        controllers.put(PagesManager.getProperty("path.page.editPatientPage"), this::fillEditPatientPage);
        controllers.put(PagesManager.getProperty("path.page.nurseMainPage"), this::fillNurseMainPage);
        controllers.put(PagesManager.getProperty("path.page.manageHospital"), this::fillHospitalPage);
        controllers.put(PagesManager.getProperty("path.page.addChamberPage"), this::fillAddChamberPage);
        controllers.put(PagesManager.getProperty("path.page.allPatients"), this::fillAllPatientsPage);
        controllers.put(PagesManager.getProperty("path.page.setDiagnosisPage"), this::fillSetDiagnosisPage);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String req, page;
        HttpSession session = request.getSession();


        String param = request.getParameter("redirectTo");
        if (param == null) {
            Person person = (Person) session.getAttribute("user");
            String role = person.getRole().getName().toUpperCase();

            req = "path.page." + UserUtils.getInstance().getKeyByRole(role);
        } else {
            req = "path.page." + param;
        }

        page = PagesManager.getProperty(req);
        if (!UserUtils.getInstance().hasAccess(request, page)) {
            return PagesManager.getProperty("path.page.error.accessDenied");
        }

        Consumer<HttpServletRequest> c = controllers.get(page);
        if (c != null) {
            c.accept(request);
        }

        logger.debug("Redirect to " + page + " page");

        session.setAttribute("currentPage", page);
        return page;
    }

    private void fillPatientRegistrationForm(HttpServletRequest request) {
        logger.debug("Find free chambers for patient registration form");
        HttpSession session = request.getSession();

        ChamberService chamberService = ChamberServiceImpl.getInstance();
        List<Chamber> chambers = chamberService.findAllFreeByType("ward");

        logger.debug(chambers);
        session.setAttribute("chambers", chambers);
    }

    private void fillRegistrationForm(HttpServletRequest request) {
        logger.debug("Find roles for staff registration form");
        HttpSession session = request.getSession();

        RoleService roleService = ServiceFactory.getRoleService();
        List<Role> roleList = roleService.findAll();

        Iterator iterator = roleList.listIterator();
        while (iterator.hasNext()) {
            Role r = (Role) iterator.next();
            if (r.getName().equals("patient")) {
                iterator.remove();
            }
        }
        session.setAttribute("roles", roleList);

        ChamberService chamberService = ChamberServiceImpl.getInstance();
        List<Chamber> chambers = chamberService.findAllFreeByType("cabinet");

        logger.debug(chambers);
        session.setAttribute("chambers", chambers);

        logger.debug(roleList);
        session.removeAttribute("removeChamberFailed");
        session.removeAttribute("removeFailed");
    }

    private void fillStaffForEdit(HttpServletRequest request) {
        logger.debug("Fill staff for edit");
        HttpSession session = request.getSession();

        Long id = Long.valueOf(request.getParameter("id"));

        PersonService service = ServiceFactory.getPersonService();
        Person person = service.findPersonById(id);

        if (person != null) {
            session.setAttribute("person", person);
        } else {
            logger.error("Invalid id (person)");
            session.setAttribute("invalidPerson", "invalid");
        }
    }

    private void fillPatientStaffPage(HttpServletRequest request) {
        logger.debug("Fill patient's staff");
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("user");

        Set<Person> doctors = new HashSet<>();
        if (person.getPersonDiagnosisList().size() != 0) {
            doctors.add(person.getPersonDiagnosisList().get(0).getDoctor());
        }

        PersonService personService = ServiceFactory.getPersonService();
        List<Person> nurses = personService.findAllByRole("nurse");
        for (Person nurse : nurses) {
            doctors.add(nurse);
        }

        logger.debug(doctors);
        session.setAttribute("patientStaffList", doctors);
    }

    private void fillAdminMainPage(HttpServletRequest request) {
        logger.debug("Fill admin main page");

        HttpSession session = request.getSession();
        PersonService personService = ServiceFactory.getPersonService();

        List<Person> doctors = personService.findAllByRole("doctor");
        List<Person> nurses = personService.findAllByRole("nurse");
        doctors.addAll(nurses);

        session.removeAttribute("removeChamberFailed");
        session.removeAttribute("person");
        session.removeAttribute("invalidPerson");
        session.removeAttribute("incorrectData");

        logger.debug(doctors);
        session.setAttribute("staffInHospital", doctors);
    }

    private void fillPatientMainPage(HttpServletRequest request) {
        logger.debug("Fill patient's main page");

        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("user");

        PersonDiagnosisService personDiagnosisService = ServiceFactory.getPersonDiagnosisService();
        person.setPersonDiagnosisList(personDiagnosisService.findAllByPatientId(person.getIdPerson()));

        person.getPersonDiagnosisList().sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        logger.debug(person.getPersonDiagnosisList());
        session.setAttribute("patientInfo", person.getPersonDiagnosisList());
    }

    private void fillDoctorMainPage(HttpServletRequest request) {
        logger.debug("Fill doctor's main page");

        HttpSession session = request.getSession();
        Person doc = (Person) session.getAttribute("user");

        PersonDiagnosisService personDiagnosisService = ServiceFactory.getPersonDiagnosisService();
        List<PersonDiagnosis> personDiagnoses = personDiagnosisService.findAllByStaffId(doc.getIdPerson());

        Set<Person> patients = new HashSet<>();
        for (PersonDiagnosis personDiagnosis : personDiagnoses) {
            if (personDiagnosis.getPatient().getIdChamber() != 0) {
                patients.add(personDiagnosis.getPatient());
            }
        }

        session.removeAttribute("person");
        session.removeAttribute("diagnosis");
        session.removeAttribute("prescription");
        session.removeAttribute("incorrectData");
        session.removeAttribute("personChamber");

        logger.debug(patients);
        session.setAttribute("myPatients", patients);
    }

    private void fillDischargePatientPage(HttpServletRequest request) {
        logger.debug("fill discharge patient page");
        HttpSession session = request.getSession();

        Long idPatient = Long.valueOf(request.getParameter("id"));

        PersonDiagnosisService personDiagnosisService = ServiceFactory.getPersonDiagnosisService();
        List<PersonDiagnosis> personDiagnosisList = personDiagnosisService.findAllByPatientId(idPatient);
        personDiagnosisList.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        PersonDiagnosis last = personDiagnosisList.get(0);

        logger.debug(last);
        session.setAttribute("personDiagnosis", last);
    }

    private void fillEditPatientPage(HttpServletRequest request) {
        logger.debug("Fill edit patient page");

        HttpSession session = request.getSession();
        Person doc = (Person) session.getAttribute("user");

        Long idPatient = Long.valueOf(request.getParameter("id"));
        PersonService personService = ServiceFactory.getPersonService();
        session.setAttribute("person", personService.findPersonById(idPatient));

        PersonDiagnosisService personDiagnosisService = ServiceFactory.getPersonDiagnosisService();
        List<PersonDiagnosis> personDiagnosisList = personDiagnosisService.findAllByPatientId(idPatient);
        Iterator iterator = personDiagnosisList.listIterator();
        while (iterator.hasNext()) {
            PersonDiagnosis personDiagnosis = (PersonDiagnosis) iterator.next();
            if (!personDiagnosis.getDoctor().getIdPerson().equals(doc.getIdPerson())) {
                iterator.remove();
            }
        }

        Collections.reverse(personDiagnosisList);
        session.setAttribute("patientDiagnosis", personDiagnosisList);

        ChamberService chamberService = ChamberServiceImpl.getInstance();
        session.setAttribute("chamber", chamberService.findChamberById(personDiagnosisList.get(0).getPatient().getIdChamber()));

    }

    private void fillNurseMainPage(HttpServletRequest request) {
        logger.debug("Fill nurse's main page");

        HttpSession session = request.getSession();

        PersonDiagnosisService personDiagnosisService = ServiceFactory.getPersonDiagnosisService();
        List<PersonDiagnosis> personDiagnosisList = personDiagnosisService.findAllForNurse();
        Iterator iterator = personDiagnosisList.listIterator();
        while (iterator.hasNext()) {
            PersonDiagnosis personDiagnosis = (PersonDiagnosis) iterator.next();
            if (personDiagnosis.getPatient().getIdChamber() == 0) {
                iterator.remove();
            }
        }

        personDiagnosisList.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        logger.debug(personDiagnosisList);
        session.setAttribute("patientDiagnosis", personDiagnosisList);
    }

    private void fillAddChamberPage(HttpServletRequest request) {
        HttpSession session = request.getSession();

        ChamberTypeService chamberTypeService = ServiceFactory.getChamberTypeService();
        List<ChamberType> types = chamberTypeService.findAll();

        session.setAttribute("chambersType", types);
    }

    private void fillHospitalPage(HttpServletRequest request) {
        logger.debug("Fill page with chambers");

        HttpSession session = request.getSession();

        ChamberService chamberService = ServiceFactory.getChamberService();
        List<Chamber> chambers = chamberService.findAll();

        session.removeAttribute("removeFailed");
        logger.debug(chambers);
        session.setAttribute("chambersInHospital", chambers);
    }

    private void fillAllPatientsPage(HttpServletRequest request) {
        HttpSession session = request.getSession();

        PersonService personService = ServiceFactory.getPersonService();
        List<Person> personList = personService.findAllByRole("patient");
        Iterator iterator = personList.listIterator();
        while (iterator.hasNext()) {
            Person person = (Person) iterator.next();
            if (person.getIdChamber() != 0) {
                iterator.remove();
            }
        }
        session.setAttribute("patients", personList);
    }

    private void fillSetDiagnosisPage(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String s = request.getParameter("id");
        if (s != null) {
            Long id = Long.valueOf(s);
            PersonService personService = ServiceFactory.getPersonService();
            Person person = personService.findPersonById(id);
            if (person != null) {
                session.setAttribute("newPerson", person);
            }
            ChamberService chamberService = ServiceFactory.getChamberService();
            List<Chamber> chambers = chamberService.findAllFreeByType("ward");
            session.setAttribute("personChamber", "personChamber");
            session.setAttribute("chambers", chambers);
        }
    }
}
