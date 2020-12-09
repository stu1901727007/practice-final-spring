package uni.finalproject.repository.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uni.finalproject.models.image.Agency;


public interface AgencyRepository extends JpaRepository<Agency, Long> {

    Page<Agency> findAll(Pageable pageable);
    Page<Agency> findAllByOrderByNameAsc(Pageable pageable);
}
