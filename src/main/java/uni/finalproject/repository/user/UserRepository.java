package uni.finalproject.repository.user;

import org.springframework.data.repository.CrudRepository;
import uni.finalproject.models.user.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
}
