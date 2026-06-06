package br.com.eduFlowProject.model.util;

public class IDGenerator {

    private static int studentSequence = 0;
    private static int teacherSequence = 0;
    private static int classGroupSequence = 0;
    private static int enrollmentSequence = 0;
    private static int evaluationSequence = 0;

    public IDGenerator() {
    }

    public static String nextStudentId(){
        return String.format("STU-%04d", ++studentSequence);
    }

    public static String nextTeacherId(){
        return String.format("TEA-%04d", ++teacherSequence);
    }

    public static String nextClassGroupId(){
        return String.format("CLS-%04d", ++classGroupSequence);
    }

    public static String nextEnrollmentId(){
        return String.format("ENR-%04d", ++enrollmentSequence);
    }

    public static String nextEvaluationId(){
        return String.format("EVA-%04d", ++evaluationSequence);
    }

}
