package stream;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private Integer id;
    private Integer productCount;
    private Double totalAmount;

}
