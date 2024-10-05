package org.acme.resource; 

import org.acme.model.User; 
import org.acme.model.Task; 
import org.acme.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.acme.repository.TaskRepository; 
import org.mindrot.jbcrypt.BCrypt;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List; 

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserRepository userRepository;
    TaskRepository taskRepository; 

    @GET
    public Response getUsers() {
        return Response.ok(userRepository.listAll()).build();
    }

    @POST
    public Response createUser(User user) {
         if (userRepository.usernameExists(user.getUsername())) {
        return Response.status(Response.Status.CONFLICT)
                       .entity("El nombre de usuario ya existe").build();
        }
        // Hash de la contraseña antes de almacenarla
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.persist(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @GET
    @Path("/{id}/tasks")
    public List<Task> getUserTasks(@PathParam("id") Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @GET
    @Path("/{username}")
    public User getUser(@PathParam("username") String username) {
        return userRepository.findByUsername(username);
    }
}
