package ua.yuriydr.hospital.utils;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.entity.Person;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * This class used for check users permissions to one or another page.
 * And helps to know starting page for every role.
 */
public class UserUtils {

    /**
     * Logger object.
     */
    private static final Logger logger = Logger.getLogger(UserUtils.class);

    /**
     * Singleton instance.
     */
    private static volatile UserUtils userAccessManager;

    /**
     * Variables that represents roles
     */
    private static final String ALL_ROLES = "ALL";
    private static final String PATIENT_ROLE = "PATIENT";
    private static final String DOCTOR_ROLE = "DOCTOR";
    private static final String NURSE_ROLE = "NURSE";
    private static final String ADMIN_ROLE = "ADMIN";

    /**
     * Access map (page - role)
     */
    private Map<String, String> accessMap = new HashMap<>();

    /**
     * Constructs access map
     */
    private UserUtils() {
        accessMap.put(PagesManager.getProperty("path.page.error"), ALL_ROLES);
        accessMap.put(PagesManager.getProperty("path.page.error.accessDenied"), ALL_ROLES);
        accessMap.put(PagesManager.getProperty("path.page.index"), ALL_ROLES);
        accessMap.put(PagesManager.getProperty("path.page.login"), ALL_ROLES);
        accessMap.put(PagesManager.getProperty("path.page.noCommand"), ALL_ROLES);
        accessMap.put(PagesManager.getProperty("path.page.patientMainPage"), PATIENT_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.patientProfile"), PATIENT_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.patientStaff"), PATIENT_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.doctorMainPage"), DOCTOR_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.doctorProfile"), DOCTOR_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.editPatientPage"), DOCTOR_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.registrationPatient"), DOCTOR_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.dischargePatientPage"), DOCTOR_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.setDiagnosisPage"), DOCTOR_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.allPatients"), DOCTOR_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.nurseMainPage"), NURSE_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.nurseProfile"), NURSE_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.adminMainPage"), ADMIN_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.adminProfile"), ADMIN_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.registration"), ADMIN_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.editStaffPage"), ADMIN_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.manageHospital"), ADMIN_ROLE);
        accessMap.put(PagesManager.getProperty("path.page.addChamberPage"), ADMIN_ROLE);
    }

    /**
     * Returns instance of this class.
     *
     * @return UserUtils instance
     */
    public static UserUtils getInstance() {
        UserUtils localInstance = userAccessManager;
        if (localInstance == null) {
            synchronized (UserUtils.class) {
                localInstance = userAccessManager;
                if (localInstance == null) {
                    userAccessManager = localInstance = new UserUtils();
                }
            }
        }
        logger.debug(localInstance);
        return localInstance;
    }

    /**
     * Checks that user has access to page
     *
     * @param req  HttpServletRequest object that contains all the client's request information.
     * @param page user has access to this page.
     * @return true if user has access.
     */
    public boolean hasAccess(HttpServletRequest req, String page) {
        Person person = (Person) req.getSession().getAttribute("user");
        String access = accessMap.get(page);
        if (person == null) {
            return false;
        }
        if (access.equals("ALL")) {
            logger.debug(person + " has access to " + page);
            return true;
        }
        if (access.equals(person.getRole().getName().toUpperCase())) {
            logger.debug(person + " has access to " + page);
            return true;
        }
        logger.debug(person + " has not access to " + page);
        return false;
    }

    /**
     * Returns start page for role.
     *
     * @param role role of person.
     * @return start page for person.
     */
    public String getPageByRole(String role) {
        String page = PagesManager.getProperty("path.page.index");
        switch (role) {
            case DOCTOR_ROLE:
                page = PagesManager.getProperty("path.page.doctorMainPage");
                break;
            case NURSE_ROLE:
                page = PagesManager.getProperty("path.page.nurseMainPage");
                break;
            case ADMIN_ROLE:
                page = PagesManager.getProperty("path.page.adminMainPage");
                break;
            case PATIENT_ROLE:
                page = PagesManager.getProperty("path.page.patientMainPage");
                break;
        }
        return page;
    }

    public String getKeyByRole(String role) {
        String key = "index";
        switch (role) {
            case DOCTOR_ROLE:
                key = "doctorMainPage";
                break;
            case NURSE_ROLE:
                key = "nurseMainPage";
                break;
            case ADMIN_ROLE:
                key = "adminMainPage";
                break;
            case PATIENT_ROLE:
                key = "patientMainPage";
                break;
        }
        return key;
    }
}
