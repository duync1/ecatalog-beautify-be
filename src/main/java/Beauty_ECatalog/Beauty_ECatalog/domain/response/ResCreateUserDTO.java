package Beauty_ECatalog.Beauty_ECatalog.domain.response;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ResCreateUserDTO {
    private long id;
    private String name;
    private String email;
    private Instant birthDay;
    private String gender;
    private String address;
    private Instant createdAt;
}
