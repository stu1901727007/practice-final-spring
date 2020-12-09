package uni.finalproject.service;

import uni.finalproject.http.requests.backoffice.ProfileFormRequest;
import uni.finalproject.models.user.User;

public interface UserService {

    /**
     *
     * @param email
     * @return
     */
    User findUserByEmail(String email);

    /**
     *
     * @param user
     * @param profileFormRequest
     * @return
     */
    User updateProfile(User user, ProfileFormRequest profileFormRequest);
}
