package de.bugtracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.bugtracker.domainobject.BugDO;
import de.bugtracker.domainvalue.BugStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;

@Component
public class TestUtil {


    public static BugDO createBugDO(Long id) {

        return BugDO.builder().bugId(id).status(BugStatus.OPEN).
                created(ZonedDateTime.now()).deleted(false).assignedUser("Sahil").
                projectName("Test Project").title("Bug Title").build();
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsBytes(object);
    }
}
