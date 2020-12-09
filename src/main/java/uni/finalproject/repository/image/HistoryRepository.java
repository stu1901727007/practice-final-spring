package uni.finalproject.repository.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uni.finalproject.models.image.History;

public interface HistoryRepository extends JpaRepository<History, Long>, JpaSpecificationExecutor<History> {

    Page<History> findAllByOrderByIdDesc(Pageable pageable);
}
