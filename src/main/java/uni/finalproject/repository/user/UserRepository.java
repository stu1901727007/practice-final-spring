package uni.finalproject.repository.user;

import org.springframework.data.repository.CrudRepository;
import uni.finalproject.models.user.User;

public interface UserRepository extends CrudRepository<User, Long> {

    /**
     *
     * @param email
     * @return
     */
    User findByEmail(String email);
}
