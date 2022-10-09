package np.com.kshitij.commons;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class BaseService extends BaseComponent {
    protected org.springframework.data.domain.Sort sort(Order sort) {
        org.springframework.data.domain.Sort order;
        if (sort == Order.DESC) {
            order = org.springframework.data.domain.Sort.by("updated").descending();
        } else {
            order = org.springframework.data.domain.Sort.by("updated").ascending();
        }
        return order;
    }

    protected <T extends BaseEntity> PageResponse<T> pageResponse(PageRequest pageRequest, List<T> entities) {
        PageResponse<T> pageResponse = new PageResponse<>();
        int size = pageRequest.getSize();
        pageResponse.setContent(entities.stream().limit(size).collect(Collectors.toList()));
        if (entities.size() > size) {
            PageRequest pr = new PageRequest();
            pr.setFrom(entities.get(entities.size() - 2).getUpdated());
            pr.setSize(size);
            pr.setOrder(pageRequest.getOrder());
            pr.setParams(pageRequest.getParams());
            String token = stringify(pr);
            if (token != null && token.length() > 100) {
                log.debug("Token length for query param exceeded 100 chars. Consider using request body instead.");
            }
            pageResponse.setToken(Base64Encoder.encode(token));
        }
        return pageResponse;
    }

    protected org.springframework.data.domain.PageRequest pageable(PageRequest pageRequest) {
        return org.springframework.data.domain.PageRequest.of(0, pageRequest.getSize() + 1, sort(pageRequest.getOrder()));
    }
}
