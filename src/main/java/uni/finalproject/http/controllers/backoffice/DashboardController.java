package uni.finalproject.http.controllers.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uni.finalproject.repository.image.HistoryRepository;
import uni.finalproject.service.UserService;

@Controller
@RequestMapping("/bo")
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping(value = "/home")
    public String home() {
        return "redirect:dashboard";
    }

    /**
     *
     * @return
     */
    @GetMapping(value = "/dashboard")
    public ModelAndView dashboard() {

        ModelAndView modelAndView = new ModelAndView("backoffice/dashboard/dashboard");
        modelAndView.addObject("history", historyRepository.findAllByOrderByIdDesc(PageRequest.of(0, 10)));

        return modelAndView;
    }
}
