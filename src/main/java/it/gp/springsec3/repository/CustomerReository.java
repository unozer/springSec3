package it.gp.springsec3.repository;

import it.gp.springsec3.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerReository extends CrudRepository<Customer, Long> {

    public Customer findCustomerByEmail(String email);
}
