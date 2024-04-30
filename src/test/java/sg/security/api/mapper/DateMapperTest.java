package sg.security.api.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;

import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Mapper
class DateMapperTest {
  private DateMapper mapper;

  @BeforeEach
  void setUp() {
    this.mapper = new DateMapperImpl();
  }

  @Test
  void testMapDateToOffset() {

    final Clock clock = Clock.systemUTC();
    final OffsetDateTime offsetDateTime1 = clock.instant().atOffset(ZoneOffset.UTC);
    final Date date = Date.from(offsetDateTime1.toLocalDate().atStartOfDay(ZoneOffset.UTC).toInstant());
    final OffsetDateTime offsetDateTime = this.mapper.mapDateToOffset(date);
    Assertions.assertThat(offsetDateTime).isEqualTo(date.toInstant().atOffset(ZoneOffset.UTC));

  }

  @Test
  void testMapNullDateToOffset() {
    final OffsetDateTime offsetDateTime = this.mapper.mapDateToOffset(null);
    Assertions.assertThat(offsetDateTime).isNull();
  }

  @Test
  void testMapSqlDateToOffset() {
    final long time = new Date().getTime();
    final java.sql.Date date = new java.sql.Date(time);
    final OffsetDateTime offsetDateTime = this.mapper.mapSqlDateToOffset(date);
    Assertions.assertThat(offsetDateTime).isEqualTo(new Date(time).toInstant().atOffset(ZoneOffset.UTC));
  }

  @Test
  void testMapNullSqlDateToOffset() {
    final OffsetDateTime offsetDateTime = this.mapper.mapSqlDateToOffset(null);
    Assertions.assertThat(offsetDateTime).isNull();
  }

  @Test
  void testOffsetToDate() {
    final OffsetDateTime now = OffsetDateTime.now();
    final Date date = this.mapper.offsetToDate(now.truncatedTo(ChronoUnit.DAYS));
    Assertions.assertThat(date).isEqualTo(new Date(now.truncatedTo(ChronoUnit.DAYS).toInstant().toEpochMilli()));
  }

  @Test
  void testNullOffsetToDate() {
    final Date date = this.mapper.offsetToDate(null);
    Assertions.assertThat(date).isNull();
  }

  @Test
  void testMapLocalDateToOffset() {

    final Clock clock = Clock.systemUTC();
    final LocalDate localDate = LocalDate.now(clock);
    final OffsetDateTime now = OffsetDateTime.now();
    final LocalDate actualLocalDate = mapper.offsetToLocalDate(now);
    Assertions.assertThat(localDate).isEqualTo(actualLocalDate);

  }

  @Test
  void testNullLocalDateToOffset() {

    final LocalDate localDate = this.mapper.offsetToLocalDate(null);
    Assertions.assertThat(localDate).isNull();

  }

}
