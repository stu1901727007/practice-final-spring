package uni.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uni.finalproject.http.requests.backoffice.LibraryFormRequest;
import uni.finalproject.models.image.History;
import uni.finalproject.models.image.Library;
import uni.finalproject.models.user.User;
import uni.finalproject.repository.image.HistoryRepository;
import uni.finalproject.repository.image.LibraryRepository;
import uni.finalproject.util.DateUtils;
import uni.finalproject.util.FileUploadUtil;

import java.io.IOException;
import java.util.Date;

@Component
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    /**
     *
     * @param q
     * @param type
     * @param agency
     * @param yearStart
     * @param yearEnd
     * @return
     */
    public History add(String q, String type, int agency, int yearStart, int yearEnd) {

        History history = new History();
        history.setSearchDate(new Date()).setQ(q).setType(type).setYearStart(yearStart).setYearEnd(yearEnd);

        return historyRepository.saveAndFlush(history);
    }
}
