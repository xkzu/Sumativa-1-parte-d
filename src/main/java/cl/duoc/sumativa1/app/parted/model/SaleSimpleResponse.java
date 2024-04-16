package cl.duoc.sumativa1.app.parted.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaleSimpleResponse {

    private String message;

    private Sale sale;
}
