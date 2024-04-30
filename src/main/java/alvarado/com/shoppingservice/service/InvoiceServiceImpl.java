package alvarado.com.shoppingservice.service;

import alvarado.com.shoppingservice.entity.Invoice;
import alvarado.com.shoppingservice.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> getInvoiceAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoice(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = this.invoiceRepository.findBynumberInvoice(invoice.getNumberInvoice());
        if (invoiceDB != null) {
            return invoiceDB;
        }

        invoice.setState("CREATED");
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = this.invoiceRepository.getById(invoice.getId());
        if (invoiceDB == null) {
            return null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());

        return this.invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice deleteInvoice(Long id) {
        Invoice invoice = this.invoiceRepository.getById(id);
        if (invoice == null) {
            return null;
        }

        invoice.setState("DELETED");
        return this.invoiceRepository.save(invoice);
    }

}
