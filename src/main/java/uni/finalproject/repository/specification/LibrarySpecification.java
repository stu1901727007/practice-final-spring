package uni.finalproject.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import uni.finalproject.models.image.Library;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LibrarySpecification {

    /**
     *
     * @param q
     * @return
     */
    public static Specification<Library> withTitle(String q) {
        if (q == null) {
            return null;
        } else {

            return (root, query, cb) -> cb.like(cb.upper(root.get("title")), "%" + q.toUpperCase() + "%");
        }
    }

    /**
     *
     * @param q
     * @return
     */
    public static Specification<Library> withText(String q) {
        if (q == null) {
            return null;
        } else {

            return (root, query, cb) -> cb.like(cb.upper(root.get("text")), "%" + q.toUpperCase() + "%");
        }
    }

    /**
     *
     * @param agency
     * @return
     */
    public static Specification<Library> withАgency(int agency) {
        if (agency <= 0) {
            return null;
        } else {

            return (root, query, cb) -> cb.equal(root.get("agencyId"), agency);
        }
    }

    /**
     *
     * @param media_type
     * @return
     */
    public static Specification<Library> withType(String media_type) {

        if (media_type == null) {
            return null;
        } else {
            List<String> listTypes = Arrays.asList(media_type.split(","));

            return (root, query, cb) -> cb.in(root.get("type")).value(listTypes);
        }
    }

    /**
     *
     * @param year_start
     * @param year_end
     * @return
     */
    public static Specification<Library> withYear(int year_start, int year_end) {
        if (year_start <= 0 || year_end <= 0) {
            return null;
        } else {

            try {
                Date startYear = new SimpleDateFormat("yyyy-MM-dd").parse(year_start + "-01-01");
                Date endYear = new SimpleDateFormat("yyyy-MM-dd").parse(year_end + "-12-31");

                return (root, query, cb) -> cb.between(root.get("publicationDate"), startYear, endYear);
            }catch (Exception e){
                return null;
            }
        }
    }
}