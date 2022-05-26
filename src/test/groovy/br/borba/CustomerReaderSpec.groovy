package br.borba

import br.borba.entidades.Customer
import br.borba.servicos.CustomerReader

import javax.persistence.EntityManager

public class CustomerReaderSpec extends spock.lang.Specification{

    public void "customer full name is formed from first name and last name"() {
        given: "a customer with example name values"
        Customer sampleCustomer = new Customer()
        sampleCustomer.setFirstName("Susan")
        sampleCustomer.setLastName("Ivanova")

        and: "an entity manager that always returns this customer"
        EntityManager entityManager = Stub(EntityManager.class)
        entityManager.find(Customer.class,1L) >> sampleCustomer

        and: "a customer reader which is the class under test"
        CustomerReader customerReader = new CustomerReader()
        customerReader.setEntityManager(entityManager)

        when: "we ask for the full name of the customer"
        String fullName = customerReader.findFullName(1L)

        then: "we get both the first and the last name"
        fullName == "Susan Ivanova"
    }

}
