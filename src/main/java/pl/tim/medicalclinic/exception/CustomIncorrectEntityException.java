package pl.tim.medicalclinic.exception;

import org.springframework.util.StringUtils;

public class CustomIncorrectEntityException extends Exception {

    public CustomIncorrectEntityException(String field) {
        super(CustomIncorrectEntityException.generateMessage(field));
    }

    private static String generateMessage(String field) {
        return StringUtils.capitalize(field) +
                " has incorrect value";
    }

}