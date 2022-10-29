package np.com.kshitij.commons;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class DtoBaseController<D, E> extends BaseController {
    protected abstract Function<E, D> entityToDto();

    protected abstract Function<D, E> dtoToEntity();

    protected D dto(E t) {
        return entityToDto().apply(t);
    }

    protected E entity(D d) {
        return dtoToEntity().apply(d);
    }

    protected Collection<E> entities(Collection<D> es) {
        return es.stream()
                .map(this::entity)
                .collect(Collectors.toList());
    }

    protected Collection<D> dtos(Collection<E> ds) {
        return ds.stream()
                .map(this::dto)
                .collect(Collectors.toList());
    }
}
