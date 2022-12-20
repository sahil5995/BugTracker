package de.bugtracker.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bugtracker.TestUtil;
import de.bugtracker.controller.mapper.BugMapper;
import de.bugtracker.dao.BugTrackerRepository;
import de.bugtracker.domainobject.BugDO;
import de.bugtracker.dto.BugDTO;
import de.bugtracker.exception.custom.ConstraintViolationException;
import de.bugtracker.exception.custom.EntityNotFoundException;
import de.bugtracker.service.impl.BugTrackerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class BugTrackerServiceImplTest {

    @InjectMocks
    private BugTrackerServiceImpl bugTrackerService;

    @Mock
    private BugTrackerRepository bugTrackerRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void testCreate() throws ConstraintViolationException {

        //Arrange
        BugDO bugDO = TestUtil.createBugDO(1L);
        Mockito.when(bugTrackerRepository.save(any())).thenReturn(bugDO);

        //Act
        BugDO result = bugTrackerService.createBug(bugDO);

        //Assert
        Mockito.verify(bugTrackerRepository, Mockito.atLeastOnce()).save(any());
        Assert.assertEquals("Test Project", result.getProjectName());

    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateException() throws ConstraintViolationException {

        //Arrange
        BugDO bugDO = TestUtil.createBugDO(1L);
        Mockito.when(bugTrackerRepository.save(any())).thenThrow(new DataIntegrityViolationException(""));

        //Act
        bugTrackerService.createBug(bugDO);
    }

    @Test
    public void testFind() throws EntityNotFoundException {

        //Arrange
        BugDO bugDO = TestUtil.createBugDO(1L);
        Mockito.when(bugTrackerRepository.findById(any())).thenReturn(Optional.of(bugDO));

        //Act
        BugDO result = bugTrackerService.findBug(1L);

        //Assert
        Assert.assertEquals("Test Project", result.getProjectName());

    }

//    @Test(expected = IllegalArgumentException.class)
//    public void testEntityException() throws EntityNotFoundException {
//        BugDO result = bugTrackerService.findBug(1L);
//    }


    @Test
    public void testGetAllBugs() {

        //Arrange
        BugDO bugDO1 = TestUtil.createBugDO(1L);
        BugDO bugDO2 = TestUtil.createBugDO(2L);
        List<BugDO> list = new ArrayList<>();
        list.add(bugDO1);
        list.add(bugDO2);

        BugDTO request = BugDTO.builder().title("Bug Title").build();
        Mockito.when(bugTrackerRepository.findAll(any(Example.class))).thenReturn(list);

        //Act
        List<BugDO> result = bugTrackerService.getAllBugs(BugMapper.INSTANCE.makeDO(request));

        //Assert
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testDelete() throws EntityNotFoundException {

        //Arrange
        BugDO bugDO = TestUtil.createBugDO(1L);
        bugDO.setDeleted(true);
        Mockito.when(bugTrackerRepository.findById(any())).thenReturn(Optional.of(bugDO));

        //Act
        BugDO result = bugTrackerService.deleteBug(1L);

        //Assert
        Assert.assertTrue(result.isDeleted());
    }

    @Test
    public void testUpdate() throws JsonMappingException, EntityNotFoundException {

        //Arrange
        BugDO bugDO = TestUtil.createBugDO(1L);
        bugDO.setTitle("New Modified Title");
        Mockito.when(bugTrackerRepository.findById(any())).thenReturn(Optional.of(bugDO));
        Mockito.when(objectMapper.updateValue(any(),any())).thenReturn(bugDO);
        Map<String, Object> fields = new HashMap<>();
        fields.put("title","New Modified Title");

        //Act
        BugDO result = bugTrackerService.updateBug(1L,fields );

        //Assert
        Assert.assertEquals("New Modified Title", result.getTitle());
    }

    //@Test
    public void testGetAllBugsAfetrDate() {

        //Arrange
        BugDO bugDO1 = TestUtil.createBugDO(1L);
        BugDO bugDO2 = TestUtil.createBugDO(2L);
        List<BugDO> list = new ArrayList<>();
        list.add(bugDO1);
        list.add(bugDO2);

        Mockito.when(bugTrackerRepository.findAllByCreatedAfter(any())).thenReturn(list);

        String date="2022-10-11T11:33:04.057318+02:00";

        //Act
        List<BugDO> result = bugTrackerService.getAllBugsAfterDate(date);

        //Assert
        Assert.assertEquals(2, result.size());
    }

    public <T,G> List<G> mytest(T[] a, Function<T,G> changetToNumber){

       return Arrays.stream(a).
               map(changetToNumber).
               collect(Collectors.toList());
    }


    public static void main(String[] args) {
        BugTrackerServiceImplTest ob = new BugTrackerServiceImplTest();
        Integer[] intArray = {1, 2, 3, 4, 5};

      List<String> list =  ob.mytest(intArray,Object::toString);
    }

}
