package de.bugtracker.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.bugtracker.domainvalue.BugStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BugDTO {

    @JsonProperty("assignedUser")
    private String assignedUser;

    @JsonProperty("title")
    @NotNull(message = "Title of the bug cannot be null")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    @NotNull(message = "Status of the bug cannot be null")
    private BugStatus status;

    @JsonProperty("created")
    private ZonedDateTime created;

    @JsonProperty("projectName")
    @NotNull(message = "Project name for the bug cannot be null")
    private String projectName;

    @JsonProperty("deleted")
    private Boolean deleted;
}
