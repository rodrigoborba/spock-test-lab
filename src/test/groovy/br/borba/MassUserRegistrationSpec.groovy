package br.borba

import br.borba.daos.EventRecorder
import br.borba.daos.UserRepository
import br.borba.entidades.Customer
import br.borba.entidades.Event
import br.borba.servicos.MassUserRegistration

import java.time.LocalDate

class MassUserRegistrationSpec extends spock.lang.Specification{
    //Class under test
    private MassUserRegistration massUserRegistration

    //Dependencies (will be mocked)
    private UserRepository userRepository
    private EventRecorder eventRecorder

    //Test data
    private List sampleCustomers

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    void setup(){
        sampleCustomers = new ArrayList<>()

        eventRecorder = Mock(EventRecorder.class)
        userRepository = Stub(UserRepository.class)

        userRepository.saveCustomer( _ as String, _ as String) >> { String firstName, String lastName ->
            Customer newCustomer = new Customer(firstName, lastName)
            newCustomer.setFullName(firstName + " " + lastName)
            newCustomer.setSince(LocalDate.now())
            return newCustomer
        }

        massUserRegistration = new MassUserRegistration(eventRecorder,userRepository)
    }

    void "mass registration of users"() {
        given: "a list of sample Customers"
        sampleCustomers.add(new Customer("Susan", "Ivanova"))
        sampleCustomers.add(new Customer("Lyta", "Alexander"))
        sampleCustomers.add(new Customer("Vir", "Cotto"))
        sampleCustomers.add(new Customer("Stephen", "Frankling"))
        //[...20 customers redacted for brevity...]

        when: "we register all customers at once"
        massUserRegistration.massRegister(sampleCustomers)

        then: "each registration event contains the correct customer details"
        sampleCustomers.each { sampleCustomer ->
            1 * eventRecorder.recordEvent({ event ->
                event.getTimestamp() != null &&
                        event.getType() == Event.Type.REGISTRATION &&
                        event.getCustomerName() == sampleCustomer.getFirstName() + " "+ sampleCustomer.getLastName()
            })
        }


    }
}
