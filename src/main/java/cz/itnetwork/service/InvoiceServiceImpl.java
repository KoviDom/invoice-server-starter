package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    // Metoda pro přidání nové faktury
    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        // Načti kompletní entitu Seller a Buyer na základě jejich ID
        PersonEntity sellerEntity = personRepository.findById(invoiceDTO.getSeller().getId())
                .orElseThrow(() -> new NotFoundException("Seller not found"));
        PersonEntity buyerEntity = personRepository.findById(invoiceDTO.getBuyer().getId())
                .orElseThrow(() -> new NotFoundException("Buyer not found"));

        // Vytvoř novou entitu faktury a nastav seller a buyer entity
        InvoiceEntity entity = invoiceMapper.toEntity(invoiceDTO);
        entity.setSeller(sellerEntity);
        entity.setBuyer(buyerEntity);

        entity = invoiceRepository.save(entity);

        return invoiceMapper.toDTO(entity);
    }

    // Metoda pro získání faktury podle ID
    @Override
    public InvoiceDTO getInvoiceById(long id) {
//        InvoiceEntity entity = fetchInvoiceById(id);
//        return invoiceMapper.toDTO(entity);
        return invoiceMapper.toDTO(fetchInvoiceById(id));
    }

    // Metoda pro získání všech faktur
    @Override
    public List<InvoiceDTO> getAll() {
        return invoiceRepository.findAll()
                .stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Metoda pro získání všech faktur vystavených konkrétní osobou na základě IČO
    @Override
    public List<InvoiceDTO> getAllSalesBySellerIdentification(String identificationNumber) {
        return invoiceRepository.findBySellerIdentificationNumber(identificationNumber)
                .stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Metoda pro získání všech faktur přijatých konkrétní osobou na základě IČO
    @Override
    public List<InvoiceDTO> getAllPurchasesByBuyerIdentification(String identificationNumber) {
        return invoiceRepository.findByBuyerIdentificationNumber(identificationNumber)
                .stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    //podivat se jeste na tohle, jelikoz u PUT mi to dava buyer a seller null hodnoty
    @Override
    public InvoiceDTO updateInvoice(long id, InvoiceDTO invoiceDTO) {
        // Najdi existující fakturu podle ID
        InvoiceEntity existingInvoice = fetchInvoiceById(id);

        // Načti kompletní entitu Seller a Buyer na základě jejich ID
        PersonEntity sellerEntity = personRepository.findById(invoiceDTO.getSeller().getId())
                .orElseThrow(() -> new NotFoundException("Seller not found"));
        PersonEntity buyerEntity = personRepository.findById(invoiceDTO.getBuyer().getId())
                .orElseThrow(() -> new NotFoundException("Buyer not found"));

        // Nastavení těchto objektů do faktury
        existingInvoice.setSeller(sellerEntity);
        existingInvoice.setBuyer(buyerEntity);

        // Aktualizace existující faktury s novými daty
        existingInvoice.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        existingInvoice.setIssued(invoiceDTO.getIssued());
        existingInvoice.setDueDate(invoiceDTO.getDueDate());
        existingInvoice.setProduct(invoiceDTO.getProduct());
        existingInvoice.setPrice(invoiceDTO.getPrice());
        existingInvoice.setVat(invoiceDTO.getVat());
        existingInvoice.setNote(invoiceDTO.getNote());

        // Uložení aktualizované faktury do databáze
        existingInvoice = invoiceRepository.save(existingInvoice);

        // Vrácení aktualizovaného DTO
        return invoiceMapper.toDTO(existingInvoice);
    }

    @Override
    public InvoiceStatisticsDTO getInvoiceStatistics() {

        // Aktuální rok
        int currentYear = LocalDate.now().getYear();

        // Všechny faktury
        List<InvoiceEntity> allInvoices = invoiceRepository.findAll();

        // Výpočet statistik
        long currentYearSum = allInvoices.stream()
                .filter(invoice -> invoice.getIssued().getYear() == currentYear)
                .mapToLong(InvoiceEntity::getPrice)
                .sum();
        long allTimeSum = allInvoices.stream()
                .mapToLong(InvoiceEntity::getPrice)
                .sum();
        int invoicesCount = allInvoices.size();

        return new InvoiceStatisticsDTO(currentYearSum, allTimeSum, invoicesCount);
    }

    @Override
    public List<InvoiceDTO> getFilteredInvoices(Long buyerId, Long sellerId, String product, Long minPrice, Long maxPrice, Integer limit) {
        // Vytvoření specifikace pro filtrování
        Specification<InvoiceEntity> specification = Specification.where(null);

        // Přidání filtračních kritérií
        specification = specification
                .and(InvoiceSpecification.hasBuyer(buyerId))
                .and(InvoiceSpecification.hasSeller(sellerId))
                .and(InvoiceSpecification.hasProduct(product))
                .and(InvoiceSpecification.hasMinPrice(minPrice))
                .and(InvoiceSpecification.hasMaxPrice(maxPrice));

        // Dotazování s limitem
        List<InvoiceEntity> invoices;
        if (limit != null) {
            invoices = invoiceRepository.findAll(specification, PageRequest.of(0, limit)).getContent();
        } else {
            invoices = invoiceRepository.findAll(specification);
        }

        // Mapování entity na DTO
        return invoices.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Metoda pro odstranění faktury
    @Override
    public void removeInvoice(long id) {
        InvoiceEntity entity = fetchInvoiceById(id);
        invoiceRepository.delete(entity);
    }

    // region: Private methods

    /**
     * Attempts to fetch an invoice by ID.
     * Throws a {@link org.webjars.NotFoundException} if the invoice is not found.
     *
     * @param id Invoice ID to fetch
     * @return Fetched entity
     * @throws org.webjars.NotFoundException if the invoice is not found
     */
    //Pomocná metoda pro vyhledání faktury v databázi a vyhození výjimky, pokud není nalezena.
    private InvoiceEntity fetchInvoiceById(long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice with id " + id + " wasn't found in the database."));
    }

    // endregion
}