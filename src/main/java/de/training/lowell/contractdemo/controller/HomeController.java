package de.training.lowell.contractdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.training.lowell.contractdemo.model.Contract;
import de.training.lowell.contractdemo.model.ContractRepository;
import de.training.lowell.contractdemo.model.Customer;
import de.training.lowell.contractdemo.model.CustomerContractRepository;
import de.training.lowell.contractdemo.model.CustomerRepository;


/**
 * HomeController serves the home management page.
 */
@Controller
@RequestMapping("/manage")
public class HomeController {

    @Autowired
    private ContractRepository contracts;

    @Autowired
    private CustomerRepository customers;

    @Autowired
    private CustomerContractRepository customerContracts;


    @GetMapping(value="")
    public String getMethodName(Model model) {
        model.addAttribute("contracts", contracts.findAll());
        model.addAttribute("customers", customers.findAll());
        model.addAttribute("customerContracts", customerContracts.findAll());
        return "manage.html";
    }

    @GetMapping(value="/testdata")
    public ResponseEntity<String> getMethodName() {
        customers.save(new Customer("Daniel", "Marohn"));
        customers.save(new Customer("Foo", "Bar"));

        contracts.save(new Contract("12 month beginners", 42, 12));
        contracts.save(new Contract("24 month experts", 60, 24));

        return new ResponseEntity<String>(HttpStatus.OK);
    }
    
}