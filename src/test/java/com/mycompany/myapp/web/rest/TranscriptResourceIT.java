package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Transcript;
import com.mycompany.myapp.repository.TranscriptRepository;
import com.mycompany.myapp.service.dto.TranscriptDTO;
import com.mycompany.myapp.service.mapper.TranscriptMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TranscriptResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TranscriptResourceIT {

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final Long DEFAULT_YEAR = 1L;
    private static final Long UPDATED_YEAR = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/transcripts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TranscriptRepository transcriptRepository;

    @Autowired
    private TranscriptMapper transcriptMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTranscriptMockMvc;

    private Transcript transcript;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transcript createEntity(EntityManager em) {
        Transcript transcript = new Transcript()
            .language(DEFAULT_LANGUAGE)
            .year(DEFAULT_YEAR)
            .status(DEFAULT_STATUS)
            .comment(DEFAULT_COMMENT)
            .date(DEFAULT_DATE);
        return transcript;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transcript createUpdatedEntity(EntityManager em) {
        Transcript transcript = new Transcript()
            .language(UPDATED_LANGUAGE)
            .year(UPDATED_YEAR)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .date(UPDATED_DATE);
        return transcript;
    }

    @BeforeEach
    public void initTest() {
        transcript = createEntity(em);
    }

    @Test
    @Transactional
    void createTranscript() throws Exception {
        int databaseSizeBeforeCreate = transcriptRepository.findAll().size();
        // Create the Transcript
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(transcript);
        restTranscriptMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transcriptDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeCreate + 1);
        Transcript testTranscript = transcriptList.get(transcriptList.size() - 1);
        assertThat(testTranscript.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testTranscript.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testTranscript.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTranscript.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTranscript.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createTranscriptWithExistingId() throws Exception {
        // Create the Transcript with an existing ID
        transcript.setId(1L);
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(transcript);

        int databaseSizeBeforeCreate = transcriptRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranscriptMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transcriptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTranscripts() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        // Get all the transcriptList
        restTranscriptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transcript.getId().intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getTranscript() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        // Get the transcript
        restTranscriptMockMvc
            .perform(get(ENTITY_API_URL_ID, transcript.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transcript.getId().intValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTranscript() throws Exception {
        // Get the transcript
        restTranscriptMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTranscript() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();

        // Update the transcript
        Transcript updatedTranscript = transcriptRepository.findById(transcript.getId()).get();
        // Disconnect from session so that the updates on updatedTranscript are not directly saved in db
        em.detach(updatedTranscript);
        updatedTranscript.language(UPDATED_LANGUAGE).year(UPDATED_YEAR).status(UPDATED_STATUS).comment(UPDATED_COMMENT).date(UPDATED_DATE);
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(updatedTranscript);

        restTranscriptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transcriptDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transcriptDTO))
            )
            .andExpect(status().isOk());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
        Transcript testTranscript = transcriptList.get(transcriptList.size() - 1);
        assertThat(testTranscript.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testTranscript.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testTranscript.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTranscript.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTranscript.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTranscript() throws Exception {
        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();
        transcript.setId(count.incrementAndGet());

        // Create the Transcript
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(transcript);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranscriptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transcriptDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transcriptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTranscript() throws Exception {
        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();
        transcript.setId(count.incrementAndGet());

        // Create the Transcript
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(transcript);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranscriptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transcriptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTranscript() throws Exception {
        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();
        transcript.setId(count.incrementAndGet());

        // Create the Transcript
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(transcript);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranscriptMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transcriptDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTranscriptWithPatch() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();

        // Update the transcript using partial update
        Transcript partialUpdatedTranscript = new Transcript();
        partialUpdatedTranscript.setId(transcript.getId());

        partialUpdatedTranscript.status(UPDATED_STATUS);

        restTranscriptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTranscript.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTranscript))
            )
            .andExpect(status().isOk());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
        Transcript testTranscript = transcriptList.get(transcriptList.size() - 1);
        assertThat(testTranscript.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testTranscript.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testTranscript.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTranscript.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTranscript.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTranscriptWithPatch() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();

        // Update the transcript using partial update
        Transcript partialUpdatedTranscript = new Transcript();
        partialUpdatedTranscript.setId(transcript.getId());

        partialUpdatedTranscript
            .language(UPDATED_LANGUAGE)
            .year(UPDATED_YEAR)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .date(UPDATED_DATE);

        restTranscriptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTranscript.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTranscript))
            )
            .andExpect(status().isOk());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
        Transcript testTranscript = transcriptList.get(transcriptList.size() - 1);
        assertThat(testTranscript.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testTranscript.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testTranscript.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTranscript.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTranscript.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTranscript() throws Exception {
        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();
        transcript.setId(count.incrementAndGet());

        // Create the Transcript
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(transcript);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranscriptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transcriptDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transcriptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTranscript() throws Exception {
        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();
        transcript.setId(count.incrementAndGet());

        // Create the Transcript
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(transcript);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranscriptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transcriptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTranscript() throws Exception {
        int databaseSizeBeforeUpdate = transcriptRepository.findAll().size();
        transcript.setId(count.incrementAndGet());

        // Create the Transcript
        TranscriptDTO transcriptDTO = transcriptMapper.toDto(transcript);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranscriptMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transcriptDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transcript in the database
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTranscript() throws Exception {
        // Initialize the database
        transcriptRepository.saveAndFlush(transcript);

        int databaseSizeBeforeDelete = transcriptRepository.findAll().size();

        // Delete the transcript
        restTranscriptMockMvc
            .perform(delete(ENTITY_API_URL_ID, transcript.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transcript> transcriptList = transcriptRepository.findAll();
        assertThat(transcriptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
