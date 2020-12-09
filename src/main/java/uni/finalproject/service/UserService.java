package uni.finalproject.service;

import uni.finalproject.http.requests.backoffice.ProfileFormRequest;
import uni.finalproject.models.user.User;

public interface UserService {

    User findUserByEmail(String email);
    User updateProfile(User user, ProfileFormRequest profileFormRequest);
}
