package de.training.lowell.contractdemo.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  List<Customer> findByLastName(@Param("name") String name);

}