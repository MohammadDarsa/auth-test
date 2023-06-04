package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Gender;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Student} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StudentDTO implements Serializable {

    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getPlaceOfBirthEn() {
        return placeOfBirthEn;
    }

    public void setPlaceOfBirthEn(String placeOfBirthEn) {
        this.placeOfBirthEn = placeOfBirthEn;
    }

    public String getPlaceOfBirthAr() {
        return placeOfBirthAr;
    }

    public void setPlaceOfBirthAr(String placeOfBirthAr) {
        this.placeOfBirthAr = placeOfBirthAr;
    }

    public String getDateOfBirthEn() {
        return dateOfBirthEn;
    }

    public void setDateOfBirthEn(String dateOfBirthEn) {
        this.dateOfBirthEn = dateOfBirthEn;
    }

    public String getDateOfBirthAr() {
        return dateOfBirthAr;
    }

    public void setDateOfBirthAr(String dateOfBirthAr) {
        this.dateOfBirthAr = dateOfBirthAr;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentDTO)) {
            return false;
        }

        StudentDTO studentDTO = (StudentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, studentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", gender='" + getGender() + "'" +
            ", major='" + getMajor() + "'" +
            ", year=" + getYear() +
            ", nameAr='" + getNameAr() + "'" +
            ", placeOfBirthEn='" + getPlaceOfBirthEn() + "'" +
            ", placeOfBirthAr='" + getPlaceOfBirthAr() + "'" +
            ", dateOfBirthEn='" + getDateOfBirthEn() + "'" +
            ", dateOfBirthAr='" + getDateOfBirthAr() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
