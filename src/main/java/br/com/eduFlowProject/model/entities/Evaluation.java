package br.com.eduFlowProject.model.entities;

import br.com.eduFlowProject.model.enums.ResultStudent;
import br.com.eduFlowProject.model.util.IDGenerator;
import br.com.eduFlowProject.model.util.Validator;

import java.time.LocalDateTime;

public class Evaluation {

    private final String id;
    private final Student student;
    private final ClassGroup classGroup;
    private final double firstSemesterTotal;
    private final double secondSemesterTotal;
    private final double average;
    private final ResultStudent resultStudent;
    private final LocalDateTime createdAt;

    public Evaluation(Student student, ClassGroup classGroup, double[] firstGrade, double[] secondGrade) {
        validateDataEvaluation(student, classGroup, firstGrade, secondGrade);
        this.student = student;
        this.classGroup = classGroup;
        this.firstSemesterTotal = calculateGradeStudent(firstGrade);
        this.secondSemesterTotal = calculateGradeStudent(secondGrade);
        this.average = calculatedAverage();
        this.resultStudent = resultStudentPerAverage(this.average);
        this.id = IDGenerator.nextEvaluationId();
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public double getFirstSemesterTotal() {
        return firstSemesterTotal;
    }

    public double getSecondSemesterTotal() {
        return secondSemesterTotal;
    }

    public double getAverage() {
        return average;
    }

    public ResultStudent getResultStudent() {
        return resultStudent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    private double calculateGradeStudent(double... grades) {
        if (grades.length != 4) {
            throw new IllegalArgumentException("É preciso passar 4 notas.");
        }
        double sum = 0.0;
        for (double grade : grades) {
            Validator.requireNonNull(grade, "A nota");
            Validator.requireValidGrade(grade, "As notas");
            sum += grade;
        }
        return sum;
    }

    private double getTotalSum() {
        return this.firstSemesterTotal + this.secondSemesterTotal;
    }

    private double calculatedAverage() {
        final double totalNumbersGrades = 8.0;
        return getTotalSum() / totalNumbersGrades;
    }

    private ResultStudent resultStudentPerAverage(double average) {
        if (average >= 6.0) {
            return ResultStudent.APPROVED;
        } else if (average >= 4.0) {
            return ResultStudent.RECOVERY;
        } else {
            return ResultStudent.DISAPPROVED;
        }
    }

    private void validateDataEvaluation(Student student, ClassGroup classGroup, double[] firstGrade, double[] secondGrade) {
        Validator.requireNonNull(student, "Dados do estudante");
        Validator.requireNonNull(classGroup, "Dados da classe");
        Validator.requireNonNull(firstGrade, "Notas do primeiro semestre");
        Validator.requireNonNull(secondGrade, "Notas do segundo semestre");
    }
}
