package com.example.vertex;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainVerticle extends AbstractVerticle {

  // Simulación de almacenamiento de tareas
  private final Gson gson = new Gson();

  private final Map<String, Task> tasks = new HashMap<>();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);

    //ruta de inicio ver todas las tareas
    router.get("/task").handler(this::getAllTasks);
    router.post("/task/:taskId").handler(this::processTask);
    router.get("/task/status/:taskId").handler(this::getTaskStatus);

    // Inicializar servidor HTTP
    vertx.createHttpServer().requestHandler(router).listen(8888).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });

    
    tasks.put("1", new Task("1", "pending"));
    tasks.put("2", new Task("2", "pending"));
  }

  // Método para obtener todas las tareas
  private void getAllTasks(RoutingContext context) {
    String jsonResponse = gson.toJson(tasks);

    context.response()
      .putHeader("content-type", "application/json")
      .end(jsonResponse);
  }

  // Método para procesar la tarea
  private void processTask(RoutingContext context) {
    String taskId = context.pathParam("taskId");
    Task task = tasks.get(taskId);

    if (task == null) {
      context.response()
        .setStatusCode(404)
        .end("Task not found");
      return;
    }

    // Marcar la tarea como en progreso
    task.setStatus("in_progress");
    task.setStartTime(System.currentTimeMillis());

    // Simular un proceso que tarda 10 segundos
    vertx.setTimer(10000, id -> {
      task.setStatus("completed");
      task.setEndTime(System.currentTimeMillis());
      System.out.println("Task " + taskId + " completed.");
    });

    context.response()
      .putHeader("content-type", "application/json")
      .end("{\"message\": \"Task " + taskId + " is in progress\"}");
  }

  // Método para obtener el estado de la tarea
  private void getTaskStatus(RoutingContext context) {
    String taskId = context.pathParam("taskId");
    Task task = tasks.get(taskId);

    if (task == null) {
      context.response()
        .setStatusCode(404)
        .end("Task not found");
      return;
    }

    long remainingTime;
    if ("in_progress".equals(task.getStatus())) {
      remainingTime = 10000 - (System.currentTimeMillis() - task.getStartTime());
      remainingTime = Math.max(remainingTime, 0);
      context.response()
        .putHeader("content-type", "application/json")
        .end("{\"taskId\": \"" + taskId + "\", \"status\": \"" + task.getStatus() + "\", \"remainingTime\": " + remainingTime + "}");
    } else {
      context.response()
        .putHeader("content-type", "application/json")
        .end("{\"taskId\": \"" + taskId + "\", \"status\": \"" + task.getStatus() + "\"}");
    }
  }

  // Clase interna para representar una tarea
  class Task {
    private final String id;
    private String status;
    private long startTime;
    private long endTime;

    public Task(String id, String status) {
      this.id = id;
      this.status = status;
    }

    public String toJson() {
      return String.format("{\"id\": \"%s\", \"status\": \"%s\", \"startTime\": %d, \"endTime\": %d}", id, status, startTime, endTime);
    }

    public String getId() {
      return id;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public long getStartTime() {
      return startTime;
    }

    public void setStartTime(long startTime) {
      this.startTime = startTime;
    }

    public long getEndTime() {
      return endTime;
    }

    public void setEndTime(long endTime) {
      this.endTime = endTime;
    }
  }
}
