package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.domain.enumeration.Gender;
import com.mycompany.myapp.repository.StudentRepository;
import com.mycompany.myapp.service.dto.StudentDTO;
import com.mycompany.myapp.service.mapper.StudentMapper;
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
 * Integration tests for the {@link StudentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudentResourceIT {

    private static final Long DEFAULT_STUDENT_ID = 1L;
    private static final Long UPDATED_STUDENT_ID = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_MAJOR = "AAAAAAAAAA";
    private static final String UPDATED_MAJOR = "BBBBBBBBBB";

    private static final Long DEFAULT_YEAR = 1L;
    private static final Long UPDATED_YEAR = 2L;

    private static final String DEFAULT_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE_OF_BIRTH_EN = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_BIRTH_EN = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE_OF_BIRTH_AR = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_BIRTH_AR = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH_EN = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH_EN = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH_AR = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/students";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentMockMvc;

    private Student student;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .studentId(DEFAULT_STUDENT_ID)
            .email(DEFAULT_EMAIL)
            .name(DEFAULT_NAME)
            .gender(DEFAULT_GENDER)
            .major(DEFAULT_MAJOR)
            .year(DEFAULT_YEAR)
            .nameAr(DEFAULT_NAME_AR)
            .placeOfBirthEn(DEFAULT_PLACE_OF_BIRTH_EN)
            .placeOfBirthAr(DEFAULT_PLACE_OF_BIRTH_AR)
            .dateOfBirthEn(DEFAULT_DATE_OF_BIRTH_EN)
            .dateOfBirthAr(DEFAULT_DATE_OF_BIRTH_AR)
            .nationality(DEFAULT_NATIONALITY)
            .phone(DEFAULT_PHONE);
        return student;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createUpdatedEntity(EntityManager em) {
        Student student = new Student()
            .studentId(UPDATED_STUDENT_ID)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .major(UPDATED_MAJOR)
            .year(UPDATED_YEAR)
            .nameAr(UPDATED_NAME_AR)
            .placeOfBirthEn(UPDATED_PLACE_OF_BIRTH_EN)
            .placeOfBirthAr(UPDATED_PLACE_OF_BIRTH_AR)
            .dateOfBirthEn(UPDATED_DATE_OF_BIRTH_EN)
            .dateOfBirthAr(UPDATED_DATE_OF_BIRTH_AR)
            .nationality(UPDATED_NATIONALITY)
            .phone(UPDATED_PHONE);
        return student;
    }

    @BeforeEach
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();
        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);
        restStudentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);
        assertThat(testStudent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStudent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStudent.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testStudent.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testStudent.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testStudent.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testStudent.getPlaceOfBirthEn()).isEqualTo(DEFAULT_PLACE_OF_BIRTH_EN);
        assertThat(testStudent.getPlaceOfBirthAr()).isEqualTo(DEFAULT_PLACE_OF_BIRTH_AR);
        assertThat(testStudent.getDateOfBirthEn()).isEqualTo(DEFAULT_DATE_OF_BIRTH_EN);
        assertThat(testStudent.getDateOfBirthAr()).isEqualTo(DEFAULT_DATE_OF_BIRTH_AR);
        assertThat(testStudent.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testStudent.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void createStudentWithExistingId() throws Exception {
        // Create the Student with an existing ID
        student.setId(1L);
        StudentDTO studentDTO = studentMapper.toDto(student);

        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].placeOfBirthEn").value(hasItem(DEFAULT_PLACE_OF_BIRTH_EN)))
            .andExpect(jsonPath("$.[*].placeOfBirthAr").value(hasItem(DEFAULT_PLACE_OF_BIRTH_AR)))
            .andExpect(jsonPath("$.[*].dateOfBirthEn").value(hasItem(DEFAULT_DATE_OF_BIRTH_EN)))
            .andExpect(jsonPath("$.[*].dateOfBirthAr").value(hasItem(DEFAULT_DATE_OF_BIRTH_AR)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc
            .perform(get(ENTITY_API_URL_ID, student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.intValue()))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.placeOfBirthEn").value(DEFAULT_PLACE_OF_BIRTH_EN))
            .andExpect(jsonPath("$.placeOfBirthAr").value(DEFAULT_PLACE_OF_BIRTH_AR))
            .andExpect(jsonPath("$.dateOfBirthEn").value(DEFAULT_DATE_OF_BIRTH_EN))
            .andExpect(jsonPath("$.dateOfBirthAr").value(DEFAULT_DATE_OF_BIRTH_AR))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .studentId(UPDATED_STUDENT_ID)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .major(UPDATED_MAJOR)
            .year(UPDATED_YEAR)
            .nameAr(UPDATED_NAME_AR)
            .placeOfBirthEn(UPDATED_PLACE_OF_BIRTH_EN)
            .placeOfBirthAr(UPDATED_PLACE_OF_BIRTH_AR)
            .dateOfBirthEn(UPDATED_DATE_OF_BIRTH_EN)
            .dateOfBirthAr(UPDATED_DATE_OF_BIRTH_AR)
            .nationality(UPDATED_NATIONALITY)
            .phone(UPDATED_PHONE);
        StudentDTO studentDTO = studentMapper.toDto(updatedStudent);

        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testStudent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStudent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStudent.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testStudent.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testStudent.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testStudent.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testStudent.getPlaceOfBirthEn()).isEqualTo(UPDATED_PLACE_OF_BIRTH_EN);
        assertThat(testStudent.getPlaceOfBirthAr()).isEqualTo(UPDATED_PLACE_OF_BIRTH_AR);
        assertThat(testStudent.getDateOfBirthEn()).isEqualTo(UPDATED_DATE_OF_BIRTH_EN);
        assertThat(testStudent.getDateOfBirthAr()).isEqualTo(UPDATED_DATE_OF_BIRTH_AR);
        assertThat(testStudent.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testStudent.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudentWithPatch() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student using partial update
        Student partialUpdatedStudent = new Student();
        partialUpdatedStudent.setId(student.getId());

        partialUpdatedStudent
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .major(UPDATED_MAJOR)
            .placeOfBirthAr(UPDATED_PLACE_OF_BIRTH_AR)
            .nationality(UPDATED_NATIONALITY)
            .phone(UPDATED_PHONE);

        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudent))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);
        assertThat(testStudent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStudent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStudent.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testStudent.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testStudent.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testStudent.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testStudent.getPlaceOfBirthEn()).isEqualTo(DEFAULT_PLACE_OF_BIRTH_EN);
        assertThat(testStudent.getPlaceOfBirthAr()).isEqualTo(UPDATED_PLACE_OF_BIRTH_AR);
        assertThat(testStudent.getDateOfBirthEn()).isEqualTo(DEFAULT_DATE_OF_BIRTH_EN);
        assertThat(testStudent.getDateOfBirthAr()).isEqualTo(DEFAULT_DATE_OF_BIRTH_AR);
        assertThat(testStudent.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testStudent.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateStudentWithPatch() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student using partial update
        Student partialUpdatedStudent = new Student();
        partialUpdatedStudent.setId(student.getId());

        partialUpdatedStudent
            .studentId(UPDATED_STUDENT_ID)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .major(UPDATED_MAJOR)
            .year(UPDATED_YEAR)
            .nameAr(UPDATED_NAME_AR)
            .placeOfBirthEn(UPDATED_PLACE_OF_BIRTH_EN)
            .placeOfBirthAr(UPDATED_PLACE_OF_BIRTH_AR)
            .dateOfBirthEn(UPDATED_DATE_OF_BIRTH_EN)
            .dateOfBirthAr(UPDATED_DATE_OF_BIRTH_AR)
            .nationality(UPDATED_NATIONALITY)
            .phone(UPDATED_PHONE);

        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudent))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testStudent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStudent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStudent.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testStudent.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testStudent.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testStudent.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testStudent.getPlaceOfBirthEn()).isEqualTo(UPDATED_PLACE_OF_BIRTH_EN);
        assertThat(testStudent.getPlaceOfBirthAr()).isEqualTo(UPDATED_PLACE_OF_BIRTH_AR);
        assertThat(testStudent.getDateOfBirthEn()).isEqualTo(UPDATED_DATE_OF_BIRTH_EN);
        assertThat(testStudent.getDateOfBirthAr()).isEqualTo(UPDATED_DATE_OF_BIRTH_AR);
        assertThat(testStudent.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testStudent.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studentDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc
            .perform(delete(ENTITY_API_URL_ID, student.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
