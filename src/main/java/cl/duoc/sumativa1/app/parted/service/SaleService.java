package cl.duoc.sumativa1.app.parted.service;

import cl.duoc.sumativa1.app.parted.model.Sale;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

public interface SaleService {

    List<Sale> getDailyEarnings(LocalDate date);

    List<Sale> getMonthlyEarnings(YearMonth yearMonth);

    List<Sale> getYearlyEarnings(Year year);

    List<Sale> getAllSales();

    Sale addSale(Sale sale);
}
