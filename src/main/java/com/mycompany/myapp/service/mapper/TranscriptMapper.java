package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.domain.Transcript;
import com.mycompany.myapp.service.dto.StudentDTO;
import com.mycompany.myapp.service.dto.TranscriptDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transcript} and its DTO {@link TranscriptDTO}.
 */
@Mapper(componentModel = "spring")
public interface TranscriptMapper extends EntityMapper<TranscriptDTO, Transcript> {
    @Mapping(target = "student", source = "student", qualifiedByName = "studentId")
    TranscriptDTO toDto(Transcript s);

    @Named("studentId")
    StudentDTO toDtoStudentId(Student student);
}
