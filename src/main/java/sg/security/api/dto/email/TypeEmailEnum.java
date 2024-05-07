package sg.security.api.dto.email;

import lombok.Getter;

public enum TypeEmailEnum {

    HOTMAIL("hotmail.com"),
    GMAIL("gmail.com"),
    YAHOO("yahoo.es");

    @Getter
    private final String domain;

    TypeEmailEnum(String domain) {
        this.domain = domain;
    }

    public static boolean isValidDomain(String domain) {
        for (TypeEmailEnum provider : TypeEmailEnum.values()) {
            if (domain.equals(provider.domain)) {
                return true;
            }
        }
        return false;
    }
}
