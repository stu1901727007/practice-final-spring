package uni.finalproject.http.controllers.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uni.finalproject.http.requests.backoffice.AgencyFormRequest;
import uni.finalproject.models.image.Agency;
import uni.finalproject.models.user.User;
import uni.finalproject.repository.image.AgencyRepository;
import uni.finalproject.service.AgencyService;
import uni.finalproject.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/bo")
public class AgencyController {

    private static final String VIEWS_CREATE_OR_UPDATE_FORM = "backoffice/agency/form";

    @Autowired
    private AgencyRepository agencyRepo;

    @Autowired
    private AgencyService agencyService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping(value = "/agency")
    public ModelAndView agencyList(HttpServletRequest request) {

        int page = 0;
        int size = 10;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        ModelAndView modelAndView = new ModelAndView("backoffice/agency/list");
        modelAndView.addObject("agencies", agencyRepo.findAllByOrderByNameAsc(PageRequest.of(page, size)));

        return modelAndView;
    }

    @GetMapping(value = "/agency/create")
    public ModelAndView agencyCreate() {
        ModelAndView modelAndView = new ModelAndView(VIEWS_CREATE_OR_UPDATE_FORM);

        AgencyFormRequest agencyFormRequest = new AgencyFormRequest();
        modelAndView.addObject("agencyFormData", agencyFormRequest);

        return modelAndView;
    }

    @PostMapping(value = "/agency/create")
    public String agencyStore(@Valid @ModelAttribute("agencyFormData") AgencyFormRequest agencyFormRequest,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, Map<String, Object> model) {

        if (!bindingResult.hasErrors()) {

            Agency agency = agencyService.createAgency(agencyFormRequest);

            redirectAttributes.addFlashAttribute("message", "Успешно създадохте агенция");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            return "redirect:/bo/agency/" + agency.getId() + "/edit";
        } else {

            redirectAttributes.addFlashAttribute("message", "Възникна проблем при създаване на записа!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

            model.put("agencyFormData", agencyFormRequest);
            return VIEWS_CREATE_OR_UPDATE_FORM;
        }
    }

    @GetMapping(value = "/agency/{agencyid}/edit")
    public String agencyEdit(@PathVariable("agencyid") Long agencyid, AgencyFormRequest agencyFormRequest, Map<String, Object> model) {

        Optional<Agency> agency = agencyRepo.findById(agencyid);

        if (agency.isPresent()) {

            Agency rsAgency = agency.get();

            agencyFormRequest.setAgencyCode(rsAgency.getCode())
                    .setAgencyId(rsAgency.getId())
                    .setAgencyName(rsAgency.getName())
                    .setAgencyDetails(rsAgency.getDetails());

            model.put("agencyFormData", agencyFormRequest);

            return VIEWS_CREATE_OR_UPDATE_FORM;
        }

        return null;
    }

    @PostMapping(value = "/agency/{agencyid}/edit")
    public String agencyUpdate(@Valid @ModelAttribute("agencyFormData") AgencyFormRequest agencyFormRequest,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @PathVariable("agencyid") Long agencyid,
                               Map<String, Object> model) {

        if (!bindingResult.hasErrors()) {

            Optional<Agency> agency = agencyRepo.findById(agencyid);

            if (agency.isPresent()) {

                agencyService.updateAgency(agencyFormRequest, agency.get());

                redirectAttributes.addFlashAttribute("message", "Успешно запазихте промените!");
                redirectAttributes.addFlashAttribute("alertClass", "alert-success");

                return "redirect:/bo/agency/" + agency.get().getId() + "/edit";
            }
        }

        redirectAttributes.addFlashAttribute("message", "Възникна проблем при запазване на промените!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        model.put("agencyFormData", agencyFormRequest);
        return VIEWS_CREATE_OR_UPDATE_FORM;
    }
}
