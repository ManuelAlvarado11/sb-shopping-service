package alvarado.com.shoppingservice.service;

import alvarado.com.shoppingservice.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    public List<Invoice> getInvoiceAll();
    public Invoice getInvoice(Long id);
    public Invoice createInvoice(Invoice invoice);
    public Invoice updateInvoice(Invoice invoice);
    public Invoice deleteInvoice(Long id);


}
