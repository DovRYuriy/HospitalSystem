package ua.yuriydr.hospital.command.impl.admin;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.command.Command;
import ua.yuriydr.hospital.command.CommandHelper;
import ua.yuriydr.hospital.model.Chamber;
import ua.yuriydr.hospital.model.Person;
import ua.yuriydr.hospital.service.ChamberService;
import ua.yuriydr.hospital.service.factory.ServiceFactory;
import ua.yuriydr.hospital.utils.PagesManager;
import ua.yuriydr.hospital.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

public class RemoveChamberCommand implements Command {

    private static final Logger logger = Logger.getLogger(RemoveChamberCommand.class);

    private static ChamberService chamberService = ServiceFactory.getChamberService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        logger.debug("Try to remove chamber");

        if (!UserUtils.getInstance().hasAccess(request, PagesManager.getProperty("path.page.manageHospital"))) {
            return PagesManager.getProperty("path.page.error.accessDenied");
        }

        HttpSession session = request.getSession();

        List<Chamber> chamberList = (List<Chamber>) session.getAttribute("chambersInHospital");
        Long id = Long.valueOf(request.getParameter("id"));

        Chamber chamber = null;
        Iterator iterator = chamberList.listIterator();
        while (iterator.hasNext()) {
            chamber = (Chamber) iterator.next();
            if (chamber.getIdChamber().equals(id)) {
                logger.debug("Found in list");
                break;
            }
        }

        if (chamber.getPatients().size() == 0) {
            chamberList.remove(chamber);
            if (chamberService.deleteChamber(chamber)) {
                logger.debug("successful deleted");
            } else {
                logger.debug("unsuccessful deleted");
            }
            session.removeAttribute("removeChamberFailed");
            session.setAttribute("chambersInHospital", chamberList);
        } else {
            session.setAttribute("removeChamberFailed", "removeChamberFailed");
        }
        return (String) session.getAttribute("currentPage");
    }
}
