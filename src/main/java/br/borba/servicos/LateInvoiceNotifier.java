package br.borba.servicos;

import br.borba.daos.EmailSender;
import br.borba.daos.InvoiceStorage;
import br.borba.entidades.Customer;

public class LateInvoiceNotifier {

    private final EmailSender emailSender;
    private final InvoiceStorage invoiceStorage;

    public LateInvoiceNotifier(final EmailSender emailSender, final InvoiceStorage invoiceStorage){
        this.emailSender = emailSender;
        this.invoiceStorage = invoiceStorage;
    }

    public void notifyIfLate(Customer customer)
    {
        if(invoiceStorage.hasOutstandingInvoice(customer)){
            emailSender.sendEmail(customer);
        }
    }
}
