package com.bunker.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bunker.app.blockchain.BlockchainConnection;
import com.bunker.app.blockchain.BunkerOrderStorage;
import com.bunker.app.domain.Bunker;
import com.bunker.app.service.BunkerService;
import com.bunker.app.web.rest.errors.BadRequestAlertException;
import com.bunker.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;

import javax.validation.Valid;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Bunker.
 */
@RestController
@RequestMapping("/api")
public class BunkerResource {

    private final Logger log = LoggerFactory.getLogger(BunkerResource.class);
    
    private BunkerService bunkerService;

    private static final String ENTITY_NAME = "bunkerBunker";
    private BunkerOrderStorage contract;
    private BlockchainConnection connection;

    public BunkerResource(BunkerService bunkerService) {
        this.bunkerService = bunkerService;
        this.connection = new BlockchainConnection();
        this.contract = connection.connectToBlockchain();
    }

    /**
     * POST  /bunkers : Create a new bunker.
     *
     * @param bunker the bunker to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bunker, or with status 400 (Bad Request) if the bunker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bunkers")
    @Timed
    public ResponseEntity<Bunker> createBunker(@Valid @RequestBody Bunker bunker) throws URISyntaxException {
        log.debug("REST request to save Bunker : {}", bunker);
        if (bunker.getId() != null) {
            throw new BadRequestAlertException("A new bunker cannot already have an ID", ENTITY_NAME, "idexists");
        }
      
        try {
//        	**CREATE BUNKER DATA IN SMART CONTRACT**
        	TransactionReceipt transactionReceipt = contract.createBunkerOrder(bunker.getOrder_id(), bunker.getSupplying_org(), bunker.getReceiving_org(), bunker.getTest_results()).send();
        	log.info("Blockchain Transaction Create Receipt: " + transactionReceipt);
        } catch(Exception e) {
        	log.error("BLOCKCHAIN - CREATE ERROR:" + e.getMessage());
        }

        Bunker result = bunkerService.save(bunker);
        
        return ResponseEntity.created(new URI("/api/bunkers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bunkers : Updates an existing bunker.
     *
     * @param bunker the bunker to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bunker,
     * or with status 400 (Bad Request) if the bunker is not valid,
     * or with status 500 (Internal Server Error) if the bunker couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bunkers")
    @Timed
    public ResponseEntity<Bunker> updateBunker(@Valid @RequestBody Bunker bunker) throws URISyntaxException {
        log.debug("REST request to update Bunker : {}", bunker);
        if (bunker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bunker result = bunkerService.save(bunker);
        try {
//      	**UPDATE BUNKER DATA IN SMART CONTRACT**
			TransactionReceipt transactionReceipt = contract.updateBunkerOrder(bunker.getOrder_id(), bunker.getSupplying_org(), bunker.getReceiving_org(), bunker.getTest_results()).send();
        	log.info("Blockchain Transaction Update Receipt: " + transactionReceipt);
        } catch (Exception e) {
        	log.error("BLOCKCHAIN UPDATE ERROR:" + e.getMessage());
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bunker.getId()))
            .body(result);
    }

    /**
     * GET  /bunkers : get all the bunkers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bunkers in body
     */
    @GetMapping("/bunkers")
    @Timed
    public List<Bunker> getAllBunkers() {
        log.debug("REST request to get all Bunkers");
        return bunkerService.findAll();
    }

    /**
     * GET  /bunkers/:id : get the "id" bunker.
     *
     * @param id the id of the bunker to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bunker, or with status 404 (Not Found)
     */
    @GetMapping("/bunkers/{id}")
    @Timed
    public ResponseEntity<Bunker> getBunker(@PathVariable String id) {
        log.debug("REST request to get Bunker : {}", id);
        Optional<Bunker> bunker = bunkerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bunker);
    }

    /**
     * DELETE  /bunkers/:id : delete the "id" bunker.
     *
     * @param id the id of the bunker to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bunkers/{id}")
    @Timed
    public ResponseEntity<Void> deleteBunker(@PathVariable String id) {
//	**GET BUNKER DATA FROM BLOCKCHAIN**
//        log.debug("REST request to delete Bunker : {}", id);
//        BigInteger orderId = BigInteger.valueOf(2);
//        try {
//			Tuple3<String,String,String> bunkerOrder= contract.getBunkerOrder(orderId).send();
//			log.info("Order ID: " + orderId);
//			log.info("Supply Org: " + bunkerOrder.getValue1());
//			log.info("Receive Org: " + bunkerOrder.getValue2());
//			log.info("Test Results: " + bunkerOrder.getValue3());
//		} catch (Exception e) {
//			log.error("BLOCKCHAIN GET ERROR: " + e.getMessage());
//		}
//        
//    	EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
//    			DefaultBlockParameterName.LATEST, contract.getContractAddress().substring(2));
//      
//    	**GET EVENT(OrderUpdated) DATA FROM BLOCKCHAIN**
//    	contract.orderUpdatedEventObservable(filter).subscribe(test -> {
//        	log.info("EVENT ORDER ID: " + test.orderId);
//        	log.info("EVENT SUPPLY ORG: " + test.supplyingOrg);
//        	log.info("EVENT RECEIVE ORG: " + test.receivingOrg);  
//        	log.info("EVENT TEST RESULTS: " + test.testResults);
//        });
        
        bunkerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
    
    
}
