package uni.finalproject.service;

import uni.finalproject.models.image.History;

public interface HistoryService {

    /**
     *
     * @param q
     * @param type
     * @param agency
     * @param yearStart
     * @param yearEnd
     * @return
     */
    History add(String q, String type, int agency, int yearStart, int yearEnd);
}
