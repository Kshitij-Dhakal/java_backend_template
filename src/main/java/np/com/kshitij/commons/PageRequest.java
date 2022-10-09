package np.com.kshitij.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class PageRequest {
    @JsonProperty("p")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Map<String, String[]> params = new HashMap<>();
    @JsonProperty("o")
    private Order order;
    @JsonProperty("f")
    private OffsetDateTime from;
    @JsonProperty("s")
    private int size;
}
