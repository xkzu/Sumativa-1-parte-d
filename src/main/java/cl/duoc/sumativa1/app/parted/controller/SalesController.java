package cl.duoc.sumativa1.app.parted.controller;

import cl.duoc.sumativa1.app.parted.model.Sale;
import cl.duoc.sumativa1.app.parted.model.SaleResponse;
import cl.duoc.sumativa1.app.parted.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@RestController
public class SalesController {

    private final SaleService saleService;

    @Autowired
    public SalesController(SaleService saleService) {
        this.saleService = saleService;
    }

    /*
        Considerar un microservicio que permita manipular las ventas de una tienda deproductos
        para mascotas. Considerar las respectivas validaciones necesarias para losdatos que ingresaran.
        El microservicio debe ser capaz de sacar ganancias diarias,mensuales o anuales a petición de lo solicitado
    */

    @GetMapping("/earnings/daily/{date}")
    public ResponseEntity<SaleResponse> getDailyEarnings(
            //se le asigna el formato de la fecha que se requiere como argumento
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        //se obtienen las ventas desde el service
        List<Sale> dailySales = saleService.getDailyEarnings(date);

        //se calculan las ventas para dar un total ganado
        double totalEarnings = dailySales.stream()
                .mapToDouble(Sale::getAmount)
                .sum();

        //se retorna con respuesta 200
        return ResponseEntity.ok(new SaleResponse(dailySales, totalEarnings));
    }

    @GetMapping("/earnings/monthly/{month}")
    public ResponseEntity<SaleResponse> getMonthlyEarnings(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {

        List<Sale> monthlySales = saleService.getMonthlyEarnings(month);

        double totalEarnings = monthlySales.stream()
                .mapToDouble(Sale::getAmount)
                .sum();

        return ResponseEntity.ok(new SaleResponse(monthlySales, totalEarnings));
    }

    @GetMapping("/earnings/yearly/{year}")
    public ResponseEntity<SaleResponse> getYearlyEarnings(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") Year year) {

        List<Sale> monthlySales = saleService.getYearlyEarnings(year);

        double totalEarnings = monthlySales.stream()
                .mapToDouble(Sale::getAmount)
                .sum();

        return ResponseEntity.ok(new SaleResponse(monthlySales, totalEarnings));
    }

    @GetMapping("/earnings")
    public ResponseEntity<List<Sale>> getEarnings() {
        return ResponseEntity.ok(saleService.getAllSales());
    }
}
