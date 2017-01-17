package ua.yuriydr.hospital.command.impl.admin;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.command.CommandHelper;
import ua.yuriydr.hospital.model.Chamber;
import ua.yuriydr.hospital.service.ChamberService;
import ua.yuriydr.hospital.service.factory.ServiceFactory;
import ua.yuriydr.hospital.utils.PagesManager;
import ua.yuriydr.hospital.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AddChamberCommand implements Command {

    private static final Logger logger = Logger.getLogger(AddChamberCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        if (!UserUtils.getInstance().hasAccess(request, PagesManager.getProperty("path.page.addChamberPage"))) {
            return PagesManager.getProperty("path.page.error.accessDenied");
        }

        logger.debug("Try to add new chamber into hospital");

        HttpSession session = request.getSession();

        Chamber newChamber = new Chamber();

        String number = request.getParameter("numb");
        String capacity = request.getParameter("count");
        boolean invalid = false;

        if (number.isEmpty() || capacity.isEmpty()) {
            invalid = true;
        }
        if (invalid) {
            session.setAttribute("incorrectNumber", "incorrectNumber");
            session.setAttribute("incorrectCapacity", "incorrectCapacity");
            return (String) session.getAttribute("currentPage");
        }

        newChamber.setNumber(Long.valueOf(number));
        newChamber.setMaxCount(Long.valueOf(capacity));

        List<Chamber> chambers = (List<Chamber>) session.getAttribute("chambersInHospital");
        for (Chamber chamber : chambers) {
            if (chamber.getNumber().equals(newChamber.getNumber())) {
                invalid = true;
            }
        }

        String page;
        if (invalid) {
            logger.debug("Add chamber, number is busy");
            session.setAttribute("incorrectNumber", "incorrectNumber");
            session.removeAttribute("incorrectCapacity");
            page = (String) session.getAttribute("currentPage");
        } else {
            logger.debug("All input data are valid");

            ChamberService chamberService = ServiceFactory.getChamberService();
            if (chamberService.insertChamber(newChamber)) {
                logger.debug("successfully added chamber");

                chambers.add(0, newChamber);
                session.setAttribute("chambersInHospital", chambers);
                session.removeAttribute("incorrectCapacity");
                session.removeAttribute("incorrectNumber");
                session.removeAttribute("removeChamberFailed");
            } else {
                logger.debug("successfully added chamber");
            }
            page = PagesManager.getProperty("path.page.manageHospital");
            session.setAttribute("currentPage", page);
        }
        return page;
    }

}
