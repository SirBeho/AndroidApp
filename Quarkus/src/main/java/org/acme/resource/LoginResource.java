package org.acme.resource; 

import org.acme.model.User; 
import org.acme.repository.UserRepository;
import org.acme.service.PasswordEncrypterService;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    UserRepository userRepository;
    @Inject
    PasswordEncrypterService passwordEncrypterService;

    
 
    @POST
    public Response login(User user) {
        
         User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null && BCrypt.checkpw(user.getPassword(), existingUser.getPassword())) {
            // Autenticación exitosa
            return Response.ok(existingUser).build();
        } else {
            // Autenticación fallida
            return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Usuario o contraseña incorrectos").build();
        }
    }


    @GET
    @Path("/encrypt")
    @Transactional
    public Response encryptPasswords() {
        passwordEncrypterService.encryptExistingPasswords();
        return Response.ok("Passwords encrypted successfully").build();
    }

}
