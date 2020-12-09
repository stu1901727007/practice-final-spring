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

    Page<Library> findAll(Pageable pageable);
    Page<Library> findAllByOrderByTitleAsc(Pageable pageable);

//    @Query(value="SELECT * FROM library ORDER BY RAND() LIMIT 15", nativeQuery=true)
//    Library fetchRandomHome();

    @Query(value="SELECT MIN(YEAR(PUBLICATION_DATE)) as min,MAX(YEAR(PUBLICATION_DATE)) as max  FROM LIBRARY", nativeQuery=true)
    Map<String, Integer> getRangeYear();

    ArrayList<Library> findTop10ByOrderByIdAsc();
}
