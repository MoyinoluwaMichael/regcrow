package africa.semicolon.regcrow.dtos.request;

import africa.semicolon.regcrow.models.Payment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class TransactionCreationRequest {
    private Long sellerId;
    private Long buyerId;
    private String description;
    private BigDecimal amount;
}
