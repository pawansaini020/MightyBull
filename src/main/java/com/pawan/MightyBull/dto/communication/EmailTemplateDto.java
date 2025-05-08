package com.pawan.MightyBull.dto.communication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Pawan Saini
 * Created on 17/01/25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class EmailTemplateDto extends CommunicationDto {

    private String subject;
    private String from;
    private List<String> to;
    private List<String> cc;
    private String[] args;
    private String template;
    private String subjectTemplate;
    private String[] subjectArgs;
    private List<String> replyTo;
}
