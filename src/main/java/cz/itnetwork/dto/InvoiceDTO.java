package cz.itnetwork.dto;

import cz.itnetwork.entity.PersonEntity;
import java.time.LocalDate;

public class InvoiceDTO {

    private Long invoiceNumber;

    private PersonEntity seller;

    private PersonEntity buyer;

    private LocalDate issued;

    private LocalDate dueDate;

    private String product;

    private Double price;

    private int vat;

    private String note;

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public PersonEntity getSeller() {
        return seller;
    }

    public void setSeller(PersonEntity seller) {
        this.seller = seller;
    }

    public PersonEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(PersonEntity buyer) {
        this.buyer = buyer;
    }

    public LocalDate getIssued() {
        return issued;
    }

    public void setIssued(LocalDate issued) {
        this.issued = issued;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
