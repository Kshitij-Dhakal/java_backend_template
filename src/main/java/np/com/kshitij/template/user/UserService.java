package np.com.kshitij.template.user;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    void addRoleToUser(String email, Role role);

    User getUser(String email);

    List<User> getUsers();
}
