package uni.finalproject.repository.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uni.finalproject.models.image.Library;

import java.util.ArrayList;

public interface LibraryRepository extends JpaRepository<Library, Long>, JpaSpecificationExecutor<Library> {

    Page<Library> findAll(Pageable pageable);
    Page<Library> findAllByOrderByTitleAsc(Pageable pageable);

//    @Query(value="SELECT * FROM library ORDER BY RAND() LIMIT 15", nativeQuery=true)
//    Library fetchRandomHome();

    ArrayList<Library> findTop10ByOrderByIdAsc();
}
