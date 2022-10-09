package np.com.kshitij.template.user;

import lombok.RequiredArgsConstructor;
import np.com.kshitij.commons.DtoBaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.function.Function;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController extends DtoBaseController<UserDto, User> {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @GetMapping("/users")
    public ResponseEntity<Collection<UserDto>> getUsers() {
        return ResponseEntity.ok().body(dtos(userService.getUsers()));
    }

    @PostMapping("/user")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto user) {
        return ResponseEntity.ok().body(dto(userService.saveUser(entity(user))));
    }

    @PatchMapping("/user/role")
    public ResponseEntity<?> saveRole(@RequestBody UserRole userRole) {
        userService.addRoleToUser(userRole.getEmail(), userRole.getRole());
        return ResponseEntity.ok().build();
    }

    @Override
    protected Function<User, UserDto> entityToDto() {
        return userDtoMapper::userToUserDto;
    }

    @Override
    protected Function<UserDto, User> dtoToEntity() {
        return userDtoMapper::userDtoToUser;
    }
}
