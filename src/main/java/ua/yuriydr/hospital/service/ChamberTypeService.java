package ua.yuriydr.hospital.service;

import ua.yuriydr.hospital.model.ChamberType;

import java.util.List;

/**
 * Service layer for working with {@link ua.yuriydr.hospital.dao.ChamberTypeDao}
 */
public interface ChamberTypeService {

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
