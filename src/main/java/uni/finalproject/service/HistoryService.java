package uni.finalproject.service;

import uni.finalproject.models.image.History;

public interface HistoryService {

    History add(String q, String type, int agency, int yearStart, int yearEnd);
}
