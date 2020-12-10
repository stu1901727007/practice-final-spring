package uni.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uni.finalproject.models.image.Agency;
import uni.finalproject.models.image.History;
import uni.finalproject.repository.image.AgencyRepository;
import uni.finalproject.repository.image.HistoryRepository;

import java.util.Date;
import java.util.Optional;

@Component
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    /**
     *
     * @param q
     * @param type
     * @param center
     * @param yearStart
     * @param yearEnd
     * @return
     */
    public History add(String q, String type, int center, int yearStart, int yearEnd) {

        History history = new History();

        history.setSearchDate(new Date()).setQ(q)
                .setType(type);

        if( center > 0 ) {
            Optional<Agency> agency = agencyRepository.findById(Long.valueOf(center));

            if( agency.isPresent() )
            {
                history.setAgency(agency.get());
            }
        }

        if( yearStart > 0 )
        {
            history.setYearStart(yearStart);
        }

        if( yearEnd > 0 )
        {
            history.setYearEnd(yearEnd);
        }

        return historyRepository.saveAndFlush(history);
    }
}
