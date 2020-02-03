package de.training.lowell.contractdemo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * CustomerTest
 */
@SpringBootTest
@AutoConfigureMockMvc
 public class CustomerContractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerContractRepository customerContractRepository;

    @Autowired 
	private ObjectMapper mapper;
    
    @Test
    public void testCreateContract() throws Exception
    {
        contractRepository.deleteAll();
        Contract contract = new Contract();
        contract.setDuration(10);
        contract.setName("integration test");
        contract.setPrice(17);

        mockMvc.perform(post("/contracts")
            .content(mapper.writeValueAsString(contract))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
        
        List<Contract> actuals = contractRepository.findByName("integration test");
        assertTrue(actuals.size() == 1);
        Contract actual = actuals.get(0);
        assertEquals(10, actual.getDuration());
        assertEquals(17, actual.getPrice());
    }

    @Test
    public void testDeleteContract() throws Exception
    {
        contractRepository.deleteAll();
        Contract contract = new Contract();
        contract.setDuration(10);
        contract.setName("integration test");
        contract.setPrice(17);
        contract = contractRepository.save(contract);
        mockMvc.perform(delete("/contracts/" + contract.getId()));
        assertEquals(0, contractRepository.count());
    }

    @Test
    public void testBookContract() throws Exception
    {
        contractRepository.deleteAll();
        customerContractRepository.deleteAll();
        Contract contract = new Contract("testcon", 1, 2);
        Customer customer = new Customer("first", "last");
        
        customer = customerRepository.save(customer);
        contract = contractRepository.save(contract);

        String content = String.format("{\"beginAt\": \"2010-01-01\",\"contract\": \"/contracts/%d\",\"customer\": \"customers/%d\"}", contract.getId(), customer.getId());
        CustomerContract newContract = new CustomerContract();
        newContract.setBeginAt(new Date());
        newContract.setCustomer(customer);
        newContract.setContract(contract);

        
        mockMvc.perform(post("/customerContracts")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
        
            CustomerContract actual = customerContractRepository.findAll().iterator().next();
            assertEquals(actual.getContract().getId(), contract.getId());
            assertEquals(actual.getCustomer().getId(), customer.getId());
    }


}