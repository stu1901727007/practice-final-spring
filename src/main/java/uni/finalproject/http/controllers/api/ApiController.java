package uni.finalproject.http.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.finalproject.models.image.Agency;
import uni.finalproject.models.image.Library;
import uni.finalproject.repository.image.AgencyRepository;
import uni.finalproject.repository.image.LibraryRepository;
import uni.finalproject.service.HistoryService;

import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;
import static uni.finalproject.repository.specification.LibrarySpecification.*;


@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private HistoryService historyService;

    Pageable defaultPagination = PageRequest.of(0, 15, Sort.by("name"));

    @GetMapping("/agency")
    public ResponseEntity<Map<String, Object>> getAllAgencies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {

        try {
            Pageable paging = PageRequest.of(page, size);

            Page<Agency> pageAgency = agencyRepository.findAllByOrderByNameAsc(paging);

            Map<String, Object> response = new HashMap<>();
            response.put("list", pageAgency.getContent());
            response.put("currentPage", pageAgency.getNumber());
            response.put("totalItems", pageAgency.getTotalElements());
            response.put("totalPages", pageAgency.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/library")
    public ResponseEntity<Map<String, Object>> getAllMediaRecords(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {

        try {
            Pageable paging = PageRequest.of(page, size);

            Page<Library> pageMedia;
            //if (title == null)
            pageMedia = libraryRepository.findAllByOrderByTitleAsc(paging);
//            else
//                pageMedia = libraryRepository.findByTitleContaining(title, paging);

            Map<String, Object> response = new HashMap<>();
            response.put("media", pageMedia.getContent());
            response.put("currentPage", pageMedia.getNumber());
            response.put("totalItems", pageMedia.getTotalElements());
            response.put("totalPages", pageMedia.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/library/{libraryid}")
    public Library showLibrary(@PathVariable(value = "libraryid") int libraryid) {

        Optional<Library> optionalLibrary = libraryRepository.findById(Long.valueOf(libraryid));

        if (optionalLibrary.isPresent()) {
            Library library = optionalLibrary.get();

            return library;
        }

        return new Library();
    }


    @GetMapping("/library/top")
    public ResponseEntity<Map<String, Object>> showLibrary() {

        ArrayList<Library> libraries = libraryRepository.findTop10ByOrderByIdAsc();

        Map<String, Object> response = new HashMap<>();
        response.put("media", libraries);
        response.put("currentPage", 1);
        response.put("totalItems", libraries.size());
        response.put("totalPages", 1);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //search?page=1&q=1&media_type=image,video,audio&center=&year_start=1920&year_end=2020
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "image,video,audio") String media_type,
            @RequestParam(defaultValue = "0") int center,
            @RequestParam(defaultValue = "0") int year_start,
            @RequestParam(defaultValue = "0") int year_end
    ) {

        try {
            Pageable paging = PageRequest.of(page, size);
            ArrayList<Library> libraries = (ArrayList<Library>) libraryRepository.findAll(
                    where(
                            withTitle(q)).or(withText(q)).
                            and(withType(media_type)).
                            and(withYear(year_start, year_end)).
                            and(withАgency(center)
                            )
            );

            Map<String, Object> response = new HashMap<>();
            response.put("media", libraries);
            response.put("currentPage", 1);
            response.put("totalItems", libraries.size());
            response.put("totalPages", 1);

            historyService.add(q, media_type, center, year_start, year_end);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
