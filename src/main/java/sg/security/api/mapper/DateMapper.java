package sg.security.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Mapper
public interface DateMapper {

    @Named("dateToOffset")
    default OffsetDateTime mapDateToOffset(final Date date) {
        if (date != null) {
            final LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.atStartOfDay().atOffset(ZoneOffset.UTC);
        } else {
            return null;
        }
    }

    @Named("sqlDateToOffset")
    default OffsetDateTime mapSqlDateToOffset(final java.sql.Date sqlDate) {
        if (null == sqlDate) {
            return null;
        }
        final Date date = new Date(sqlDate.getTime());
        return date.toInstant().atOffset(ZoneOffset.UTC);
    }

    @Named("offsetToDate")
    default Date offsetToDate(final OffsetDateTime offset) {
        if (offset != null) {
            return Date.from(offset.toLocalDate()
                    .atTime(0, 0)
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        }
        return null;
    }

    @Named("offsetToLocalDate")
    default LocalDate offsetToLocalDate(final OffsetDateTime offset) {
        if (offset != null) {
            return offset.toLocalDate();
        }
        return null;
    }

}
