package ua.yuriydr.hospital.dao;

import ua.yuriydr.hospital.dao.mysql.impl.MySqlChamberTypeDao;
import ua.yuriydr.hospital.model.ChamberType;

import java.util.List;

/**
 * Represents methods to manage chamber types in the hospital.
 *
 * @see MySqlChamberTypeDao
 */
public interface ChamberTypeDao {

    /**
     * Returns a list of chamber types.
     *
     * @return a list of chamber types.
     */
    List<ChamberType> findAll();

    /**
     * Returns chamber type by id.
     *
     * @param id chamber type.
     * @return chamber.
     */
    ChamberType findChamberTypeById(Long id);

    /**
     * Returns chamber type by name.
     *
     * @param name chamber type name.
     * @return chamber type.
     */
    ChamberType findChamberTypeByName(String name);

    /**
     * Delete chamber type.
     *
     * @param chamberType that need to delete.
     * @return true if chamber type successfully deleted.
     */
    boolean deleteChamberType(ChamberType chamberType);

    /**
     * Add new chamber type.
     *
     * @param chamberType that need to add.
     * @return true if chamber type successfully added.
     */
    boolean insertChamberType(ChamberType chamberType);

}
