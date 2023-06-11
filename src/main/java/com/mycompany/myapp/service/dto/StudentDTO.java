package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Gender;
import java.io.Serializable;
import java.util.Objects;
import lombok.Data;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Student} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class StudentDTO implements Serializable {

    private Long id;

    private Long studentId;

    private String email;

    private String name;

    private Gender gender;

    private String major;

    private Long year;

    private String nameAr;

    private String placeOfBirthEn;

    private String placeOfBirthAr;

    private String dateOfBirthEn;

    private String dateOfBirthAr;

    private String nationality;

    private String phone;
}
