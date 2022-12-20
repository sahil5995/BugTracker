package de.bugtracker.service;


import com.fasterxml.jackson.databind.JsonMappingException;
import de.bugtracker.domainobject.BugDO;
import de.bugtracker.exception.custom.ConstraintViolationException;
import de.bugtracker.exception.custom.EntityNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public interface BugTrackerService {

    BugDO createBug(BugDO bugDO) throws ConstraintViolationException;

    BugDO findBug(Long bugId) throws EntityNotFoundException;

    List<BugDO> getAllBugs(BugDO bugDO);

    List<BugDO> getAllBugsAfterDate(String date);

    BugDO deleteBug(Long bugId) throws EntityNotFoundException;

    BugDO updateBug(Long bugId, Map<String, Object> fields) throws EntityNotFoundException, JsonMappingException;

}
