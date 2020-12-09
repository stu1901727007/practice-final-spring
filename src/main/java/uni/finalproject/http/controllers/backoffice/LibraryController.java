package uni.finalproject.http.controllers.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uni.finalproject.http.requests.backoffice.LibraryFormRequest;
import uni.finalproject.models.image.Library;
import uni.finalproject.models.user.User;
import uni.finalproject.repository.image.AgencyRepository;
import uni.finalproject.repository.image.LibraryRepository;
import uni.finalproject.service.LibraryService;
import uni.finalproject.util.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/bo")
public class LibraryController {

    private static final String VIEWS_CREATE_OR_UPDATE_FORM = "backoffice/library/form";

    private static final Map<String, String> TYPES = new HashMap<String, String>() {{
        put("image", "Изображение");
        put("video", "Видео");
        put("audio", "Аудио");
    }};

    @Autowired
    private LibraryRepository libraryRepo;

    @Autowired
    private AgencyRepository agencyRepo;

    @Autowired
    private LibraryService libraryService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping(value = "/library")
    public ModelAndView libraryList(HttpServletRequest request) {

        int page = 0;
        int size = 10;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        ModelAndView modelAndView = new ModelAndView("backoffice/library/list");
        modelAndView.addObject("libraries", libraryRepo.findAllByOrderByTitleAsc(PageRequest.of(page, size)));
        return modelAndView;
    }

    @GetMapping(value = "/library/create")
    public ModelAndView libraryCreate() {
        ModelAndView modelAndView = new ModelAndView(VIEWS_CREATE_OR_UPDATE_FORM);

        LibraryFormRequest libraryFormRequest = new LibraryFormRequest();
        modelAndView.addObject("libraryFormData", libraryFormRequest);
        modelAndView.addObject("hashTypes", TYPES);
        modelAndView.addObject("agencies", agencyRepo.findAll());

        return modelAndView;
    }

    @PostMapping(value = "/library/create")
    public String libraryStore(@Valid @ModelAttribute("libraryFormData") LibraryFormRequest libraryFormRequest,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Map<String, Object> model,
                               @RequestParam("image") MultipartFile multipartFile) {

        if (!bindingResult.hasErrors()) {

            Library library = libraryService.createLibrary(libraryFormRequest, multipartFile);

            redirectAttributes.addFlashAttribute("message", "Успешно създадохте запис");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            return "redirect:/bo/library/" + library.getId() + "/edit";

        } else {

            redirectAttributes.addFlashAttribute("message", "Възникна проблем при създаване на записа!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

            model.put("hashTypes", TYPES);
            model.put("agencies", agencyRepo.findAll());
            model.put("libraryFormData", libraryFormRequest);

            return VIEWS_CREATE_OR_UPDATE_FORM;
        }
    }

    @GetMapping(value = "/library/{libraryid}/edit")
    public String libraryEdit(@PathVariable("libraryid") Long libraryid, Map<String, Object> model) {

        Optional<Library> library = libraryRepo.findById(libraryid);

        if (library.isPresent()) {
            LibraryFormRequest libraryFormRequest = new LibraryFormRequest();

            Library rsLibrary = library.get();

            libraryFormRequest.setLibraryTitle(rsLibrary.getTitle())
                    .setLibraryId(rsLibrary.getId())
                    .setLibraryType(rsLibrary.getType())
                    .setLibraryResume(rsLibrary.getResume())
                    .setLibraryText(rsLibrary.getText())
                    .setLibraryEmbed(rsLibrary.getEmbed())
                    .setLibraryPhotographer(rsLibrary.getPhotographer())
                    .setLibraryFile(rsLibrary.getFile())
                    .setLibraryAgency(rsLibrary.getAgency())
                    .setLibraryDeleted(rsLibrary.getDeleted());

            try {
                libraryFormRequest.setLibraryPublicationDate(DateUtils.formattedFront(rsLibrary.getPublicationDate()));
            }catch(Exception e){}

            model.put("libraryFormData", libraryFormRequest);
            model.put("hashTypes", TYPES);
            model.put("agencies", agencyRepo.findAll());

            return VIEWS_CREATE_OR_UPDATE_FORM;
        }

        return null;
    }

    @PostMapping(value = "/library/{libraryid}/edit")
    public String libraryUpdate(@Valid @ModelAttribute("libraryFormData") LibraryFormRequest libraryFormRequest,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @PathVariable("libraryid") Long libraryid,
                                Map<String, Object> model,
                                @RequestParam("image") MultipartFile multipartFile) {

        if (!bindingResult.hasErrors()) {

            Optional<Library> library = libraryRepo.findById(libraryid);

            if (library.isPresent()) {

                libraryService.updateLibrary(libraryFormRequest, multipartFile, library.get());

                redirectAttributes.addFlashAttribute("message", "Успешно запазихте промените!");
                redirectAttributes.addFlashAttribute("alertClass", "alert-success");

                return "redirect:/bo/library/" + library.get().getId() + "/edit";
            }
        }

        redirectAttributes.addFlashAttribute("message", "Възникна проблем при създаване на записа!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        model.put("libraryFormData", libraryFormRequest);
        return VIEWS_CREATE_OR_UPDATE_FORM;
    }

    @DeleteMapping(value = "/library/{libraryid}/edit")
    public String libraryUpdate(@PathVariable("libraryid") Long libraryid, RedirectAttributes redirectAttributes) {

        Optional<Library> library = libraryRepo.findById(libraryid);

        if (library.isPresent()) {

            Library rsLibrary = library.get();

            libraryService.deleteLibrary(rsLibrary);

            redirectAttributes.addFlashAttribute("message", "Успешно изтрихте " + rsLibrary.getTitle() +"!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        }
        else
        {
            redirectAttributes.addFlashAttribute("message", "Възникна проблем при изтриваме на запис!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/bo/library";
    }

    private Library getLibrary(User user) {

        return new Library();
    }
}
