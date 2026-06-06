package br.com.eduFlowProject.model.util;

import br.com.eduFlowProject.model.exceptions.StatusChangeException;
import br.com.eduFlowProject.model.exceptions.ValidationException;

import java.time.LocalDate;

public class Validator {

    public static void requireNonNull(Object object, String fieldName) {
        if (object == null) {
            throw new ValidationException(fieldName + " não pode ser nullo.");
        }
    }

    public static void requireNonBlank(String value, String fieldName) {
        requireNonNull(value, fieldName);
        if (value.trim().isBlank()) {
            throw new ValidationException(fieldName + " não pode ser vazio.");
        }
    }

    public static void requireMaxCapacity(int value, String fieldName) {
        requireNonNull(value, fieldName);
        if (value <= 0 || value > 30) {
            throw new ValidationException(fieldName + " deve ter no maximo 30 alunos.");
        }
    }

    public static void requireCpfValidation(String value, String fieldName) {
        requireNonBlank(value, fieldName);
        if (!value.matches("\\d{11}")) {
            throw new ValidationException(fieldName + " deve conter exatamente 11 digitos.");
        }
    }

    public static void requirePhoneValidation(String value, String fieldName) {
        requireNonBlank(value, fieldName);
        if (!value.matches("\\d{11}")) {
            throw new ValidationException(fieldName + " deve conter exatamente 11 digitos (DD) *****-****.");
        }
    }

    public static void requireTextLength(String value, int min, int max, String fieldName) {
        requireNonBlank(value, fieldName);
        if (value.length() < min || value.length() > max) {
            throw new ValidationException(fieldName + " deve ter entre: " + min + " e " + max + " caracteres.");
        }
    }

    public static void requireValidGrade(double value, String fieldName) {
        requireNonNull(value, fieldName);
        if (value < 0 || value > 10) {
            throw new ValidationException(fieldName + " não pode ser menor que 0 ou maior que 10.");
        }
    }

    public static void requireRegistrationNumber(String value, String fieldName) {
        requireNonBlank(value, fieldName);
        if (!value.matches("\\d{4}")) {
            throw new ValidationException(fieldName + " deve conter exatamente 4 digitos.");
        }
    }

    public static void requireInitialAndFinalDate(LocalDate initialDate, LocalDate finalDate, String fieldName) {
        requireNonNull(initialDate, fieldName);
        requireNonNull(finalDate, fieldName);
        if (finalDate.isBefore(initialDate)) {
            throw new ValidationException(fieldName + " deve ser depois da data de inicio.");
        }
    }

    public static void requirePastOrFutureDate(LocalDate initialDate, String fieldName) {
        requireNonNull(initialDate, fieldName);
        if (initialDate.isAfter(LocalDate.now())) {
            throw new ValidationException(fieldName + " deve ser antes da data de hoje.");
        }
    }

    public static void requireFutureDate(LocalDate finalDate, String fieldName) {
        requireNonNull(finalDate, fieldName);
        if (finalDate.isBefore(LocalDate.now())) {
            throw new ValidationException(fieldName + " deve ser depois da data de hoje.");
        }
    }

}
