package de.training.lowell.contractdemo.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerContractRepository extends CrudRepository<CustomerContract, Long> {

  List<Contract> findByCustomer(@Param("customer") long lastName);

}