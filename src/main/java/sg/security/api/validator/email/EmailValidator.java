package sg.security.api.validator.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sg.security.api.dto.email.TypeEmailEnum;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return EMAIL_PATTERN.matcher(email).matches() && isTypeValid(email);
    }

    private boolean isTypeValid(String email) {

        String[] parts = email.split("@");

        if (parts.length != 2) {
            return false;
        }

        String domain = parts[1].toLowerCase();
        return TypeEmailEnum.isValidDomain(domain);
    }
}
