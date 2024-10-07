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
    Router router = Router.router(vertx);

    // ruta de inicio ver todas las tareas
    router.get("/task").handler(this::getAllTasks);
   /*  router.post("/task/:taskId").handler(this::processTaskById);
    router.get("/task/status/:taskId").handler(this::getTaskStatus); */

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

  public void processTaskById(String taskId) {
    String selectQuery = "SELECT * FROM tasks WHERE id = ?";

    dbUtils.executeQuery(selectQuery, new JsonArray().add(taskId), new DBUtils.QueryCallback() {
        @Override
        public void onSuccess(JsonArray result) {
            if (result.size() > 0) {
                JsonObject task = result.getJsonObject(0);
                String status = task.getString("status");

                if ("pending".equalsIgnoreCase(status)) {
                    String updateQuery = "UPDATE tasks SET status = 'inprogress', progress = 0 WHERE id = ?";
                    dbUtils.executeQuery(updateQuery, new JsonArray().add(taskId), new DBUtils.QueryCallback() {
                        @Override
                        public void onSuccess(JsonArray result) {
                            System.out.println("Task " + taskId + " status changed to inprogress");

                            // Simular el progreso cada segundo durante 10 segundos
                            long timerId = vertx.setPeriodic(1000, id -> {
                                // Actualizar progreso en la base de datos
                                String progressUpdateQuery = "UPDATE tasks SET progress = progress + 10 WHERE id = ?";
                                dbUtils.executeQuery(progressUpdateQuery, new JsonArray().add(taskId), new DBUtils.QueryCallback() {
                                    @Override
                                    public void onSuccess(JsonArray result) {
                                        System.out.println("Task " + taskId + " progress updated");

                                        // Comprobar si se debe completar la tarea
                                        String checkProgressQuery = "SELECT progress FROM tasks WHERE id = ?";
                                        dbUtils.executeQuery(checkProgressQuery, new JsonArray().add(taskId), new DBUtils.QueryCallback() {
                                            @Override
                                            public void onSuccess(JsonArray result) {
                                                int progress = result.getJsonObject(0).getInteger("progress");
                                                if (progress >= 100) {
                                                    // Completar la tarea y detener el temporizador
                                                    vertx.cancelTimer(id);
                                                    String completeQuery = "UPDATE tasks SET status = 'completed' WHERE id = ?";
                                                    dbUtils.executeQuery(completeQuery, new JsonArray().add(taskId), new DBUtils.QueryCallback() {
                                                        @Override
                                                        public void onSuccess(JsonArray result) {
                                                            System.out.println("Task " + taskId + " status changed to completed");
                                                        }

                                                        @Override
                                                        public void onError(Throwable cause) {
                                                            System.err.println("Error updating task status to completed: " + cause.getMessage());
                                                        }
                                                    });
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable cause) {
                                                System.err.println("Error fetching progress: " + cause.getMessage());
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(Throwable cause) {
                                        System.err.println("Error updating task progress: " + cause.getMessage());
                                    }
                                });
                            });
                        }

                        @Override
                        public void onError(Throwable cause) {
                            System.err.println("Error updating task status to inprogress: " + cause.getMessage());
                        }
                    });
                } else {
                    System.out.println("Task " + taskId + " is not in pending state.");
                }
            } else {
                System.out.println("Task with id " + taskId + " not found.");
            }
        }

        @Override
        public void onError(Throwable cause) {
            System.err.println("Error fetching task: " + cause.getMessage());
        }
    });
}


  public void getTaskStatus(String taskId, RoutingContext context) {
    String selectQuery = "SELECT * FROM tasks WHERE id = ?";

    dbUtils.executeQuery(selectQuery, new JsonArray().add(taskId), new DBUtils.QueryCallback() {
      @Override
      public void onSuccess(JsonArray result) {
        if (result.size() > 0) {
          JsonObject task = result.getJsonObject(0);
          String status = task.getString("status");

          long remainingTime = 0;

          if ("inprogress".equalsIgnoreCase(status)) {
            // Aquí puedes implementar la lógica para calcular el tiempo restante
            // Por simplicidad, asumimos que solo ha pasado la mitad del tiempo
            remainingTime = 5000; // Ejemplo: 5 segundos restantes
          }

          JsonObject response = new JsonObject()
              .put("status", status)
              .put("remainingTime", remainingTime);

          context.response()
              .putHeader("content-type", "application/json")
              .end(response.encode());
        } else {
          context.response()
              .setStatusCode(404)
              .end("Task not found");
        }
      }

      @Override
      public void onError(Throwable cause) {
        context.response()
            .setStatusCode(500)
            .end("Database error: " + cause.getMessage());
      }
    });
  }

}
