package cl.duoc.sumativa1.app.parted.service;

import cl.duoc.sumativa1.app.parted.model.Sale;
import cl.duoc.sumativa1.app.parted.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> getDailyEarnings(LocalDate date) {
        //se crea una lista y se almacenan las ventas desde nuestra bd en memoria
        List<Sale> allSales = getSales();

        //se convierte la lista en un stream, luego se aplica un filtro en caso de que la fecha sea correcta
        //y finalmente lo convierte en lista de nuevo
        return allSales.stream()
                .filter(sale -> sale.getSaleTime().toLocalDate().isEqual(date))
                .toList();
    }

    @Override
    public List<Sale> getMonthlyEarnings(YearMonth yearMonth) {
        return getSales().stream()
                .filter(sale -> YearMonth.from(sale.getSaleTime()).equals(yearMonth))
                .toList();
    }

    @Override
    public List<Sale> getYearlyEarnings(Year year) {
        return getSales().stream()
                .filter(sale -> Year.from(sale.getSaleTime()).equals(year))
                .toList();
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    private List<Sale> getSales() {
        return Arrays.asList(
                new Sale(1, "Catnip Toy", 9.99, LocalDateTime.of(
                        2024, 3, 20, 14, 30)),
                new Sale(2, "Scratching Post", 25.95, LocalDateTime.of(
                        2024, 3, 21, 10, 0)),
                new Sale(3, "Automatic Feeder", 59.99, LocalDateTime.of(
                        2024, 3, 22, 16, 45)),
                new Sale(4, "Litter Box", 15.50, LocalDateTime.of(
                        2024, 4, 1, 11, 20)),
                new Sale(5, "Collar with Bell", 4.99, LocalDateTime.of(
                        2024, 4, 1, 12, 30)),
                new Sale(6, "Gourmet Cat Food, Chicken Flavor", 19.99, LocalDateTime.of(
                        2024, 4, 2, 9, 15))
        );
    }
}
