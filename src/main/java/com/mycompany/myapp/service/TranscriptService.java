package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Transcript;
import com.mycompany.myapp.repository.TranscriptRepository;
import com.mycompany.myapp.service.dto.StudentDTO;
import com.mycompany.myapp.service.dto.TranscriptDTO;
import com.mycompany.myapp.service.mapper.TranscriptMapper;
import com.mycompany.myapp.web.rest.dto.response.StudentTranscriptsResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Transcript}.
 */
@Service
@Transactional
public class TranscriptService {

    private final Logger log = LoggerFactory.getLogger(TranscriptService.class);

    private final TranscriptRepository transcriptRepository;

    private final TranscriptMapper transcriptMapper;

    private final StudentService studentService;

    public TranscriptService(TranscriptRepository transcriptRepository, TranscriptMapper transcriptMapper, StudentService studentService) {
        this.transcriptRepository = transcriptRepository;
        this.transcriptMapper = transcriptMapper;
        this.studentService = studentService;
    }

    /**
     * Save a transcript.
     *
     * @param transcriptDTO the entity to save.
     * @return the persisted entity.
     */
    public TranscriptDTO save(TranscriptDTO transcriptDTO) {
        log.debug("Request to save Transcript : {}", transcriptDTO);
        Transcript transcript = transcriptMapper.toEntity(transcriptDTO);
        transcript = transcriptRepository.save(transcript);
        return transcriptMapper.toDto(transcript);
    }

    /**
     * Update a transcript.
     *
     * @param transcriptDTO the entity to save.
     * @return the persisted entity.
     */
    public TranscriptDTO update(TranscriptDTO transcriptDTO) {
        log.debug("Request to update Transcript : {}", transcriptDTO);
        Transcript transcript = transcriptMapper.toEntity(transcriptDTO);
        transcript = transcriptRepository.save(transcript);
        return transcriptMapper.toDto(transcript);
    }

    /**
     * Partially update a transcript.
     *
     * @param transcriptDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TranscriptDTO> partialUpdate(TranscriptDTO transcriptDTO) {
        log.debug("Request to partially update Transcript : {}", transcriptDTO);

        return transcriptRepository
            .findById(transcriptDTO.getId())
            .map(existingTranscript -> {
                transcriptMapper.partialUpdate(existingTranscript, transcriptDTO);

                return existingTranscript;
            })
            .map(transcriptRepository::save)
            .map(transcriptMapper::toDto);
    }

    /**
     * Get all the transcripts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TranscriptDTO> findAll() {
        log.debug("Request to get all Transcripts");
        return transcriptRepository.findAll().stream().map(transcriptMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one transcript by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TranscriptDTO> findOne(Long id) {
        log.debug("Request to get Transcript : {}", id);
        return transcriptRepository.findById(id).map(transcriptMapper::toDto);
    }

    /**
     * Delete the transcript by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Transcript : {}", id);
        transcriptRepository.deleteById(id);
    }

    public StudentTranscriptsResponse getStudentTranscripts() {
        StudentDTO student = studentService.findLoggedInStudent().orElseThrow(() -> new RuntimeException("Student not found"));
        List<TranscriptDTO> transcripts = transcriptRepository
            .findByStudent_Id(student.getId())
            .stream()
            .map(transcriptMapper::toDto)
            .collect(Collectors.toList());
        return new StudentTranscriptsResponse(transcripts);
    }
}
