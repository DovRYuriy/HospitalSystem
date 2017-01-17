package ua.yuriydr.hospital.dao;

import ua.yuriydr.hospital.dao.mysql.impl.MySqlChamberDao;
import ua.yuriydr.hospital.model.Chamber;

import java.util.List;

/**
 * Represents methods to manage chambers in the hospital.
 *
 * @see MySqlChamberDao
 */

public interface ChamberDao {

    /**
     * Returns a list of chambers in hospital.
     *
     * @return a list of chambers in hospital.
     */
    List<Chamber> findAll();

    /**
     * Returns a chamber by id.
     *
     * @param id chamber.
     * @return chamber.
     */
    Chamber findChamberById(Long id);

    /**
     * Update chamber.
     *
     * @param chamber that need to update.
     * @return true if chamber successfully updated.
     */
    boolean updateChamber(Chamber chamber);

    /**
     * Delete chamber.
     *
     * @param chamber that need to update.
     * @return true if chamber successfully updated.
     */
    boolean deleteChamber(Chamber chamber);

    /**
     * Add new chamber.
     *
     * @param chamber that need to add
     * @return true if chamber added successfully.
     */
    boolean insertChamber(Chamber chamber);

}
