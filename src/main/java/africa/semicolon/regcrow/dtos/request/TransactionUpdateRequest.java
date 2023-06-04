package africa.semicolon.regcrow.dtos.request;

import africa.semicolon.regcrow.models.PaymentMethod;
import africa.semicolon.regcrow.models.Status;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionUpdateRequest {
    private Long transactionId;
    private Status status;
}
