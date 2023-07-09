package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.StudentRepository;
import com.mycompany.myapp.service.dto.StudentDTO;
import com.mycompany.myapp.service.mapper.StudentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Student}.
 */
@Service
@Transactional
public class StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final UserService userService;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, UserService userService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.userService = userService;
    }

    /**
     * Save a student.
     *
     * @param studentDTO the entity to save.
     * @return the persisted entity.
     */
    public StudentDTO save(StudentDTO studentDTO) {
        log.debug("Request to save Student : {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    /**
     * Update a student.
     *
     * @param studentDTO the entity to save.
     * @return the persisted entity.
     */
    public StudentDTO update(StudentDTO studentDTO) {
        log.debug("Request to update Student : {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    /**
     * Partially update a student.
     *
     * @param studentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StudentDTO> partialUpdate(StudentDTO studentDTO) {
        log.debug("Request to partially update Student : {}", studentDTO);

        return studentRepository
            .findById(studentDTO.getId())
            .map(existingStudent -> {
                studentMapper.partialUpdate(existingStudent, studentDTO);

                return existingStudent;
            })
            .map(studentRepository::save)
            .map(studentMapper::toDto);
    }

    /**
     * Get all the students.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StudentDTO> findAll() {
        log.debug("Request to get all Students");
        return studentRepository.findAll().stream().map(studentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one student by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudentDTO> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id).map(studentMapper::toDto);
    }

    /**
     * Delete the student by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }

    /**
     * Get one student by user email.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Student> findByUserEmail(String email) {
        log.debug("Request to get Student : {}", email);
        return studentRepository.findByEmail(email);
    }

    //get current logged in student
    public Optional<StudentDTO> findLoggedInStudent() {
        User user = userService.getCurrentUser().orElseThrow(() -> new RuntimeException("User not logged in"));
        Optional<Authority> authority = user
            .getAuthorities()
            .stream()
            .filter(a -> a.getName().equals("ROLE_ADMIN") || a.getName().equals("ROLE_CLERIC"))
            .findFirst();
        if (authority.isPresent()) return Optional.of(new StudentDTO());
        return findByUserEmail(user.getEmail()).map(studentMapper::toDto);
    }

    //update current logged in student
    public Optional<StudentDTO> updateLoggedInStudent(StudentDTO studentDTO) {
        User user = userService.getCurrentUser().orElseThrow(() -> new RuntimeException("User not logged in"));
        studentDTO.setEmail(user.getEmail());
        this.update(studentDTO);
        return Optional.of(studentDTO);
    }

    //add student
    public StudentDTO addStudent(StudentDTO studentDto) {
        Optional<Student> student = studentRepository.findByEmail(studentDto.getEmail());
        Student s;
        if (student.isPresent()) {
            s = student.get();
            if (!s.getStudentId().equals(studentDto.getStudentId())) return null;
            s.setMajor(studentDto.getMajor());
            s.setYear(studentDto.getYear());
        } else s = studentMapper.toEntity(studentDto);
        studentRepository.save(s);
        studentDto.setId(s.getId());
        return studentDto;
    }
}
