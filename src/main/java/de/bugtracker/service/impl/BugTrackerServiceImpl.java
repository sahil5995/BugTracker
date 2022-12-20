package de.bugtracker.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bugtracker.dao.BugTrackerRepository;
import de.bugtracker.domainobject.BugDO;
import de.bugtracker.exception.custom.ConstraintViolationException;
import de.bugtracker.exception.custom.EntityNotFoundException;
import de.bugtracker.service.BugTrackerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BugTrackerServiceImpl implements BugTrackerService {

    private final BugTrackerRepository bugTrackerRepository;

    private final ObjectMapper objectMapper;

//    final static DateTimeFormatter formatter
//            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z");

    public BugTrackerServiceImpl(BugTrackerRepository bugTrackerRepository, ObjectMapper objectMapper) {
        this.bugTrackerRepository = bugTrackerRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * This method is used to create a new Bug
     *
     * @param bugDO Entity object
     * @return BugDO object
     * @throws ConstraintViolationException
     */
    @Override
    public BugDO createBug(BugDO bugDO) throws ConstraintViolationException {
        BugDO responseBugDO;
        try {
            responseBugDO = bugTrackerRepository.save(bugDO);
            log.info("Bug created successfully with id {}", responseBugDO.getBugId());
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(e.getMessage());
        }
        return responseBugDO;
    }

    /**
     * This method is used to find the bug
     *
     * @param bugId Long
     * @return BugDO object
     * @throws EntityNotFoundException
     */
    @Override
    public BugDO findBug(Long bugId) throws EntityNotFoundException {
        return bugTrackerRepository.findById(bugId).
                orElseThrow(() -> new EntityNotFoundException("Could not find bug entity with id:" + bugId));
    }

    /**
     * This method is used to fetch all the bugs based on the given search criteria.
     *
     * @param bugDO BugDO
     * @return List<BugDO>
     */
    @Override
    public List<BugDO> getAllBugs(BugDO bugDO) {
        List<BugDO> list;
        if (bugDO != null) {
            list = getWithCriteria(bugDO);
        } else {
            list = bugTrackerRepository.findAll();
        }
        log.info("Found the Bug list with size {}", list.size());
        return list;
    }




    private List<BugDO> getWithCriteria(BugDO bugDO) {
        List<BugDO> list;
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreNullValues();
        list = bugTrackerRepository.findAll(Example.of(bugDO, exampleMatcher));
        return list;
    }

    /**
     * Method is used to delete the bug
     *
     * @param bugId Long
     * @return BugDO
     * @throws EntityNotFoundException
     */
    @Transactional
    @Override
    public BugDO deleteBug(Long bugId) throws EntityNotFoundException {
        BugDO bugdo = findBug(bugId);
        bugdo.setDeleted(true);
        log.info("Bug softly deleted with id {}", bugdo.getBugId());
        return bugdo;
    }

    /**
     * Method is used to update the bug
     *
     * @param bugId  Long
     * @param fields Map
     * @return BugDO updated object
     * @throws EntityNotFoundException
     * @throws JsonMappingException
     */
    @Transactional
    @Override
    public BugDO updateBug(Long bugId, Map<String, Object> fields) throws EntityNotFoundException, JsonMappingException {
        BugDO result = findBug(bugId);
        log.info("Updating bug with id {}", result.getBugId());
        return objectMapper.updateValue(result, fields);
    }

    @Override
    public List<BugDO> getAllBugsAfterDate(String datetime) {
//        ZonedDateTime zdtWithZoneOffset = ZonedDateTime
//                .parse(datetime, formatter);
//      List<BugDO> result =  bugTrackerRepository.findAllByCreatedAfter(zdtWithZoneOffset);
        return null;
    }
}
