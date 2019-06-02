package pl.tim.medicalclinic.exception;

import org.springframework.util.StringUtils;

public class AlreadyExistsException extends  Exception{

    public AlreadyExistsException(Class clazz, String uniquField) {
        super(AlreadyExistsException.generateMessage(clazz.getSimpleName(), uniquField));
    }

    private static String generateMessage(String entity, String uniqueField) {
        return StringUtils.capitalize(entity) +
                " already exists with the given field : " +
                uniqueField;
    }
}
