package uni.finalproject.http.controllers.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uni.finalproject.repository.user.RoleRepository;
import uni.finalproject.repository.user.UserRepository;


@Controller
public class IndexController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String home() {

        return "webapp/index";
    }
}
