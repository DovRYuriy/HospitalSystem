package ua.yuriydr.hospital.service;

import ua.yuriydr.hospital.model.Chamber;

import java.util.List;

/**
 * Service layer for working with {@link ua.yuriydr.hospital.dao.ChamberDao}
 */
public interface ChamberService {

    /**
     * Returns a list of free chambers in hospital.
     *
     * @return a list of free chambers in hospital.
     */
    List<Chamber> findAllFree();

    /**
     * Returns chamber by id.
     *
     * @return chamber by id
     */
    Chamber findChamberById(Long idChamber);

    /**
     * Returns a list of chambers in hospital.
     *
     * @return a list of chambers in hospital.
     */
    List<Chamber> findAll();

    /**
     * Insert chamber.
     *
     * @param chamber that need to insert.
     * @return true if chamber successfully added.
     */
    boolean insertChamber(Chamber chamber);

    /**
     * Delete chamber.
     *
     * @param chamber that need to delete.
     * @return true if chamber successfully deleted.
     */
    boolean deleteChamber(Chamber chamber);
}
