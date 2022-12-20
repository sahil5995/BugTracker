package de.bugtracker.controller;

import de.bugtracker.TestUtil;
import de.bugtracker.controller.mapper.BugMapper;
import de.bugtracker.domainobject.BugDO;
import de.bugtracker.domainvalue.BugStatus;
import de.bugtracker.dto.BugDTO;
import de.bugtracker.service.BugTrackerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BugTrackerController.class)
public class BugTrackerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BugMapper mapper;
    @MockBean
    private BugTrackerService bugTrackerService;


    @Test
    public void testCreateCar() throws Exception{

            //Arrange
            BugDO bugDO = TestUtil.createBugDO(1L);
            bugDO.setBugId(1L);
            BugDTO request = BugMapper.INSTANCE.makeDTO(bugDO);
            Mockito.when(bugTrackerService.createBug(any())).thenReturn(bugDO);
            when(mapper.makeDO(any())).thenReturn(bugDO);
            when(mapper.makeDTO(any())).thenReturn(request);
            //Act & Assert
            mockMvc.perform(post("http://localhost:8081/bugtracker").contentType(MediaType.APPLICATION_JSON).
                            content(TestUtil.convertObjectToJsonBytes(request))).
                    andDo(print()).andExpect(status().isCreated()).
                    andExpect(jsonPath("$.title").exists()).
                    andExpect(jsonPath("$.projectName").exists());

            Mockito.verify(bugTrackerService, times(1)).createBug(any());

    }

    @Test
    public void testBadRequest() throws Exception{

        //Arrange
        BugDO bugDO = BugDO.builder().bugId(1L).status(BugStatus.OPEN).
                created(ZonedDateTime.now()).deleted(false).assignedUser("Sahil").
                projectName("Test Project").build();

        BugDTO request = BugMapper.INSTANCE.makeDTO(bugDO);
        Mockito.when(bugTrackerService.createBug(any())).thenReturn(bugDO);
        when(mapper.makeDO(any())).thenReturn(bugDO);
        when(mapper.makeDTO(any())).thenReturn(request);
        //Act & Assert
        mockMvc.perform(post("http://localhost:8081/bugtracker").contentType(MediaType.APPLICATION_JSON).
                        content(TestUtil.convertObjectToJsonBytes(request))).
                andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetBug() throws Exception{

        //Arrange
        BugDO bugDO = TestUtil.createBugDO(1L);
        BugDTO result = BugMapper.INSTANCE.makeDTO(bugDO);
        Mockito.when(bugTrackerService.findBug(any())).thenReturn(bugDO);
        when(mapper.makeDTO(any())).thenReturn(result);
        //Act & Assert
        mockMvc.perform(get("http://localhost:8081/bugtracker/1").contentType(MediaType.APPLICATION_JSON)).
                andDo(print()).andExpect(status().isOk()).
                andExpect(jsonPath("$.title").exists()).
                andExpect(jsonPath("$.projectName").exists());

        Mockito.verify(bugTrackerService, times(1)).findBug(any());

    }

    @Test
    public void testGetAllBug() throws Exception{

        //Arrange
        BugDO bugDO1 = TestUtil.createBugDO(1L);
        BugDO bugDO2 = TestUtil.createBugDO(2L);
        List<BugDO> listDOs = new ArrayList<>();
        listDOs.add(bugDO1);
        listDOs.add(bugDO2);

        BugDTO bugDTO1 = BugMapper.INSTANCE.makeDTO(bugDO1);
        BugDTO bugDTO2 = BugMapper.INSTANCE.makeDTO(bugDO2);
        List<BugDTO> listDTOs = new ArrayList<>();
        listDTOs.add(bugDTO1);
        listDTOs.add(bugDTO2);

        Mockito.when(bugTrackerService.getAllBugs(any())).thenReturn(listDOs);
        when(mapper.createList(any())).thenReturn(listDTOs);
        //Act & Assert
        mockMvc.perform(get("http://localhost:8081/bugtracker").contentType(MediaType.APPLICATION_JSON).
                        content(TestUtil.convertObjectToJsonBytes(bugDO1))).
                andDo(print()).andExpect(status().isOk());

        Mockito.verify(bugTrackerService, times(1)).getAllBugs(any());

    }


    @Test
    public void testDeleteBug() throws Exception{

        //Arrange
        BugDO bugDO = TestUtil.createBugDO(1L);
        bugDO.setDeleted(true);
        BugDTO result = BugMapper.INSTANCE.makeDTO(bugDO);
        Mockito.when(bugTrackerService.deleteBug(any())).thenReturn(bugDO);
        when(mapper.makeDTO(any())).thenReturn(result);
        //Act & Assert
        mockMvc.perform(delete("http://localhost:8081/bugtracker/1").contentType(MediaType.APPLICATION_JSON)).
                andDo(print()).andExpect(status().isOk()).
                andExpect(jsonPath("$.deleted").exists()).
                andExpect(jsonPath("$.deleted").value(true));

        Mockito.verify(bugTrackerService, times(1)).deleteBug(any());
    }


    @Test
    public void testUpdateBug() throws Exception{

        //Arrange
        BugDO bugDO = TestUtil.createBugDO(1L);
        bugDO.setTitle("New Modified Title");

        BugDTO result = BugMapper.INSTANCE.makeDTO(bugDO);
        Mockito.when(bugTrackerService.updateBug(any(),any())).thenReturn(bugDO);
        when(mapper.makeDTO(any())).thenReturn(result);

        Map<String, Object> fields = new HashMap<>();
        fields.put("title","New Modified Title");

        //Act & Assert
        mockMvc.perform(patch("http://localhost:8081/bugtracker/1").contentType(MediaType.APPLICATION_JSON).
                        content(TestUtil.convertObjectToJsonBytes(fields))).
                andDo(print()).andExpect(status().isOk()).
                andExpect(jsonPath("$.title").exists()).
                andExpect(jsonPath("$.title").value("New Modified Title"));

        Mockito.verify(bugTrackerService, times(1)).updateBug(any(),any());

    }

}
