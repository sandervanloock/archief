package be.sandervl.admin.hibernate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

import static java.util.regex.Pattern.compile;

public class BlankOrPatternValidator implements ConstraintValidator<BlankOrPattern, String> {

    private java.util.regex.Pattern pattern;

    public void initialize(BlankOrPattern parameters) {
        Pattern.Flag flags[] = parameters.flags();
        int intFlag = 0;
        for (Pattern.Flag flag : flags) {
            intFlag = intFlag | flag.getValue();
        }

        try {
            pattern = compile(parameters.regexp(), intFlag);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Invalid regular expression.", e);
        }
    }

    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.length() == 0) {
            return true;
        }
        Matcher m = pattern.matcher(value);
        return m.matches();
    }
}
