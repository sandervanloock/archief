package be.sandervl.admin.hibernate.validator;

import javax.validation.Payload;
import javax.validation.constraints.Pattern;

/**
 * Created by sander on 30/07/2015.
 */
public @interface BlankOrPattern {
    String regexp();
    Pattern.Flag[] flags() default {};
    String message() default "{javax.validation.constraints.Pattern.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}
