package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long>, JpaSpecificationExecutor<InvoiceEntity> {

    // Hledání faktur podle IČO prodávajícího (seller)
    List<InvoiceEntity> findBySellerIdentificationNumber(String identificationNumber);

    // Hledání faktur podle IČO kupujícího (buyer)
    List<InvoiceEntity> findByBuyerIdentificationNumber(String identificationNumber);

}
