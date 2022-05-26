package br.borba

import br.borba.daos.EmailSender
import br.borba.daos.EventRecorder
import br.borba.daos.InvoiceStorage
import br.borba.entidades.Customer
import br.borba.entidades.Event
import br.borba.servicos.LateInvoiceNotifier

class EventCheckSpec extends spock.lang.Specification{
    //Class to be tested
    private LateInvoiceNotifier lateInvoiceNotifier

    //Dependencies (will be mocked)
    private EmailSender emailSender
    private InvoiceStorage invoiceStorage
    private EventRecorder eventRecorder

    //Test data
    private Customer sampleCustomer

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    void setup(){
        invoiceStorage = Stub(InvoiceStorage.class)
        emailSender = Mock(EmailSender.class)
        eventRecorder = Mock(EventRecorder.class)

        lateInvoiceNotifier = new LateInvoiceNotifier(emailSender,invoiceStorage,eventRecorder)
        sampleCustomer = new Customer()
        sampleCustomer.setFirstName("Susan")
        sampleCustomer.setLastName("Ivanova")
    }

    void "email about late invoice should contain customer details"() {
        given: "a customer with a late invoice"
        invoiceStorage.hasOutstandingInvoice(sampleCustomer) >> true

        when: "we check if an email should be sent"
        lateInvoiceNotifier.notifyIfLate(sampleCustomer)

        then: "the customer is indeed emailed"
        1 * emailSender.sendEmail(sampleCustomer)

        and: "the event is recorded with the respective details"
        1 * eventRecorder.recordEvent({
            event ->
                event.getTimestamp() != null &&
                        event.getType() == Event.Type.REMINDER_SENT &&
                        event.getCustomerName() == "Susan Ivanova"
        })
    }

}
