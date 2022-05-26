package br.borba

import br.borba.daos.CustomerDAO
import br.borba.entidades.Customer
import org.apache.logging.log4j.core.Logger

import javax.persistence.EntityManager

class CustomerDaoSpec extends spock.lang.Specification{
    // Class to be tested
    private CustomerDAO customerDao

    // Dependencies (will be mocked)
    private EntityManager entityManager
    private Logger logger

    //Test data
    private Customer sampleCustomer

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    void setup(){
        customerDao = new CustomerDAO()

        entityManager = Stub(EntityManager.class)
        customerDao.setEntityManager(entityManager)

        logger = Mock(Logger.class)
        customerDao.setLogger(logger)
    }

    void "customer IDs are logged whenever they are saved in the DB"() {
        given: "a customer dao that assigns an ID to customer"
        entityManager.persist( _ as Customer) >> { Customer customer ->  customer.setId(123L)}

        when: "that customer is saved in the DB"
        customerDao.saveCustomer("Suzan", "Ivanova")

        then: "the ID is correctly logged"
        1 * logger.info("Saved customer with id {}", 123L)

    }
}
