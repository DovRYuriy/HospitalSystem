package ua.yuriydr.hospital.dao;

import ua.yuriydr.hospital.dao.mysql.impl.MySqlRoleDao;
import ua.yuriydr.hospital.model.Role;

import java.util.List;

/**
 * Represents methods to manage diagnoses in the hospital.
 *
 * @see MySqlRoleDao
 */
public interface RoleDao {

    /**
     * Returns a list of roles.
     *
     * @return a list of roles.
     */
    List<Role> findAll();

    /**
     * Returns role by id.
     *
     * @param id role.
     * @return role.
     */
    Role findRoleById(Long id);

    /**
     * Returns diagnosis by name.
     *
     * @param name role name.
     * @return diagnosis.
     */
    Role findRoleByName(String name);

    /**
     * Delete diagnosis.
     *
     * @param role that need to delete.
     * @return true if role successfully deleted.
     */
    boolean deleteRole(Role role);

    /**
     * Add new role.
     *
     * @param role that need to add.
     * @return true if role successfully added.
     */
    boolean insertRole(Role role);

}
