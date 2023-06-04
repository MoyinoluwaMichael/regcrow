package africa.semicolon.regcrow.dtos.request;


import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegistrationRequest {
    private String email;
    private String password;
}
