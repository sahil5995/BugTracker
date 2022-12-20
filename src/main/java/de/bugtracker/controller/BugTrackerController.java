package de.bugtracker.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import de.bugtracker.controller.mapper.BugMapper;
import de.bugtracker.domainobject.BugDO;
import de.bugtracker.dto.BugDTO;
import de.bugtracker.exception.custom.ConstraintViolationException;
import de.bugtracker.exception.custom.EntityNotFoundException;
import de.bugtracker.service.BugTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("bugtracker")
public class BugTrackerController {

    private final BugTrackerService bugTrackerService;
    private final BugMapper bugMapper;

    @Autowired
    public BugTrackerController(final BugTrackerService bugTrackerService, final BugMapper bugMapper) {
        this.bugTrackerService = bugTrackerService;
        this.bugMapper = bugMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BugDTO createBug(@Valid @RequestBody BugDTO bugDTO) throws ConstraintViolationException {
        BugDO bugdo = bugTrackerService.createBug(bugMapper.makeDO(bugDTO));
        return bugMapper.makeDTO(bugdo);
    }

    @GetMapping("/{bugId}")
    public BugDTO getBug(@PathVariable long bugId) throws EntityNotFoundException {
        BugDO result = bugTrackerService.findBug(bugId);
        return bugMapper.makeDTO(result);
    }

    @GetMapping
    public List<BugDTO> getAllBugs(@RequestBody(required=false)  BugDTO bugDTO) {
        List<BugDO> result = bugTrackerService.getAllBugs(bugMapper.makeDO(bugDTO));
        return bugMapper.createList(result);
    }

    @GetMapping("/withdate")
    public List<BugDTO> getBugsAfterDate(@RequestParam("datetime") String date) {
        List<BugDO> result = bugTrackerService.getAllBugsAfterDate(date);
        return bugMapper.createList(result);
    }

    @DeleteMapping("/{bugId}")
    public BugDTO deleteBug(@PathVariable long bugId) throws EntityNotFoundException {
        BugDO result = bugTrackerService.deleteBug(bugId);
        return bugMapper.makeDTO(result);
    }

    @PatchMapping(path = "/{bugId}")
    public BugDTO updateBug(@PathVariable long bugId, @RequestBody Map<String, Object> fields) throws EntityNotFoundException, JsonMappingException {
        BugDO result = bugTrackerService.updateBug(bugId, fields);
        return bugMapper.makeDTO(result);
    }

    @PutMapping(path = "/{bugId}")
    public BugDTO updateMQT(@PathVariable long bugId, @RequestBody Map<String, Object> fields) throws EntityNotFoundException, JsonMappingException {
        BugDO result = bugTrackerService.updateBug(bugId, fields);
        return bugMapper.makeDTO(result);
    }

}