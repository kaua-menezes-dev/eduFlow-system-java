package br.com.eduFlowProject.repository;

import br.com.eduFlowProject.model.entities.Teacher;

import java.util.List;
import java.util.Optional;

public class TeacherRepository extends InMemory<Teacher, String> {

    @Override
    protected String getId(Teacher entity) {
        return getId(entity);
    }

    public List<Teacher> findAllActive(){
        return findAll().stream()
                .filter(Teacher::isActive)
                .toList();
    }

    public Optional<Teacher> findByRegistration(String registration) {
        return findAll().stream()
                .filter(t -> t.getRegistrationNumber().equals(registration))
                .findFirst();
    }

    public boolean existsByTeacher(Teacher teacher) {
        return findAll().stream()
                .anyMatch(t -> t != null && t.getId().equals(t.getId()));
    }

    public boolean existsByRegistration(String registration) {
        return findAll().stream()
                .anyMatch(t -> t.getRegistrationNumber().equals(registration));
    }

}
