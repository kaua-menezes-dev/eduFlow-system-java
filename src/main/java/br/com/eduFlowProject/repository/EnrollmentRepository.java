package br.com.eduFlowProject.repository;

import br.com.eduFlowProject.model.entities.Enrollment;

import java.util.List;
import java.util.Optional;

public class EnrollmentRepository extends InMemory<Enrollment, String> {

    @Override
    protected String getId(Enrollment entity) {
        return getId(entity);
    }

    public List<Enrollment> findByAllActive(){
        return findAll().stream()
                .filter(Enrollment::isActive)
                .toList();
    }

    public Optional<Enrollment> findByEnrollmentId(String id) {
        return findAll().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public Optional<Enrollment> findByStudentId(String id) {
        return findAll().stream()
                .filter(e -> e.getStudent().getId().equals(id))
                .findFirst();
    }

    public Optional<Enrollment> findByClassGroupId(String id) {
        return findAll().stream()
                .filter(e -> e.getClassGroup().getId().equals(id))
                .findFirst();
    }

    public boolean existsByStudentAndClassGroup(String id) {
        return findAll().stream()
                .anyMatch(e -> e.getStudent().getId().equals(id) && e.getClassGroup().getId().equals(id));
    }

}
