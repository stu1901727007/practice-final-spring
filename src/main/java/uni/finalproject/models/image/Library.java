package uni.finalproject.models.image;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties({"deleted"})
@Entity
@Table(name = "library")
@SQLDelete(sql = "UPDATE library SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Library implements Serializable {

    @Id
    @Column(name = "media_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd H:m:s")
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd H:m:s")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd H:m:s")
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd H:m:s")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(length = 500)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String resume;

    @Column(columnDefinition = "LONGTEXT")
    private String text;

    @Column(length = 200)
    private String photographer;

    @Column(length = 1000)
    private String embed;

    @Column(length = 200)
    private String file;

    @Column(columnDefinition="tinyint(1) default 0")
    private Boolean deleted;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @Transient
    public String getPhotosImagePath() {
        if ( file == null || id == null) return null;

        return "/uploads/media/" + id + "/" + file;
    }
}
