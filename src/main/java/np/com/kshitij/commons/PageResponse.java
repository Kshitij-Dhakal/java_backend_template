package np.com.kshitij.commons;

import lombok.Data;

import java.util.Collection;

@Data
public class PageResponse<T> {
    private Collection<T> content;
    private String token;
}
