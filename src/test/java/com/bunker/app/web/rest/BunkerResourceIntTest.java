package com.bunker.app.web.rest;

import com.bunker.app.BunkerApp;

import com.bunker.app.domain.Bunker;
import com.bunker.app.repository.BunkerRepository;
import com.bunker.app.service.BunkerService;
import com.bunker.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;
import java.util.List;


import static com.bunker.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BunkerResource REST controller.
 *
 * @see BunkerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BunkerApp.class)
public class BunkerResourceIntTest {

    private static final BigInteger DEFAULT_ORDER_ID = BigInteger.valueOf(1);
    private static final BigInteger UPDATED_ORDER_ID = BigInteger.valueOf(2);

    private static final String DEFAULT_SUPPLYING_ORG = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLYING_ORG = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVING_ORG = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVING_ORG = "BBBBBBBBBB";

    private static final String DEFAULT_TEST_RESULTS = "AAAAAAAAAA";
    private static final String UPDATED_TEST_RESULTS = "BBBBBBBBBB";

    @Autowired
    private BunkerRepository bunkerRepository;
    
    @Autowired
    private BunkerService bunkerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restBunkerMockMvc;

    private Bunker bunker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BunkerResource bunkerResource = new BunkerResource(bunkerService);
        this.restBunkerMockMvc = MockMvcBuilders.standaloneSetup(bunkerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bunker createEntity() {
        Bunker bunker = new Bunker()
            .order_id(DEFAULT_ORDER_ID)
            .supplying_org(DEFAULT_SUPPLYING_ORG)
            .receiving_org(DEFAULT_RECEIVING_ORG)
            .test_results(DEFAULT_TEST_RESULTS);
        return bunker;
    }

    @Before
    public void initTest() {
        bunkerRepository.deleteAll();
        bunker = createEntity();
    }

    @Test
    public void createBunker() throws Exception {
        int databaseSizeBeforeCreate = bunkerRepository.findAll().size();

        // Create the Bunker
        restBunkerMockMvc.perform(post("/api/bunkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bunker)))
            .andExpect(status().isCreated());

        // Validate the Bunker in the database
        List<Bunker> bunkerList = bunkerRepository.findAll();
        assertThat(bunkerList).hasSize(databaseSizeBeforeCreate + 1);
        Bunker testBunker = bunkerList.get(bunkerList.size() - 1);
        assertThat(testBunker.getOrder_id()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testBunker.getSupplying_org()).isEqualTo(DEFAULT_SUPPLYING_ORG);
        assertThat(testBunker.getReceiving_org()).isEqualTo(DEFAULT_RECEIVING_ORG);
        assertThat(testBunker.getTest_results()).isEqualTo(DEFAULT_TEST_RESULTS);
    }

    @Test
    public void createBunkerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bunkerRepository.findAll().size();

        // Create the Bunker with an existing ID
        bunker.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restBunkerMockMvc.perform(post("/api/bunkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bunker)))
            .andExpect(status().isBadRequest());

        // Validate the Bunker in the database
        List<Bunker> bunkerList = bunkerRepository.findAll();
        assertThat(bunkerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkOrder_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = bunkerRepository.findAll().size();
        // set the field null
        bunker.setOrder_id(null);

        // Create the Bunker, which fails.

        restBunkerMockMvc.perform(post("/api/bunkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bunker)))
            .andExpect(status().isBadRequest());

        List<Bunker> bunkerList = bunkerRepository.findAll();
        assertThat(bunkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllBunkers() throws Exception {
        // Initialize the database
        bunkerRepository.save(bunker);

        // Get all the bunkerList
        restBunkerMockMvc.perform(get("/api/bunkers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bunker.getId())))
            .andExpect(jsonPath("$.[*].order_id").value(hasItem(DEFAULT_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].supplying_org").value(hasItem(DEFAULT_SUPPLYING_ORG.toString())))
            .andExpect(jsonPath("$.[*].receiving_org").value(hasItem(DEFAULT_RECEIVING_ORG.toString())))
            .andExpect(jsonPath("$.[*].test_results").value(hasItem(DEFAULT_TEST_RESULTS.toString())));
    }
    
    @Test
    public void getBunker() throws Exception {
        // Initialize the database
        bunkerRepository.save(bunker);

        // Get the bunker
        restBunkerMockMvc.perform(get("/api/bunkers/{id}", bunker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bunker.getId()))
            .andExpect(jsonPath("$.order_id").value(DEFAULT_ORDER_ID.toString()))
            .andExpect(jsonPath("$.supplying_org").value(DEFAULT_SUPPLYING_ORG.toString()))
            .andExpect(jsonPath("$.receiving_org").value(DEFAULT_RECEIVING_ORG.toString()))
            .andExpect(jsonPath("$.test_results").value(DEFAULT_TEST_RESULTS.toString()));
    }

    @Test
    public void getNonExistingBunker() throws Exception {
        // Get the bunker
        restBunkerMockMvc.perform(get("/api/bunkers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBunker() throws Exception {
        // Initialize the database
        bunkerService.save(bunker);

        int databaseSizeBeforeUpdate = bunkerRepository.findAll().size();

        // Update the bunker
        Bunker updatedBunker = bunkerRepository.findById(bunker.getId()).get();
        updatedBunker
            .order_id(UPDATED_ORDER_ID)
            .supplying_org(UPDATED_SUPPLYING_ORG)
            .receiving_org(UPDATED_RECEIVING_ORG)
            .test_results(UPDATED_TEST_RESULTS);

        restBunkerMockMvc.perform(put("/api/bunkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBunker)))
            .andExpect(status().isOk());

        // Validate the Bunker in the database
        List<Bunker> bunkerList = bunkerRepository.findAll();
        assertThat(bunkerList).hasSize(databaseSizeBeforeUpdate);
        Bunker testBunker = bunkerList.get(bunkerList.size() - 1);
        assertThat(testBunker.getOrder_id()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testBunker.getSupplying_org()).isEqualTo(UPDATED_SUPPLYING_ORG);
        assertThat(testBunker.getReceiving_org()).isEqualTo(UPDATED_RECEIVING_ORG);
        assertThat(testBunker.getTest_results()).isEqualTo(UPDATED_TEST_RESULTS);
    }

    @Test
    public void updateNonExistingBunker() throws Exception {
        int databaseSizeBeforeUpdate = bunkerRepository.findAll().size();

        // Create the Bunker

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBunkerMockMvc.perform(put("/api/bunkers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bunker)))
            .andExpect(status().isBadRequest());

        // Validate the Bunker in the database
        List<Bunker> bunkerList = bunkerRepository.findAll();
        assertThat(bunkerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBunker() throws Exception {
        // Initialize the database
        bunkerService.save(bunker);

        int databaseSizeBeforeDelete = bunkerRepository.findAll().size();

        // Get the bunker
        restBunkerMockMvc.perform(delete("/api/bunkers/{id}", bunker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bunker> bunkerList = bunkerRepository.findAll();
        assertThat(bunkerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bunker.class);
        Bunker bunker1 = new Bunker();
        bunker1.setId("id1");
        Bunker bunker2 = new Bunker();
        bunker2.setId(bunker1.getId());
        assertThat(bunker1).isEqualTo(bunker2);
        bunker2.setId("id2");
        assertThat(bunker1).isNotEqualTo(bunker2);
        bunker1.setId(null);
        assertThat(bunker1).isNotEqualTo(bunker2);
    }
}
