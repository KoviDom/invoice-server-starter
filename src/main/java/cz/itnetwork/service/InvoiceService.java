package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {

    /**
     * Creates a new invoice
     *
     * @param invoiceDTO Invoice to create
     * @return newly created invoice
     */
    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    /**
     * Fetches an invoice by its ID
     *
     * @param id Invoice ID to fetch
     * @return Invoice with the specified ID
     */
    InvoiceDTO getInvoiceById(long id);

    /**
     * Fetches all invoices
     *
     * @return List of all invoices
     */
    List<InvoiceDTO> getAll();

    /**
     * Deletes an invoice by its ID
     *
     * @param id Invoice ID to delete
     */

    void removeInvoice(long id);

}
