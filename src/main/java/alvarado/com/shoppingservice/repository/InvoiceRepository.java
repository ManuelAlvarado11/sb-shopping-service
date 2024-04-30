package alvarado.com.shoppingservice.repository;

import alvarado.com.shoppingservice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    public List<Invoice> findByCustomerId(Long customerId);
    public Invoice findBynumberInvoice(String numberInvoice);
}
