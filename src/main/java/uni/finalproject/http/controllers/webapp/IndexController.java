package uni.finalproject.http.controllers.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uni.finalproject.repository.image.LibraryRepository;

import java.util.Map;


@Controller
public class IndexController {

    @Autowired
    private LibraryRepository libraryRepo;

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/")
    public String home(Map<String, Object> model) {

        Map<String, Integer> yearsRange = libraryRepo.getRangeYear();

        model.put("yearsRange", yearsRange);

        return "webapp/index";
    }
}
