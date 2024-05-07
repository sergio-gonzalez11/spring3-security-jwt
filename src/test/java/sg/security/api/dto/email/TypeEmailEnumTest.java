package sg.security.api.dto.email;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import sg.security.api.dto.email.TypeEmailEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypeEmailEnumTest {

  @Nested
  class IsAvalid {

    @Test
    void isValidDomain() {
      assertEquals(Boolean.TRUE, TypeEmailEnum.isValidDomain("gmail.com"));
    }

    @Test
    void NotValidDomain() {
      assertEquals(Boolean.FALSE, TypeEmailEnum.isValidDomain("gmail.es"));
    }
  }

}
