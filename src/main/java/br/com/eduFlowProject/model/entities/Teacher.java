package br.com.eduFlowProject.model.entities;

import br.com.eduFlowProject.model.enums.Specialty;
import br.com.eduFlowProject.model.enums.TeacherStatus;
import br.com.eduFlowProject.model.exceptions.StatusChangeException;
import br.com.eduFlowProject.model.util.IDGenerator;
import br.com.eduFlowProject.model.util.Validator;

import java.time.LocalDateTime;

public class Teacher {

    private final String id;
    private String name;
    private final String registrationNumber;
    private Specialty teacherSpecialty;
    private TeacherStatus teacherStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Teacher(String name, String registrationNumber, Specialty teacherSpecialty) {
        validateDataTeacher(name, teacherSpecialty, registrationNumber);
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.teacherSpecialty = teacherSpecialty;
        this.id = IDGenerator.nextTeacherId();
        this.teacherStatus = TeacherStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Specialty getTeacherSpecialty() {
        return teacherSpecialty;
    }

    public TeacherStatus getTeacherStatus() {
        return teacherStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isActive(){
        return teacherStatus == TeacherStatus.ACTIVE;
    }

    public void activateTeacher(){
        if (isActive()) {
            throw new StatusChangeException("Professor já está ativo.");
        }
        teacherStatus = TeacherStatus.ACTIVE;
        updatedAt = LocalDateTime.now();
    }

    public void deactivateTeacher(){
        if (!isActive()) {
            throw new StatusChangeException("Professor já está inativo.");
        }
        teacherStatus = TeacherStatus.INACTIVE;
        updatedAt = LocalDateTime.now();
    }

    public void setName(String newName) {
        Validator.requireTextLength(newName, 3, 50, "Novo nome do professor");
        this.name = newName;
        this.updatedAt = LocalDateTime.now();
    }

    public void setTeacherSpecialty(Specialty teacherSpecialty) {
        Validator.requireNonNull(teacherSpecialty, "Nova area do professor");
        this.teacherSpecialty = teacherSpecialty;
        this.updatedAt = LocalDateTime.now();
    }

    private void validateDataTeacher(String name, Specialty teacherSpecialty, String registrationNumber) {
        Validator.requireTextLength(name, 3, 50, "Nome do professor");
        Validator.requireNonNull(teacherSpecialty, "Area do professor");
        Validator.requireRegistrationNumber(registrationNumber, "Nº de registro do professor");
    }

}
