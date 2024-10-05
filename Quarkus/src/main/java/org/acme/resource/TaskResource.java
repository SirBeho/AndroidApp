package org.acme.resource; // Declaración de paquete para el recurso de tareas

import org.acme.model.Project;
import org.acme.model.Task; 
import org.acme.repository.TaskRepository; 

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;



@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @Inject
    TaskRepository taskRepository;

    @POST
    public Response createTask(Task task) {
        taskRepository.persist(task);
        return Response.status(Response.Status.CREATED).entity(task).build();
    }

    @GET
    @Path("/{id}")
    public Response  getTask(@PathParam("id") Long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new NotFoundException("Task not found");
        }
        return Response.ok(task).build(); 

    }


    @GET
    public List<Task> getAllTasks() {
        return taskRepository.listAll();
    }

    @GET
    @Path("/status/{status}")
    public List<Task> getTasksByStatus(@PathParam("status") String status) {
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
