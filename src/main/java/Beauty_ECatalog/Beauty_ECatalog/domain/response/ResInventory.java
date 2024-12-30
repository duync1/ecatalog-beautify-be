package Beauty_ECatalog.Beauty_ECatalog.domain.response;

import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResInventory {
    private Product product;
    private int beginningInventory;
    private int totalImported;
    private int totalSold;
    private int endingInventory;
}
