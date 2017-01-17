package ua.yuriydr.hospital.service.factory.impl;

import ua.yuriydr.hospital.dao.RoleDao;
import ua.yuriydr.hospital.dao.mysql.MySqlDaoFactory;
import ua.yuriydr.hospital.model.Role;
import ua.yuriydr.hospital.service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    private static volatile RoleServiceImpl roleService;

    private RoleDao roleDao = MySqlDaoFactory.getRoleDao();

    private RoleServiceImpl() {

    }

    public static RoleServiceImpl getInstance() {
        RoleServiceImpl localInstance = roleService;
        if (localInstance == null) {
            synchronized (RoleServiceImpl.class) {
                localInstance = roleService;
                if (localInstance == null) {
                    roleService = localInstance = new RoleServiceImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findRoleById(Long id) {
        return roleDao.findRoleById(id);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleDao.findRoleByName(name);
    }

    @Override
    public boolean deleteRole(Role role) {
        return roleDao.deleteRole(role);
    }

    @Override
    public boolean insertRole(Role role) {
        return roleDao.insertRole(role);
    }
}
