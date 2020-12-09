package uni.finalproject.http.controllers.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uni.finalproject.models.user.Role;
import uni.finalproject.models.user.User;
import uni.finalproject.models.user.UserRoles;
import uni.finalproject.repository.user.RoleRepository;
import uni.finalproject.repository.user.UserRepository;


@Controller
public class IndexController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {

        User usera = userRepository.findByEmail("user@local.com");
        Role userRole1 = roleRepository.findByRole(UserRoles.REGULAR_USER);
        userRole1.removeUser(usera);
        //userRole1.removeUser( usera );


        model.addAttribute("name", name);
        return "webapp/index";
    }
}
