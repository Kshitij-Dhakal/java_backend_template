package np.com.kshitij.template.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * A DTO for the {@link User} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private UUID id;
    private OffsetDateTime created;
    private OffsetDateTime updated;
    @NotNull(message = "Name field is blank")
    private String name;
    @NotNull(message = "Email field is blank")
    private String email;
    @NotNull(message = "Password field is blank")
    private String password;
    private Collection<Role> roles;
}