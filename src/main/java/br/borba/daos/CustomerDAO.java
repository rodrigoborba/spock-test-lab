package br.borba.daos;

import br.borba.entidades.Customer;
import org.apache.logging.log4j.core.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class CustomerDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private Logger logger;

    public void saveCustomer(String firstName, String lastName) {
        if(firstName == null || lastName==null)
        {
            logger.error("Missing customer information");
            throw new IllegalArgumentException();
        }
        Customer customer = new Customer(firstName,lastName);
        entityManager.persist(customer);
        entityManager.flush();
        logger.info("Saved customer with id {}", customer.getId());
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
