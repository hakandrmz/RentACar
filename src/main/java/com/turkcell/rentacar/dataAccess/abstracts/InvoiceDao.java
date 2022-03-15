package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.entities.concretes.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceDao extends JpaRepository<Invoice,Integer> {
    List<Invoice> findByInvoiceDateBetween(LocalDate to,LocalDate before);
}
