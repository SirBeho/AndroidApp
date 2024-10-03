package org.acme.resource; 

import org.acme.model.User; 
import org.acme.model.Task; 
import org.acme.repository.UserRepository; 
import org.acme.repository.TaskRepository; 


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

    @POST
    public Response createUser(User user) {
        if (userRepository.usernameExists(user.getUsername())) {
            return Response.status(Response.Status.CONFLICT).entity("Username already exists").build();
        }
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
