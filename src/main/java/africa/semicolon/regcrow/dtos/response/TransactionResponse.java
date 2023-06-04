package africa.semicolon.regcrow.dtos.response;

import africa.semicolon.regcrow.models.Status;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private Long sellerId;
    private Long buyerId;
    private String description;
    private Status status;
}
