package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.acme.model.Task; 
import org.acme.model.User; 


@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    // Método para buscar un usuario por name de usuario
    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }

    // Puedes agregar más métodos personalizados aquí
    public boolean usernameExists(String username) {
        return find("username", username).count() > 0;
    }

    // Método para buscar un usuario por id
    public User findById(Long id) {
        return find("id", id).firstResult();
    }

  

}
