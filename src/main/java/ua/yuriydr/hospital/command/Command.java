package ua.yuriydr.hospital.command;

import ua.yuriydr.hospital.command.impl.*;
import ua.yuriydr.hospital.command.impl.authorization.*;
import ua.yuriydr.hospital.command.impl.util.*;
import ua.yuriydr.hospital.command.impl.staff.*;
import ua.yuriydr.hospital.command.impl.profile.*;
import ua.yuriydr.hospital.command.impl.admin.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents an operation that accepts request and response arguments
 * and returns result.
 *
 * @see EmptyCommand
 * @see UnknownCommand
 * @see LoginCommand
 * @see LogoutCommand
 * @see ChangeLanguageCommand
 * @see RedirectToPagesCommand
 * @see DischargePatientCommand
 * @see MakeStaffAction
 * @see RegistrationPatientCommand
 * @see RemovePersonDiagnosisCommand
 * @see SetDiagnosisCommand
 * @see ChangePasswordCommand
 * @see UpdateProfileCommand
 * @see ChangeStaffDataCommand
 * @see RegistrationPersonCommand
 * @see RemovePersonCommand
 * @see AddChamberCommand
 * @see RemoveChamberCommand
 */

public interface Command {

    /**
     * Returns the page to which you need to go after
     * the specific command.
     *
     * @param request  HttpServletRequest object that contains all the client's request information.
     * @param response HttpServletResponse object to return information to the client.
     * @return page to which you need to go after the specific command from user.
     */
    String execute(HttpServletRequest request, HttpServletResponse response);

}
