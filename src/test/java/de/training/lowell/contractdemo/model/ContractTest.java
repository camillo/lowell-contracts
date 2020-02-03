package de.training.lowell.contractdemo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
 public class ContractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContractRepository repository;

    @Autowired 
	private ObjectMapper mapper;
    
    @Test
    public void testCreateContract() throws Exception
    {
        repository.deleteAll();
        Contract contract = new Contract();
        contract.setDuration(10);
        contract.setName("integration test");
        contract.setPrice(17);

        mockMvc.perform(post("/contracts")
            .content(mapper.writeValueAsString(contract))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
        
        List<Contract> actuals = repository.findByName("integration test");
        assertTrue(actuals.size() == 1);
        Contract actual = actuals.get(0);
        
        assertEquals(10, actual.getDuration());
        assertEquals(17, actual.getPrice());
    }


}