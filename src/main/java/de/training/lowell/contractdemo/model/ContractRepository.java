package de.training.lowell.contractdemo.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContractRepository extends CrudRepository<Contract, Long> {

  List<Contract> findByName(@Param("name") String name);

}