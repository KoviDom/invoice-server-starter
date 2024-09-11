package cz.itnetwork.entity.filter;

import lombok.Data;

@Data
public class InvoiceFilter {

    private Long buyerID;
    private Long sellerID;
    private String product;
    private Double minPrice;
    private Double maxPrice;
    private Integer limit = 10;

}
