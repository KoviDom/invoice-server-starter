package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.domain.Specification;

public class InvoiceSpecification {

    public static Specification<InvoiceEntity> hasBuyer(Long buyerID) {
        return (root, query, criteriaBuilder) ->
                buyerID == null ? null : criteriaBuilder.equal(root.get("buyer").get("id"), buyerID);
    }

    public static Specification<InvoiceEntity> hasSeller(Long sellerID) {
        return (root, query, criteriaBuilder) ->
                sellerID == null ? null : criteriaBuilder.equal(root.get("seller").get("id"), sellerID);
    }

    public static Specification<InvoiceEntity> hasProduct(String product) {
        return (root, query, criteriaBuilder) ->
                (product == null || product.isEmpty()) ? null : criteriaBuilder.like(root.get("product"), "%" + product + "%");
    }

    public static Specification<InvoiceEntity> hasMinPrice(Long minPrice) {
        return (root, query, criteriaBuilder) ->
                minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<InvoiceEntity> hasMaxPrice(Long maxPrice) {
        return (root, query, criteriaBuilder) ->
                maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

}
