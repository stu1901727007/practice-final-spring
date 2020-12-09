package uni.finalproject.http.requests.backoffice;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class ProfileFormRequest {

    @NotBlank
    @Size(min = 1, max = 40)
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 5, max = 13)
    private String mobileNumber;

//    @NotBlank
//    @Size(min = 5, max = 12)
    private String password;

    @NotBlank
    private String email;
}
