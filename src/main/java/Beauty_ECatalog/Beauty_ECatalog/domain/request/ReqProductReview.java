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
public class ReqProductReview {
    private long productId;
    private String email;
    private String reviewSummary;
    private String detailReview;
    private Instant date;
    private double rating;
}
