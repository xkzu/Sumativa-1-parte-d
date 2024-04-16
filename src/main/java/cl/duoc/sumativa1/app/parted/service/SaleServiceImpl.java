package cl.duoc.sumativa1.app.parted.service;

import cl.duoc.sumativa1.app.parted.model.Sale;
import cl.duoc.sumativa1.app.parted.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
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
        LocalDateTime startOfDay = date.atStartOfDay(); // 00:00:00
        LocalDateTime endOfDay = date.atTime(23, 59, 59, 999999999); // 23:59:59.999999999
        return saleRepository.findSalesByDate(startOfDay, endOfDay);
    }

    @Override
    public List<Sale> getMonthlyEarnings(YearMonth yearMonth) {
        int month = yearMonth.getMonthValue();
        return saleRepository.findSalesByMonth(month);
    }

    @Override
    public List<Sale> getYearlyEarnings(Year year) {
        int yearInt = year.getValue();
        return saleRepository.findSalesByYear(yearInt);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public Sale addSale(Sale sale) {
        return saleRepository.save(sale);
    }

}
