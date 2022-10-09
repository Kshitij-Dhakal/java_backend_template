package np.com.kshitij.template.user;

import lombok.Getter;
import lombok.Setter;
import np.com.kshitij.commons.BaseEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Entity
public class User extends BaseEntity {
    @NotNull(message = "Name field is blank")
    private String name;
    @NotNull(message = "Email field is blank")
    private String email;
    @NotNull(message = "Password field is blank")
    private String password;
    @Enumerated
    @ElementCollection
    private Collection<Role> roles = new ArrayList<>();
}
