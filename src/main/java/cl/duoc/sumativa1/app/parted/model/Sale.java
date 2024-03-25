package cl.duoc.sumativa1.app.parted.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Sale {

    private int id;

    private String productName;

    private double amount;

    private LocalDateTime saleTime;
}
