package cl.duoc.sumativa1.app.parted.repository;

import cl.duoc.sumativa1.app.parted.model.Sale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SaleRepositoryTest {

    private final SaleRepository saleRepository;

    @Autowired
    SaleRepositoryTest(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Test
    void findSalesByYear() {
        List<Sale> sales = saleRepository.findSalesByYear(2024);
        assertNotNull(sales);
        assertFalse(sales.isEmpty());
    }

    @Test
    void findSalesByMonth() {
        List<Sale> sales = saleRepository.findSalesByMonth(4);
        assertNotNull(sales);
        assertFalse(sales.isEmpty());
    }

    @Test
    void findSalesByDate() {
        LocalDate date = LocalDate.of(2024, 4, 10); // 10 de abril de 2024

        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);

        List<Sale> sales = saleRepository.findSalesByDate(startOfDay, endOfDay);

        assertNotNull(sales);
        assertFalse(sales.isEmpty());
    }

    @Test
    void getAllSales() {
        List<Sale> sales = saleRepository.findAll();
        assertNotNull(sales);
        assertFalse(sales.isEmpty());
    }

    @Test
    void getSaleById() {
    Optional<Sale> sale = saleRepository.findById(1L);
    assertNotNull(sale);
    assertFalse(sale.isEmpty());
    }

}