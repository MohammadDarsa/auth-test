package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //find by email
    Optional<Student> findByEmail(String email);
}
