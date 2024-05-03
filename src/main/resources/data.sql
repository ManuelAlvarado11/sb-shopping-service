INSERT INTO tbl_invoices (id, number_invoice, description, customer_id, create_at, state) VALUES(7, '0001', 'invoice office items', 2, NOW(),'CREATED');

INSERT INTO tbl_invoice_items ( invoice_id, product_id, quantity, price ) VALUES(7, 4 , 1, 178.89);
INSERT INTO tbl_invoice_items ( invoice_id, product_id, quantity, price)  VALUES(7, 5 , 2, 12.5);
INSERT INTO tbl_invoice_items ( invoice_id, product_id, quantity, price)  VALUES(7, 6 , 1, 40.06);
