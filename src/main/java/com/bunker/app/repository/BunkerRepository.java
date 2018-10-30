package com.bunker.app.repository;

import com.bunker.app.domain.Bunker;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Bunker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BunkerRepository extends MongoRepository<Bunker, String> {

}
