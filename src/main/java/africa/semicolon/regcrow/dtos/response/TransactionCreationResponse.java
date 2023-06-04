package africa.semicolon.regcrow.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionCreationResponse {
    private Long id;
    private String message;
}
