package de.training.lowell.contractdemo.model;

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

/**
 * CustomerTest
 */
@SpringBootTest
@AutoConfigureMockMvc
 public class CustomerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository repository;

    @Autowired 
	private ObjectMapper mapper;
    
    @Test
    public void fullNameTest(){
        Customer customer = new Customer();
        customer.setFirstName("foo");
        customer.setLastName("bar");
        String actual = customer.fullName();
        assertTrue(actual.contains("foo"));
        assertTrue(actual.contains("bar"));
    }

    @Test
    public void testCreateCustomer() throws Exception
    {
        repository.deleteAll();
        List<Customer> actual = repository.findByLastName("bar");
        assertTrue(actual.size() == 0);

        mockMvc.perform(post("/customers")
            .content(mapper.writeValueAsString(new Customer("foo","bar")))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
        
            actual = repository.findByLastName("bar");
            assertTrue(actual.size() == 1);
    }


}