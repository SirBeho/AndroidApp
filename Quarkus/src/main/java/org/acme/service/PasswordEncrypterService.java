
package org.acme.service;
import org.mindrot.jbcrypt.BCrypt;
import javax.transaction.Transactional;
import java.util.List;
import org.acme.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.User; 

@ApplicationScoped
public class PasswordEncrypterService {

    @Inject
    UserRepository userRepository;


    public void encryptExistingPasswords() {
        List<User> users = userRepository.findAll().list();

        for (User user : users) {
            String existingPassword = user.getPassword();

            // Verifica si la contraseña ya está encriptada con BCrypt (normalmente empieza con $2a$ o $2b$)
            if (!existingPassword.startsWith("$2a$") && !existingPassword.startsWith("$2b$")) {
                // Si no está encriptada, la encripta
                String hashedPassword = BCrypt.hashpw(existingPassword, BCrypt.gensalt(12));
                user.setPassword(hashedPassword);
                userRepository.persist(user); // Guarda el cambio en la base de datos
            }
        }
    }
}
