package com.example.restfulbankservice.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findTopByOrderByIdDesc();
    List<Account> findAll();
    Optional<Account> findByNumber(Long number);

}
