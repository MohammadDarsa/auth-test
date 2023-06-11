package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.domain.enumeration.Gender;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data JPA repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //find by email
    Optional<Student> findByEmail(String email);
}
