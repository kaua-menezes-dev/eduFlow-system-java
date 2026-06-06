package br.com.eduFlowProject.repository;

import br.com.eduFlowProject.model.entities.ClassGroup;

import java.util.List;
import java.util.Optional;

public class ClassGroupRepository extends InMemory<ClassGroup, String> {

    @Override
    protected String getId(ClassGroup entity) {
        return getId(entity);
    }

    public List<ClassGroup> findAllOpen(){
        return findAll().stream()
                .filter(ClassGroup::isOpen)
                .toList();
    }

    public Optional<ClassGroup> findByTeacherId(String id) {
        return findAll().stream()
                .filter(cg -> cg.getTeacher().getId().equals(id))
                .findFirst();
    }

    public Optional<ClassGroup> findByClassGroupId(String id){
        return findAll().stream()
                .filter(cg -> cg.getId().equals(id))
                .findFirst();
    }

    public boolean existsByClassGroupId(String id) {
        return findAll().stream()
                .anyMatch(cg -> cg.getId().equals(id));
    }
}
