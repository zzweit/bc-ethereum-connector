package com.bunker.app.service.impl;

import com.bunker.app.service.BunkerService;
import com.bunker.app.domain.Bunker;
import com.bunker.app.repository.BunkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Bunker.
 */
@Service
public class BunkerServiceImpl implements BunkerService {

    private final Logger log = LoggerFactory.getLogger(BunkerServiceImpl.class);

    private BunkerRepository bunkerRepository;

    public BunkerServiceImpl(BunkerRepository bunkerRepository) {
        this.bunkerRepository = bunkerRepository;
    }

    /**
     * Save a bunker.
     *
     * @param bunker the entity to save
     * @return the persisted entity
     */
    @Override
    public Bunker save(Bunker bunker) {
        log.debug("Request to save Bunker : {}", bunker);
        return bunkerRepository.save(bunker);
    }

    /**
     * Get all the bunkers.
     *
     * @return the list of entities
     */
    @Override
    public List<Bunker> findAll() {
        log.debug("Request to get all Bunkers");
        return bunkerRepository.findAll();
    }


    /**
     * Get one bunker by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Bunker> findOne(String id) {
        log.debug("Request to get Bunker : {}", id);
        return bunkerRepository.findById(id);
    }

    /**
     * Delete the bunker by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Bunker : {}", id);
        bunkerRepository.deleteById(id);
    }
}
