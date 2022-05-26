package br.borba.servicos;

import br.borba.daos.EmailSender;
import br.borba.daos.EventRecorder;
import br.borba.daos.InvoiceStorage;
import br.borba.entidades.Customer;
import br.borba.entidades.Event;

import java.time.LocalDate;

public class LateInvoiceNotifier {

    private final EmailSender emailSender;
    private final InvoiceStorage invoiceStorage;

    private EventRecorder eventRecorder;

    public LateInvoiceNotifier(final EmailSender emailSender, final InvoiceStorage invoiceStorage){
        this.emailSender = emailSender;
        this.invoiceStorage = invoiceStorage;
    }

    public LateInvoiceNotifier(final EmailSender emailSender, final InvoiceStorage invoiceStorage, final EventRecorder eventRecorder){
        this.emailSender = emailSender;
        this.invoiceStorage = invoiceStorage;
        this.eventRecorder = eventRecorder;
    }

    public void notifyIfLate(Customer customer)
    {
        if(invoiceStorage.hasOutstandingInvoice(customer)){
            emailSender.sendEmail(customer);
            Event event = new Event();
            event.setTimestamp(LocalDate.now());
            event.setCustomerName(customer.getFullName());
            event.setType(Event.Type.REMINDER_SENT);
            eventRecorder.recordEvent(event);
        }
    }
}
