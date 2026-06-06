package br.com.eduFlowProject.repository;

import br.com.eduFlowProject.model.entities.Student;

import java.util.List;
import java.util.Optional;

public class StudentRepository extends InMemory<Student, String>{

    @Override
    protected String getId(Student entity) {
        return entity.getId();
    }

    public Optional<Student> findByCpf(String cpf) {
        return findAll().stream()
                .filter(s -> s.getCpf().equalsIgnoreCase(cpf))
                .findFirst();
    }

    public List<Student> findAllActive(){
        return findAll().stream()
                .filter(Student::isActive)
                .toList();
    }

    public boolean existsByCpf(String cpf) {
        return findAll().stream()
                .anyMatch(s -> s.getCpf().equals(cpf));
    }

}
