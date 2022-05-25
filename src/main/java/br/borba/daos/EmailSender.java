package br.borba.daos;

import br.borba.entidades.Customer;

public interface EmailSender {
    void sendEmail(Customer customer);
}
