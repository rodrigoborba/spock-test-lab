package br.borba.servicos;

import br.borba.daos.EventRecorder;
import br.borba.daos.UserRepository;
import br.borba.entidades.Customer;
import br.borba.entidades.Event;
import br.borba.exceptions.ValidationException;

import java.util.List;

public class MassUserRegistration {

    private final EventRecorder eventRecorder;
    private final UserRepository userRepository;

    public MassUserRegistration(final EventRecorder eventRecorder,
                                final UserRepository userRepository) {
        this.eventRecorder = eventRecorder;
        this.userRepository = userRepository;
    }

    private void register(String firstName, String lastName) throws ValidationException {
        if(null == firstName || null == lastName){
            throw new ValidationException("Campos obrigatórios não podem ser nulos");
        }
        Customer newCustomer = userRepository.saveCustomer(firstName, lastName);

        Event event = new Event();
        event.setTimestamp(newCustomer.getSince());
        event.setCustomerName(newCustomer.getFullName());
        event.setType(Event.Type.REGISTRATION);
        eventRecorder.recordEvent(event);
    }

    public void massRegister(List<Customer> rawCustomerNames) throws ValidationException {
        for (Customer customer:rawCustomerNames) {
            register(customer.getFirstName(),customer.getLastName());
        }
    }
}
