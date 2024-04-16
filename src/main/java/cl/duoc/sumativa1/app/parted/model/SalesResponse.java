package cl.duoc.sumativa1.app.parted.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SalesResponse {

    private String message;

    List<Sale> sales;
}
