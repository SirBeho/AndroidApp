package org.acme.resource; // Declaración de paquete para el recurso de tareas

import org.acme.model.Project;
import org.acme.model.Task;
import org.acme.repository.ProjectRepository;
import org.acme.repository.TaskRepository;
import org.acme.repository.UserRepository;

import io.vertx.core.json.JsonObject;

import org.acme.model.User;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.beans.Transient;
import java.util.Date;
import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @Inject
    TaskRepository taskRepository;
    @Inject
    UserRepository userRepository;
    @Inject 
    ProjectRepository projectRepository;

    @GET
    public List<Task> getAllTasks() {
        return taskRepository.listAll();
    }

    @POST
    @Transactional
    public Response createTask(JsonObject json) {

        try {
            String title = json.getString("title");
            String description = json.getString("description");
            Long projectId = json.getLong("project");
            Long userId = json.getLong("user");
    
            Project project = projectRepository.findById(projectId);
            if (project == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("Proyecto no encontrado")
                    .build();
            }
    
            User user = userRepository.findById(userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario no encontrado")
                    .build();
            }
    
            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            task.setProject(project);
            task.setUser(user);
            task.setStatus("pending");
            task.setCreated(new Date());
    
            taskRepository.persist(task);
            return Response.status(Response.Status.CREATED).entity(task).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Error processing request: " + e.getMessage())
                .build();
        } 
    }

    @GET
    @Path("/{id}")
    public Response getTask(@PathParam("id") Long id) {

        return Response.ok(id * 6).build();

        /*
         * Task task = taskRepository.findById(id);
         * 
         * if (task == null) {
         * throw new NotFoundException("Task not found");
         * }
         * return Response.ok(task).build();
         */
    }

    @GET
    @Path("/user/{id}")
    public List<Task> getTasksByUserId(@PathParam("id") Long id) {

        return taskRepository.findByUserId(id);
    }

    @GET
    @Path("/status/{status}")
    public List<Task> getTasksByStatus(@PathParam("status") String status) {
        // renotma
        return taskRepository.findByStatus(status);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") Long id) {
        boolean deleted = taskRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Task not found");
        }
        return Response.noContent().build();
    }
}
