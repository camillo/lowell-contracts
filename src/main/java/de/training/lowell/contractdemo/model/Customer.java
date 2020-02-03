package de.training.lowell.contractdemo.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Customers can book contracts
 */
@Entity
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;

    public Customer() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Customer(String firstName, String lastName) {
        this();
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }
    public String fullName(){
        return String.format("%s %s", this.firstName, this.lastName);
    }

    @OneToMany(mappedBy = "customer")
    private Set<CustomerContract> contracts;

    @Override
    public String toString() {
        return this.fullName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<CustomerContract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<CustomerContract> contracts) {
        this.contracts = contracts;
    }
 
    public void bookContract(Contract contract) {
        CustomerContract cc = new CustomerContract();
        cc.setContract(contract);
        cc.setCustomer(this);
        cc.setBeginAt(new Date());
        
    }
}