package com.example.vertex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import com.example.vertex.Task;

public class MainVerticle extends AbstractVerticle {

  // Simulación de almacenamiento de tareas

  private DBUtils dbUtils;
  private TaskManager taskManager;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    ObjectMapper mapper = DatabindCodec.mapper();
    ObjectMapper prettyMapper = DatabindCodec.prettyMapper();
    ;

    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    prettyMapper.registerModule(new JavaTimeModule());
    prettyMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    dbUtils = new DBUtils(vertx);
    taskManager = new TaskManager(vertx,dbUtils);
    Router router = Router.router(vertx);

    // ruta de inicio ver todas las tareas
    router.get("/task").handler(this::getAllTasks);
    router.post("/task/:taskId").handler(this::processTaskById);
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

  }

  private void getAllTasks(RoutingContext context) {
    dbUtils.executeQuery("SELECT * FROM tasks", new JsonArray(), new DBUtils.QueryCallback() {
      @Override
      public void onSuccess(JsonArray result) {
        context.response()
            .putHeader("content-type", "application/json")
            .end(result.encode());
      }

      @Override
      public void onError(Throwable cause) {
        context.response()
            .setStatusCode(500)
            .end("Database error: " + cause.getMessage());
      }
    });
  }

  public void processTaskById(RoutingContext context) {
    String taskId = context.pathParam("taskId");
    String selectQuery = "SELECT * FROM tasks WHERE id = ?";

    dbUtils.executeQuery(selectQuery, new JsonArray().add(taskId), new DBUtils.QueryCallback() {
        @Override
        public void onSuccess(JsonArray result) {
            if (result.size() > 0) {
                JsonObject task = result.getJsonObject(0);
                String status = task.getString("status");

                if ("pending".equalsIgnoreCase(status)) {
                    String updateQuery = "UPDATE tasks SET status = 'inprogress' WHERE id = ?";
                    dbUtils.executeQuery(updateQuery, new JsonArray().add(taskId), new DBUtils.QueryCallback() {

                          
                        @Override
                        public void onSuccess(JsonArray result) {
                            
                            System.out.println("Task " + taskId + " status changed to inprogress");
                            taskManager.startTask(taskId);
                            
                            context.response()
                                .putHeader("content-type", "application/json")
                                .end(task.encode());
                        }

                        @Override
                        public void onError(Throwable cause) {
                            System.err.println("Error updating task status to inprogress: " + cause.getMessage());
                        }
                    });
                } else {
                    System.out.println("Task " + taskId + " is not in pending state.");
                    // Enviar respuesta al cliente
                    context.response()
                        .setStatusCode(400)
                        .end("Task " + taskId + " is not in pending state.");
                }
            } else {
                System.out.println("Task with id " + taskId + " not found.");
                // Enviar respuesta al cliente
                context.response()
                    .setStatusCode(404)
                    .end("Task with id " + taskId + " not found.");
            }
        }

        @Override
        public void onError(Throwable cause) {
            System.err.println("Error fetching task: " + cause.getMessage());
        }
    });
}


  public void getTaskStatus(RoutingContext context) {
    String taskId = context.pathParam("taskId");
    JsonObject status = taskManager.getTaskStatus(taskId);

    context.response()
        .putHeader("content-type", "application/json")
        .end(status.encode());
  

   
  }

}
