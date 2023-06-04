package africa.semicolon.regcrow.dtos.request;

import africa.semicolon.regcrow.models.BioData;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerUpdateRequest {
    private Long customerId;
    private String firstname;
    private String lastname;

}
