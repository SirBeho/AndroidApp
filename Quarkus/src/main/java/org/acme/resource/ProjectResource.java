package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.Project;
import org.acme.repository.ProjectRepository;

@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResource {

    @Inject
    ProjectRepository projectRepository;

    @GET
    @Path("/{id}")
    public Response getProject(@PathParam("id") Long id) {
        Project project = projectRepository.findById(id);
        if (project == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
        }
        return Response.ok(project).build();
    }

    @POST
    public Response createProject(Project project) {
        projectRepository.persist(project);
        return Response.status(Response.Status.CREATED).entity(project).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProject(@PathParam("id") Long id, Project project) {
        Project existingProject = projectRepository.findById(id);
        if (existingProject == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
        }
        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        projectRepository.persist(existingProject);
        return Response.ok(existingProject).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProject(@PathParam("id") Long id) {
        Project project = projectRepository.findById(id);
        if (project == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
        }
        projectRepository.delete(project);
        return Response.noContent().build(); // Devuelve 204 No Content
    }
}
