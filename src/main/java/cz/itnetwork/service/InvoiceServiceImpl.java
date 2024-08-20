package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;

    // Metoda pro přidání nové faktury
    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity entity = invoiceMapper.toEntity(invoiceDTO);
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