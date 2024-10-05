package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.acme.model.Task;


@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {

    public List<Task> findByUserId(Long userId) {
        return find("user.id", userId).list(); 
    }

    // Método para buscar tareas por estado
    public List<Task> findByStatus(String status) {
        return find("status", status).list();
    }

    // Método para obtener todas las tareas de un proyecto específico
    public List<Task> findByProject(Long projectId) {
        return find("project.id", projectId).list();
    }

    public List<Task> findTasksByUserId(Long userId) {
        return find("user.id", userId).list();
    }

}
