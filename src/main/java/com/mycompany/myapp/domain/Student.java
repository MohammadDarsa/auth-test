package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Gender;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "major")
    private String major;

    @Column(name = "year")
    private Long year;

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "place_of_birth_en")
    private String placeOfBirthEn;

    @Column(name = "place_of_birth_ar")
    private String placeOfBirthAr;

    @Column(name = "date_of_birth_en")
    private String dateOfBirthEn;

    @Column(name = "date_of_birth_ar")
    private String dateOfBirthAr;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "student" }, allowSetters = true)
    private Set<Transcript> transcripts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Student id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return this.studentId;
    }

    public Student studentId(Long studentId) {
        this.setStudentId(studentId);
        return this;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return this.email;
    }

    public Student email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public Student name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Student gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return this.major;
    }

    public Student major(String major) {
        this.setMajor(major);
        return this;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Long getYear() {
        return this.year;
    }

    public Student year(Long year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getNameAr() {
        return this.nameAr;
    }

    public Student nameAr(String nameAr) {
        this.setNameAr(nameAr);
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getPlaceOfBirthEn() {
        return this.placeOfBirthEn;
    }

    public Student placeOfBirthEn(String placeOfBirthEn) {
        this.setPlaceOfBirthEn(placeOfBirthEn);
        return this;
    }

    public void setPlaceOfBirthEn(String placeOfBirthEn) {
        this.placeOfBirthEn = placeOfBirthEn;
    }

    public String getPlaceOfBirthAr() {
        return this.placeOfBirthAr;
    }

    public Student placeOfBirthAr(String placeOfBirthAr) {
        this.setPlaceOfBirthAr(placeOfBirthAr);
        return this;
    }

    public void setPlaceOfBirthAr(String placeOfBirthAr) {
        this.placeOfBirthAr = placeOfBirthAr;
    }

    public String getDateOfBirthEn() {
        return this.dateOfBirthEn;
    }

    public Student dateOfBirthEn(String dateOfBirthEn) {
        this.setDateOfBirthEn(dateOfBirthEn);
        return this;
    }

    public void setDateOfBirthEn(String dateOfBirthEn) {
        this.dateOfBirthEn = dateOfBirthEn;
    }

    public String getDateOfBirthAr() {
        return this.dateOfBirthAr;
    }

    public Student dateOfBirthAr(String dateOfBirthAr) {
        this.setDateOfBirthAr(dateOfBirthAr);
        return this;
    }

    public void setDateOfBirthAr(String dateOfBirthAr) {
        this.dateOfBirthAr = dateOfBirthAr;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Student nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return this.phone;
    }

    public Student phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Transcript> getTranscripts() {
        return this.transcripts;
    }

    public void setTranscripts(Set<Transcript> transcripts) {
        if (this.transcripts != null) {
            this.transcripts.forEach(i -> i.setStudent(null));
        }
        if (transcripts != null) {
            transcripts.forEach(i -> i.setStudent(this));
        }
        this.transcripts = transcripts;
    }

    public Student transcripts(Set<Transcript> transcripts) {
        this.setTranscripts(transcripts);
        return this;
    }

    public Student addTranscript(Transcript transcript) {
        this.transcripts.add(transcript);
        transcript.setStudent(this);
        return this;
    }

    public Student removeTranscript(Transcript transcript) {
        this.transcripts.remove(transcript);
        transcript.setStudent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", studentId=" + getStudentId() +
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
