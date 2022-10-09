package np.com.kshitij.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import np.com.kshitij.commons.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Slf4j
@SuppressWarnings("SameParameterValue")
public class BaseComponent {
    private static ObjectMapper objectMapper;
    @Autowired
    private ObjectMapper objectMapper0;

    protected static <T> T parse(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new ServerException("Error while deserializing payload", e);
        }
    }

    protected static String stringify(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new ServerException("Error while serializing payload", e);
        }
    }

    @PostConstruct
    public void init() {
        synchronized (BaseComponent.class) {
            if (objectMapper == null) {
                objectMapper = objectMapper0;
            }
        }
    }
}
