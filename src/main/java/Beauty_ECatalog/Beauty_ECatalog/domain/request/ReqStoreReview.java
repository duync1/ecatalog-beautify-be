package Beauty_ECatalog.Beauty_ECatalog.domain.request;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqStoreReview {
    private String email;
    private double productQuality;
    private double serviceQuality;
    private double deliveryQuality;
    private Instant date;
    private String title;
    private String comment;
}
