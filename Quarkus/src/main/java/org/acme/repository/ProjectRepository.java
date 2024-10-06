package org.acme.repository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.acme.model.Project;

@ApplicationScoped
public class ProjectRepository implements PanacheRepository<Project> {

        public Project findById(Long id) {
            return find("id", id).firstResult();
        }

        public List<Project> findByUserId(Long userId) {
            return find("user.id", userId).list();
        }

}
