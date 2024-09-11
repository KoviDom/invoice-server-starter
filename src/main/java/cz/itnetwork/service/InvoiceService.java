package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;

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
    List<InvoiceDTO> getAllInvoices(InvoiceFilter invoiceFilter);

    /**
     * Deletes an invoice by its ID
     *
     * @param id Invoice ID to delete
     */

    void removeInvoice(long id);

    // Deklarace nových metod
    List<InvoiceDTO> getAllSalesBySellerIdentification(String identificationNumber);

    List<InvoiceDTO> getAllPurchasesByBuyerIdentification(String identificationNumber);

    InvoiceDTO updateInvoice(long id, InvoiceDTO invoiceDTO);

    InvoiceStatisticsDTO getInvoiceStatistics();

    /**
     * Fetches all invoices based on filter parameters.
     *
     * @param buyerId Optional buyer ID for filtering.
     * @param sellerId Optional seller ID for filtering.
     * @param product Optional product name for filtering.
     * @param minPrice Optional minimum price for filtering.
     * @param maxPrice Optional maximum price for filtering.
     * @param limit Optional limit for the number of invoices returned.
     * @return List of filtered invoices.
     */

}