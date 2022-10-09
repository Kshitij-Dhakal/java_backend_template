package np.com.kshitij.commons;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", source = "id")
@Mapping(target = "created", source = "created")
@Mapping(target = "updated", source = "updated")
public @interface ToEntity {
}