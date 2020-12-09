package uni.finalproject.repository.user;

import org.springframework.data.repository.CrudRepository;
import uni.finalproject.models.user.Role;
import uni.finalproject.models.user.UserRoles;

public interface RoleRepository extends CrudRepository<Role, Long> {

    /**
     *
     * @param role
     * @return
     */
    Role findByRole(UserRoles role);
}
