package alvarado.com.shoppingservice.service;

import alvarado.com.shoppingservice.client.CustomerClient;
import alvarado.com.shoppingservice.client.ProductClient;
import alvarado.com.shoppingservice.entity.Invoice;
import alvarado.com.shoppingservice.entity.InvoiceItem;
import alvarado.com.shoppingservice.model.Customer;
import alvarado.com.shoppingservice.model.Product;
import alvarado.com.shoppingservice.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private CustomerClient customerClient;


    @Override
    public List<Invoice> getInvoiceAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if(invoice != null){
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);

            List<InvoiceItem> invoiceItems = invoice.getItems().stream().map( invoiceItem -> {
                Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
                invoiceItem.setProduct(product);
                return invoiceItem;
            }).collect(Collectors.toList());

            invoice.setItems(invoiceItems);
        }
        return invoice;
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = this.invoiceRepository.findBynumberInvoice(invoice.getNumberInvoice());
        if (invoiceDB != null) {
            return invoiceDB;
        }

        invoice.setState("CREATED");
        invoiceDB = invoiceRepository.save(invoice);

        invoiceDB.getItems().forEach( invoiceItem -> {
            productClient.updateStockProduct(invoiceItem.getProductId(), invoiceItem.getQuantity() * -1);
        });

        return invoiceDB;
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
