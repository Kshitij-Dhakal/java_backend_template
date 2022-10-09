package np.com.kshitij.template.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import np.com.kshitij.commons.BaseService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byEmail = userRepo.findByEmail(username);
        if (byEmail == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        log.debug("User found in the database : {}", username);
        List<SimpleGrantedAuthority> collect =
                byEmail.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                byEmail.getId().toString(), byEmail.getPassword(), collect);
    }

    @Override
    public User saveUser(@Valid User user) {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public void addRoleToUser(
            @NotNull(message = "Email is blank") String email,
            Role role) {
        log.info("Adding role {} to user {}", role, email);
        User byEmail = userRepo.findByEmail(email);
        byEmail.getRoles().add(role);
    }

    @Override
    public User getUser(@NotNull(message = "Email is blank") String email) {
        log.info("Fetching user : {}", email);
        return userRepo.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }
}
