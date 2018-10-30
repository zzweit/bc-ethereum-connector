package com.bunker.app.service;

import com.bunker.app.domain.Bunker;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Bunker.
 */
public interface BunkerService {

    /**
     * Save a bunker.
     *
     * @param bunker the entity to save
     * @return the persisted entity
     */
    Bunker save(Bunker bunker);

    /**
     * Get all the bunkers.
     *
     * @return the list of entities
     */
    List<Bunker> findAll();


    /**
     * Get the "id" bunker.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Bunker> findOne(String id);

    /**
     * Delete the "id" bunker.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
