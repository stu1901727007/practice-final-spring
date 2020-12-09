package uni.finalproject.http.controllers.backoffice;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uni.finalproject.http.requests.backoffice.ProfileFormRequest;
import uni.finalproject.models.user.User;
import uni.finalproject.repository.user.UserRepository;
import uni.finalproject.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/bo")
public class AuhController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private static final String VIEWS_CREATE_OR_UPDATE_FORM = "backoffice/auth/profile";

    @GetMapping(value = {"/login"})
    public ModelAndView login() {
        return new ModelAndView("backoffice/auth/login");
    }

    @GetMapping(value = {"/logout"})
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:login";
    }

    @GetMapping(value = "/")
    public String home() {
        return "redirect:dashboard";
    }

    @GetMapping(value = "/profile")
    public ModelAndView getUserProfile() {
        ModelAndView modelAndView = new ModelAndView(VIEWS_CREATE_OR_UPDATE_FORM);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        ProfileFormRequest profileFormRequest = new ProfileFormRequest()
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setMobileNumber(user.getMobileNumber())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword());

        modelAndView.addObject("userFormData", profileFormRequest);
        modelAndView.addObject("userName", user.getFullName());

        return modelAndView;
    }

    @PostMapping(value = "/profile")
    public String agencyUpdate(@Valid @ModelAttribute("userFormData") ProfileFormRequest profileFormRequest,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Map<String, Object> model) {

        if (!bindingResult.hasErrors()) {

            EmailValidator validator = EmailValidator.getInstance();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());

            userService.updateProfile(user, profileFormRequest);

            redirectAttributes.addFlashAttribute("message", "Успешно запазихте промените!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            return "redirect:/bo/profile";
        }

        redirectAttributes.addFlashAttribute("message", "Възникна проблем при запазване на промените!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        model.put("userFormData", profileFormRequest);
        return VIEWS_CREATE_OR_UPDATE_FORM;
    }



}
