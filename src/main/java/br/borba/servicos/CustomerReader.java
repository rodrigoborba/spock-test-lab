package br.borba.servicos;

import br.borba.entidades.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomerReader {

    @PersistenceContext
    private EntityManager entityManager;

    public String findFullName(Long customerID){
        Customer customer = entityManager.find(Customer.class, customerID);
        if(null != customer) {
            return customer.getFirstName() + " " + customer.getLastName();
        }
        return "";
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}