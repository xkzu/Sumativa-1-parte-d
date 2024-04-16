package cl.duoc.sumativa1.app.parted.controller;

import cl.duoc.sumativa1.app.parted.model.Sale;
import cl.duoc.sumativa1.app.parted.model.SaleResponse;
import cl.duoc.sumativa1.app.parted.model.SaleSimpleResponse;
import cl.duoc.sumativa1.app.parted.model.SalesResponse;
import cl.duoc.sumativa1.app.parted.service.SaleService;
import cl.duoc.sumativa1.app.parted.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        try {
            if (saleService.getDailyEarnings(date).isEmpty()) {
                return ResponseEntity.ofNullable(
                        new SaleResponse("No se encontraron ventas diarias", null, null));
            }
            //se obtienen las ventas desde el service
            List<Sale> dailySales = saleService.getDailyEarnings(date);

            //se calculan las ventas para dar un total ganado
            double totalEarnings = dailySales.stream()
                    .mapToDouble(Sale::getAmount)
                    .sum();

            //se retorna con respuesta 200
            return ResponseEntity.ok(new SaleResponse(Constant.SUCCESS, dailySales, totalEarnings));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new SaleResponse("Error al obtener las ganancias diarias " + e.getMessage(), null, null));
        }

    }

    @GetMapping("/earnings/monthly/{month}")
    public ResponseEntity<SaleResponse> getMonthlyEarnings(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        try {
            if (saleService.getMonthlyEarnings(month).isEmpty()) {
                return ResponseEntity.ofNullable(
                        new SaleResponse("No se encontraron ganancias mensuales", null, null));
            }
            List<Sale> monthlySales = saleService.getMonthlyEarnings(month);

            double totalEarnings = monthlySales.stream()
                    .mapToDouble(Sale::getAmount)
                    .sum();

            return ResponseEntity.ok(new SaleResponse(Constant.SUCCESS, monthlySales, totalEarnings));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new SaleResponse("Error al obtener ganancias mensuales " + e.getMessage(), null, null));
        }
    }

    @GetMapping("/earnings/yearly/{year}")
    public ResponseEntity<SaleResponse> getYearlyEarnings(
            @PathVariable @DateTimeFormat(pattern = "yyyy") Year year) {
        try {
            if (saleService.getYearlyEarnings(year).isEmpty()) {
                return ResponseEntity.ofNullable(
                        new SaleResponse("No se encontraron ventas", null, null));
            }
            List<Sale> yearlyEarnings = saleService.getYearlyEarnings(year);

            double totalEarnings = yearlyEarnings.stream()
                    .mapToDouble(Sale::getAmount)
                    .sum();

            return ResponseEntity.ok(new SaleResponse(Constant.SUCCESS, yearlyEarnings, totalEarnings));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new SaleResponse("Error al obtener ganancias anuales " + e.getMessage(), null, null));
        }

    }

    @GetMapping("/earnings")
    public ResponseEntity<SalesResponse> getEarnings() {
        try {
            if (saleService.getAllSales().isEmpty()) {
                return ResponseEntity.ofNullable(
                        new SalesResponse("No se encontraron ventas", null));
            }
            return ResponseEntity.ok(new SalesResponse(Constant.SUCCESS, saleService.getAllSales()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new SalesResponse("Error al obtener todas las ventas " + e.getMessage(), null));
        }

    }

    @PostMapping("/earnings/add")
    public ResponseEntity<SaleSimpleResponse> addSale(@RequestBody Sale sale) {
        try {
            if (sale.getAmount() <= 0 || sale.getProductName().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new SaleSimpleResponse(
                                "El monto debe ser mayor a cero y el nombre del producto no puede ser null ni vacio",
                                null));
            }
            return ResponseEntity.ok(new SaleSimpleResponse(Constant.SUCCESS, saleService.addSale(sale)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new SaleSimpleResponse("Error al ingresar venta " + e.getMessage(), null));
        }
    }

    @PutMapping("/earnings/update/{id}")
    public ResponseEntity<SaleSimpleResponse> updateSale(@PathVariable Long id, @RequestBody Sale sale) {
        try {
            if (id < 1) {
                return ResponseEntity.badRequest().body(
                        new SaleSimpleResponse("id no puede ser menor a 1", null));
            }
            if (saleService.getSaleById(id).isEmpty()) {
                return ResponseEntity.ofNullable(
                        new SaleSimpleResponse("El id ingresado no se encuentra en bd", null));
            }
            if (sale.getAmount() <= 0 || sale.getProductName().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new SaleSimpleResponse(
                                "El monto debe ser mayor a cero y el nombre del producto no puede ser null ni vacio",
                                null));
            }
            sale.setId(id);
            return ResponseEntity.ok(new SaleSimpleResponse(Constant.SUCCESS, saleService.updateSale(sale)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new SaleSimpleResponse("Error al actualizar venta " + e.getMessage(), null));
        }
    }
}
