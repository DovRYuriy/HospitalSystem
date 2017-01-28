package ua.yuriydr.hospital.service.factory.impl;


import ua.yuriydr.hospital.dao.ChamberDao;
import ua.yuriydr.hospital.dao.PersonDao;
import ua.yuriydr.hospital.dao.mysql.MySqlDaoFactory;
import ua.yuriydr.hospital.entity.Chamber;
import ua.yuriydr.hospital.service.ChamberService;

import java.util.ArrayList;
import java.util.List;

public class ChamberServiceImpl implements ChamberService {

    private static volatile ChamberServiceImpl chamberService;

    private static final ChamberDao chamberDao = MySqlDaoFactory.getChamberDao();
    private static final PersonDao personDao = MySqlDaoFactory.getPersonDao();

    private ChamberServiceImpl() {

    }

    public static ChamberServiceImpl getInstance() {
        ChamberServiceImpl localInstance = chamberService;
        if (localInstance == null) {
            synchronized (ChamberServiceImpl.class) {
                localInstance = chamberService;
                if (localInstance == null) {
                    chamberService = localInstance = new ChamberServiceImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public List<Chamber> findAllFree() {
        List<Chamber> freeChambers = new ArrayList<>();
        List<Chamber> allChambers = chamberDao.findAll();
        for (Chamber chamber : allChambers) {
            chamber.setPatients(personDao.findAllInSpecificChamber(chamber.getIdChamber()));
            if (chamber.getMaxCount() != chamber.getPatients().size()) {
                freeChambers.add(chamber);
            }
        }
        return freeChambers;
    }

    @Override
    public List<Chamber> findAllFreeByType(String type) {
        List<Chamber> freeChambers = new ArrayList<>();
        List<Chamber> allChambers = chamberDao.findAllByType(type);
        for (Chamber chamber : allChambers) {
            chamber.setPatients(personDao.findAllInSpecificChamber(chamber.getIdChamber()));
            if (chamber.getMaxCount() != chamber.getPatients().size()) {
                freeChambers.add(chamber);
            }
        }
        return freeChambers;
    }

    @Override
    public Chamber findChamberById(Long idChamber) {
        Chamber chamber = chamberDao.findChamberById(idChamber);
        if (chamber != null) {
            chamber.setPatients(personDao.findAllInSpecificChamber(idChamber));
        }
        return chamber;
    }

    @Override
    public List<Chamber> findAll() {
        List<Chamber> allChambers = chamberDao.findAll();
        for (Chamber chamber : allChambers) {
            chamber.setPatients(personDao.findAllInSpecificChamber(chamber.getIdChamber()));
        }
        return allChambers;
    }

    @Override
    public boolean insertChamber(Chamber chamber) {
        return chamberDao.insertChamber(chamber);
    }

    @Override
    public boolean deleteChamber(Chamber chamber) {
        return chamberDao.deleteChamber(chamber);
    }
}
