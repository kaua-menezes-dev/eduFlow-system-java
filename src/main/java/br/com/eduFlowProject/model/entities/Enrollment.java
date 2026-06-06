package br.com.eduFlowProject.model.entities;

import br.com.eduFlowProject.model.enums.EnrollmentStatus;
import br.com.eduFlowProject.model.exceptions.StatusChangeException;
import br.com.eduFlowProject.model.util.IDGenerator;
import br.com.eduFlowProject.model.util.Validator;

import java.time.LocalDateTime;

public class Enrollment {

    private final String id;
    private Student student;
    private ClassGroup classGroup;
    private EnrollmentStatus enrollmentStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String reason;
    private LocalDateTime cancelledAt;
    private LocalDateTime completedAt;
    private LocalDateTime lockedAt;

    public Enrollment(Student student, ClassGroup classGroup) {
        validateDataEnrollment(student, classGroup);
        this.student = student;
        this.classGroup = classGroup;
        this.id = IDGenerator.nextEnrollmentId();
        this.createdAt = LocalDateTime.now();
        this.enrollmentStatus = EnrollmentStatus.ACTIVE;
        this.updatedAt = null;
        this.reason = null;
        this.cancelledAt = null;
        this.completedAt = null;
    }

    public String getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getLockedAt() {
        return lockedAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public Student getStudent() {
        return student;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isActive(){
        return enrollmentStatus == EnrollmentStatus.ACTIVE;
    }

    public boolean isLocked(){
        return enrollmentStatus == EnrollmentStatus.LOCKED;
    }

    public boolean isCancelled(){return enrollmentStatus == EnrollmentStatus.CANCELLED;}

    public boolean isCompleted() {return enrollmentStatus == EnrollmentStatus.COMPLETED;}

    public void activateEnrollment(){
        validateStatusTransition(EnrollmentStatus.ACTIVE);
        enrollmentStatus = EnrollmentStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void lockedEnrollment(String newReason){
        validateStatusTransition(EnrollmentStatus.LOCKED);
        if (newReason != null) {
            Validator.requireTextLength(newReason, 0, 100, "Motivo de cancelamento");
        }
        this.reason = newReason;
        enrollmentStatus = EnrollmentStatus.LOCKED;

        this.lockedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void canceledEnrollment(String newReason){
        validateStatusTransition(EnrollmentStatus.CANCELLED);
        if (newReason != null) {
            Validator.requireTextLength(newReason, 0, 100, "Motivo de cancelamento");
        }
        this.reason = newReason;
        enrollmentStatus = EnrollmentStatus.CANCELLED;

        this.cancelledAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void completedEnrollment() {
        validateStatusTransition(EnrollmentStatus.COMPLETED);
        enrollmentStatus = EnrollmentStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private void validateDataEnrollment(Student student, ClassGroup classGroup) {
        Validator.requireNonNull(student, "Dados do estudante");
        Validator.requireNonNull(classGroup, "Dados da classe");
    }

    private void validateStatusTransition(EnrollmentStatus targetStatus) {
        boolean valid = switch (targetStatus) {
            case ACTIVE -> enrollmentStatus != EnrollmentStatus.ACTIVE;
            case COMPLETED -> enrollmentStatus == EnrollmentStatus.ACTIVE;
            case CANCELLED -> enrollmentStatus == EnrollmentStatus.ACTIVE || enrollmentStatus == EnrollmentStatus.LOCKED;
            case LOCKED -> enrollmentStatus == EnrollmentStatus.ACTIVE;
        };
        if (!valid) {
            throw new StatusChangeException("Não foi possivel trocar o status da matricula de: " + enrollmentStatus + " para: " +  targetStatus);
        }
    }
}
