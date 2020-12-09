package uni.finalproject.repository.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uni.finalproject.models.image.Library;

import java.util.ArrayList;
import java.util.Map;

public interface LibraryRepository extends JpaRepository<Library, Long>, JpaSpecificationExecutor<Library> {

    /**
     *
     * @param pageable
     * @return
     */
    Page<Library> findAll(Pageable pageable);

    /**
     *
     * @param pageable
     * @return
     */
    Page<Library> findAllByOrderByTitleAsc(Pageable pageable);

    /**
     *
     * @return
     */
    @Query(value="SELECT MIN(YEAR(PUBLICATION_DATE)) as min,MAX(YEAR(PUBLICATION_DATE)) as max  FROM LIBRARY", nativeQuery=true)
    Map<String, Integer> getRangeYear();

    /**
     * 
     * @return
     */
    ArrayList<Library> findTop10ByOrderByIdAsc();
}
