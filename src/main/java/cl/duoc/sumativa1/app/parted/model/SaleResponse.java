package cl.duoc.sumativa1.app.parted.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class SaleResponse {

    private String message;

    private List<Sale> sales;

    private Double totalEarnings;
}
