package uni.finalproject.http.requests.backoffice;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class AgencyFormRequest {

    private Long agencyId;

    @NotBlank
    @Size(min = 2, max = 10)
    private String agencyCode;

    @NotBlank
    @Size(min = 2, max = 200)
    private String agencyName;

    @NotBlank
    @Size(max = 500)
    private String agencyDetails;

    @Column(columnDefinition="tinyint(1) default 0")
    private Boolean agencyDeleted;

}
