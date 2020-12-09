package uni.finalproject.http.requests.backoffice;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import uni.finalproject.models.image.Agency;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
//@Conditional(selected = "libraryType", values = {"audio", "video"}, required = {"libraryEmbed"})
public class LibraryFormRequest {

    private Long libraryId;

    @NotBlank
    private String libraryType;

    @NotBlank
    @Size(max = 500)
    private String libraryTitle;

    @NotBlank
    private String libraryResume;

    @NotBlank
    private String libraryText;

    @NotBlank
    @Size(max = 200)
    private String libraryPhotographer;

    //@NotBlank
    @Size(max = 1000)
    private String libraryEmbed;

    @Column(length = 200)
    private String libraryFile;

    @Column(columnDefinition="tinyint(1) default 0")
    private Boolean libraryDeleted;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String libraryPublicationDate;

    @NotNull
    private Agency libraryAgency;

    //@Transient
    public String getPhotosImagePath() {

        if ( libraryFile == null || libraryId == null)
            return null;

        return "/uploads/media/" + libraryId + "/" + libraryFile;
    }
}
