package com.mycompany.myapp.web.rest.dto.response;

import com.mycompany.myapp.service.dto.TranscriptDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentTranscriptsResponse {

    private List<TranscriptDTO> transcripts;
}
