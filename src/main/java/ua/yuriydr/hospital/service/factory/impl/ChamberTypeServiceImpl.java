package ua.yuriydr.hospital.service.factory.impl;


import ua.yuriydr.hospital.dao.ChamberTypeDao;
import ua.yuriydr.hospital.dao.mysql.MySqlDaoFactory;
import ua.yuriydr.hospital.model.ChamberType;
import ua.yuriydr.hospital.service.ChamberTypeService;

import java.util.List;

public class ChamberTypeServiceImpl implements ChamberTypeService{

    private static volatile ChamberTypeServiceImpl chamberTypeService;

    private ChamberTypeDao chamberTypeDao = MySqlDaoFactory.getChamberTypeDao();

    private ChamberTypeServiceImpl(){

    }

    public static ChamberTypeServiceImpl getInstance(){
        ChamberTypeServiceImpl localInstance = chamberTypeService;
        if(localInstance == null){
            synchronized (ChamberTypeServiceImpl.class){
                localInstance = chamberTypeService;
                if(localInstance == null){
                    chamberTypeService = localInstance = new ChamberTypeServiceImpl();
                }
            }
        }
        return localInstance;
    }


    @Override
    public List<ChamberType> findAll() {
        return chamberTypeDao.findAll();
    }

    @Override
    public ChamberType findChamberTypeById(Long id) {
        return chamberTypeDao.findChamberTypeById(id);
    }

    @Override
    public ChamberType findChamberTypeByName(String name) {
        return chamberTypeDao.findChamberTypeByName(name);
    }

    @Override
    public boolean deleteChamberType(ChamberType chamberType) {
        return chamberTypeDao.deleteChamberType(chamberType);
    }

    @Override
    public boolean insertChamberType(ChamberType chamberType) {
        return chamberTypeDao.insertChamberType(chamberType);
    }
}
