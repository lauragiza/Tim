package pl.tim.medicalclinic.exception;

import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class DoctorAbsentException extends Exception {

    public DoctorAbsentException(Class clazz, LocalDate absentDay) {
        super(DoctorAbsentException.generateMessage(clazz.getSimpleName(), absentDay));
    }

    private static String generateMessage(String entity, LocalDate absentDay) {
        return StringUtils.capitalize(entity) +
                " is Absent in :" +
                absentDay;
    }

}