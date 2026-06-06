package br.com.eduFlowProject.model.entities;

import br.com.eduFlowProject.model.enums.StudentStatus;
import br.com.eduFlowProject.model.exceptions.StatusChangeException;
import br.com.eduFlowProject.model.util.IDGenerator;
import br.com.eduFlowProject.model.util.Validator;

import java.time.LocalDateTime;

public class Student {

    private final String id;
    private String name;
    private final String cpf;
    private String phone;
    private StudentStatus studentStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Student(String name, String cpf, String phone) {
        validateDataStudent(name, cpf, phone);
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.studentStatus = StudentStatus.ACTIVE;
        this.id = IDGenerator.nextStudentId();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public String getId() {
        return id;
    }

    public StudentStatus getStudentStatus() {
        return studentStatus;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isActive() {
        return studentStatus == StudentStatus.ACTIVE;
    }

    public void activateStudent() {
        if (isActive()) {
            throw new StatusChangeException("Aluno já está ativo.");
        }
        studentStatus = StudentStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivateStudent() {
        if (!isActive()) {
            throw new StatusChangeException("Aluno já está inativo.");
        }
        studentStatus = StudentStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void setName(String newName) {
        Validator.requireTextLength(newName, 3, 50, "Novo nome do estudante");
        this.name = newName;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPhone(String newPhone) {
        Validator.requirePhoneValidation(newPhone, "Novo telefone do estudante");
        this.phone = newPhone;
        this.updatedAt = LocalDateTime.now();
    }


    private void validateDataStudent(String name, String cpf, String phone) {
        Validator.requireCpfValidation(cpf, "CPF do estudante");
        Validator.requirePhoneValidation(phone, "Telefone do estudante");
        Validator.requireTextLength(name, 3, 50, "Nome do estudante");
    }
}
