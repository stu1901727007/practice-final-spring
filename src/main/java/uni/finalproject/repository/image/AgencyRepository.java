package uni.finalproject.repository.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uni.finalproject.models.image.Agency;


public interface AgencyRepository extends JpaRepository<Agency, Long> {

    /**
     *
     * @param pageable
     * @return
     */
    Page<Agency> findAll(Pageable pageable);

    /**
     *
     * @param pageable
     * @return
     */
    Page<Agency> findAllByOrderByNameAsc(Pageable pageable);
}
