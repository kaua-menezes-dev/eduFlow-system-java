package br.com.eduFlowProject.repository;

import br.com.eduFlowProject.model.entities.Evaluation;

import java.util.Optional;

public class EvaluationRepository extends InMemory<Evaluation, String> {

    @Override
    protected String getId(Evaluation entity) {
        return getId(entity);
    }

    public Optional<Evaluation> findByStudentId(String id) {
        return findAll().stream()
                .filter(e -> e.getStudent().getId().equals(id))
                .findFirst();
    }

    public Optional<Evaluation> findByClassGroupId(String id) {
        return findAll().stream()
                .filter(e -> e.getClassGroup().getId().equals(id))
                .findFirst();
    }

    public Optional<Evaluation> findByEvaluationId(String id) {
        return findAll().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public boolean existsByEvaluationId(String id) {
        return findAll().stream()
                .anyMatch(e -> e.getId().equals(id));
    }
}
