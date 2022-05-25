package br.borba.daos;

import br.borba.entidades.Customer;

public interface UserRepository {
    Customer saveCustomer(String firstName, String lastName);
}
