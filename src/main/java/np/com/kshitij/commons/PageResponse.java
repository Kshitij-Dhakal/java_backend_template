package np.com.kshitij.commons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = false)
public class PageResponse<T> extends RepresentationModel<PageResponse<T>> {
    private Collection<T> content;
}
