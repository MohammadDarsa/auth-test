package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Transcript} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TranscriptDTO implements Serializable {

    private Long id;

    private String language;

    private Long year;

    private String status;

    private String comment;

    private LocalDate date;

    private StudentDTO student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TranscriptDTO)) {
            return false;
        }

        TranscriptDTO transcriptDTO = (TranscriptDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transcriptDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TranscriptDTO{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            ", year=" + getYear() +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            ", date='" + getDate() + "'" +
            ", student=" + getStudent() +
            "}";
    }
}
