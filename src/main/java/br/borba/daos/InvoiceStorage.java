package br.borba.daos;

import br.borba.entidades.Customer;

public interface InvoiceStorage {
    boolean hasOutstandingInvoice(Customer customer);
}
