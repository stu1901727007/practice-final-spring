package uni.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uni.finalproject.http.requests.backoffice.ProfileFormRequest;
import uni.finalproject.models.user.User;
import uni.finalproject.repository.user.RoleRepository;
import uni.finalproject.repository.user.UserRepository;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param email
     * @return
     */
    @Transactional
    public User findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            return user.get();
        }
        throw new RuntimeException("Error");
    }

    /**
     *
     * @param user
     * @param profileFormRequest
     * @return
     */
    @Override
    public User updateProfile(User user, ProfileFormRequest profileFormRequest) {
        Optional<User> rsUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));

        if (rsUser.isPresent()) {

            User userModel = rsUser.get();
            userModel.setFirstName(profileFormRequest.getFirstName())
                    .setLastName(profileFormRequest.getLastName())
                    .setMobileNumber(profileFormRequest.getMobileNumber())
                    .setEmail(profileFormRequest.getEmail());

            if( !profileFormRequest.getPassword().isEmpty() )
            {
                userModel.setPassword(bCryptPasswordEncoder.encode(profileFormRequest.getPassword()));
            }

            return userRepository.save(userModel);
        }
        throw new RuntimeException("Error");
    }
}
