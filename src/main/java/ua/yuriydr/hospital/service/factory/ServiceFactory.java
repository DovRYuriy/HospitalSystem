package ua.yuriydr.hospital.service.factory;

import ua.yuriydr.hospital.service.*;
import ua.yuriydr.hospital.service.factory.impl.*;

public class ServiceFactory {

    private ServiceFactory() {

    }

    public static ChamberService getChamberService() {
        return ChamberServiceImpl.getInstance();
    }

    public static DiagnosisService getDiagnosisService() {
        return DiagnosisServiceImpl.getInstance();
    }

    public static PersonService getPersonService() {
        return PersonServiceImpl.getInstance();
    }

    public static PersonDiagnosisService getPersonDiagnosisService() {
        return PersonDiagnosisServiceImpl.getInstance();
    }

    public static PrescriptionService getPrescriptionService() {
        return PrescriptionServiceImpl.getInstance();
    }

    public static RoleService getRoleService() {
        return RoleServiceImpl.getInstance();
    }

    public static ChamberTypeService getChamberTypeService() {
        return ChamberTypeServiceImpl.getInstance();
    }


}
