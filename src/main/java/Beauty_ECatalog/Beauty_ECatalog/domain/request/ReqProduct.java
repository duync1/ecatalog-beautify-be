package Beauty_ECatalog.Beauty_ECatalog.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqProduct {
    private String productName;
    private int quantity;
}
