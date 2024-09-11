package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoices")
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {

        return invoiceService.addInvoice(invoiceDTO);

    }

    //i s filtraci
    @GetMapping("/invoices")
    public List<InvoiceDTO> getInvoices(InvoiceFilter invoiceFilter) {
        return invoiceService.getAllInvoices(invoiceFilter);
    }

    // Endpoint pro získání všech faktur vystavených konkrétní osobou (prodej) na základě IČO
    @GetMapping("/identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getAllSalesBySellerIdentification(@PathVariable String identificationNumber) {
        return invoiceService.getAllSalesBySellerIdentification(identificationNumber);
    }

    // Endpoint pro získání všech faktur přijatých konkrétní osobou (nákup) na základě IČO
    @GetMapping("/identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getAllPurchasesByBuyerIdentification(@PathVariable String identificationNumber) {
        return invoiceService.getAllPurchasesByBuyerIdentification(identificationNumber);
    }

    @DeleteMapping("/invoices/{invoiceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable Long invoiceId) {
        invoiceService.removeInvoice(invoiceId);
    }

    @GetMapping("/invoices/{invoiceId}")
    public InvoiceDTO getInvoiceById(@PathVariable Long invoiceId) {
        return invoiceService.getInvoiceById(invoiceId);
    }

    @PutMapping("/invoices/{invoiceId}")
    public InvoiceDTO updateInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.updateInvoice(invoiceId, invoiceDTO);
    }

    // Nový endpoint pro získání statistik
    @GetMapping("/invoices/statistics")
    public InvoiceStatisticsDTO getInvoiceStatistics() {
        return invoiceService.getInvoiceStatistics();
    }

}