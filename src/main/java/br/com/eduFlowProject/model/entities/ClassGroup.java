package br.com.eduFlowProject.model.entities;

import br.com.eduFlowProject.model.enums.ClassGroupStatus;
import br.com.eduFlowProject.model.enums.Specialty;
import br.com.eduFlowProject.model.exceptions.StatusChangeException;
import br.com.eduFlowProject.model.util.IDGenerator;
import br.com.eduFlowProject.model.util.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClassGroup {

    private final String id;
    private Teacher teacher;
    private Specialty specialty;
    private ClassGroupStatus classGroupStatus;
    private String reason;
    private int maxCapacity;
    private LocalDate startDate;
    private LocalDate endDate;
    private final LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private LocalDateTime cancelledAt;

    public ClassGroup(Teacher teacher, Specialty specialty, int maxCapacity, LocalDate startDate, LocalDate endDate) {
        validateDataClassGroup(teacher, specialty, maxCapacity, startDate, endDate);
        this.teacher = teacher;
        this.specialty = specialty;
        this.maxCapacity = maxCapacity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.classGroupStatus = ClassGroupStatus.FORMATION;
        this.id = IDGenerator.nextClassGroupId();
        this.createdAt = LocalDateTime.now();
        this.updateAt = null;
        this.reason = null;
        this.cancelledAt = null;
    }

    public String getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public ClassGroupStatus getClassGroupStatus() {
        return classGroupStatus;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public boolean sameSpecialty() {
        return specialty == teacher.getTeacherSpecialty();
    }

    public boolean isOpen() {
        return classGroupStatus == ClassGroupStatus.FORMATION;
    }

    public void closeClassGroup() {
        validateStatusTransition(ClassGroupStatus.CLOSED);
        classGroupStatus = ClassGroupStatus.CLOSED;
        this.updateAt = LocalDateTime.now();
    }

    public void openClassGroup() {
        validateStatusTransition(ClassGroupStatus.FORMATION);
        classGroupStatus = ClassGroupStatus.FORMATION;
        this.updateAt = LocalDateTime.now();
    }

    public void cancelClassGroup(String newReason) {
        validateStatusTransition(ClassGroupStatus.CANCELLED);
        if (newReason != null) {
            Validator.requireTextLength(newReason, 0, 100, "Motivo do cancelamento");
        }

        this.reason = newReason;

        classGroupStatus = ClassGroupStatus.CANCELLED;
        this.updateAt = LocalDateTime.now();
        this.cancelledAt = LocalDateTime.now();
    }

    public void inProgressClassGroup() {
        validateStatusTransition(ClassGroupStatus.IN_PROGRESS);
        classGroupStatus = ClassGroupStatus.IN_PROGRESS;
        this.updateAt = LocalDateTime.now();
    }


    public void setStartDate(LocalDate newStartDate) {
        Validator.requirePastOrFutureDate(newStartDate, "Nova data de inicio");
        Validator.requireInitialAndFinalDate(newStartDate, getEndDate(), "Data final");
        if (!isOpen()) {
            throw new StatusChangeException("Classe deve estar aberta para trocar a data de inicio.");
        }
        this.startDate = newStartDate;
        this.updateAt = LocalDateTime.now();
    }

    public void setEndDate(LocalDate newEndDate) {
        Validator.requireFutureDate(newEndDate, "Nova data final");
        Validator.requireInitialAndFinalDate(getStartDate(), newEndDate, "Data final");
        if (!isOpen()) {
            throw new StatusChangeException("Classe deve estar aberta para trocar a data final.");
        }
        this.endDate = newEndDate;
        this.updateAt = LocalDateTime.now();
    }

    private void validateDataClassGroup(Teacher teacher, Specialty specialty, int maxCapacity, LocalDate startDate, LocalDate endDate) {
        Validator.requireNonNull(teacher, "Dados do professor");
        Validator.requireNonNull(specialty, "Materia da turma");
        Validator.requireMaxCapacity(maxCapacity, "Capacidade da sala");
        Validator.requirePastOrFutureDate(startDate, "Data de inicio");
        Validator.requireFutureDate(endDate, "Data final");
        Validator.requireInitialAndFinalDate(startDate, endDate, "Data final");
    }

    private void validateStatusTransition(ClassGroupStatus targetStatus) {
        boolean valid = switch (targetStatus) {
            case FORMATION -> classGroupStatus != ClassGroupStatus.FORMATION;
            case IN_PROGRESS -> classGroupStatus == ClassGroupStatus.FORMATION;
            case CLOSED -> classGroupStatus == ClassGroupStatus.IN_PROGRESS;
            case CANCELLED -> classGroupStatus != ClassGroupStatus.CLOSED;
        };
        if (!valid) {
            throw new StatusChangeException("Não foi possivel trocar o status da matricula de: " + classGroupStatus + " para: " + targetStatus);
        }
    }
}
