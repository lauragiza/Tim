package pl.tim.medicalclinic.exception;

import org.springframework.util.StringUtils;

public class IllegalEntryGoogleCalendarException extends Exception {

    public IllegalEntryGoogleCalendarException() {
        super(IllegalEntryGoogleCalendarException.generateMessage());
    }

    private static String generateMessage() {
        return StringUtils.capitalize("Inncorrect entry to calendar, check credential.json file ");
    }

}