package com.example.vertex;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

public class TaskManager {

    private final Map<String, TaskProgress> tasks = new ConcurrentHashMap<>();
    private final WebClient webClient;
    private DBUtils dbUtils;

    // Constructor que acepta una instancia de Vertx para inicializar WebClient
    public TaskManager(Vertx vertx, DBUtils dbUtils) {
        this.webClient = WebClient.create(vertx);
        this.dbUtils = dbUtils;
        
    }

    public void startTask(String taskId) {
        
        if (tasks.containsKey(taskId)) {
            System.out.println("Task " + taskId + " is already being processed.");
            return;
        }

        
        int incremento = (int) (Math.random() * 5) + 1;


        TaskProgress taskProgress = new TaskProgress("inprogress", 0, new Date(), null, "");
        tasks.put(taskId, taskProgress);

        System.out.print("\033[H\033[2J");
        System.out.flush();

        long timerId = Vertx.currentContext().owner().setPeriodic(500, id -> {
            
            System.out.print("\033[H");
            System.out.flush();
     
            if (taskProgress.progress >= 100) {
               
                Vertx.currentContext().owner().cancelTimer(id);
                taskProgress.status = "completed";
                taskProgress.progress = 100;
                taskProgress.endTime = new Date();

                Duration duration = Duration.between(taskProgress.startTime.toInstant(), taskProgress.endTime.toInstant());
               
                //tasks.remove(taskId);
                taskProgress.log = "Tarea [" + taskId + "] [      Completed     ] 100% [" + String.format("%3s", duration.getSeconds()) + " seg] [TaskReport" + taskId + ".pdf]"; 
                
                PdfReportGenerator.generatePdf(dbUtils,taskId,taskProgress);
                updateTaskStatus(taskId);
                
                /* System.out.print("\033[H\033[2J");
                System.out.flush(); */
                
                
               
                //sendPushNotification(taskId, "Task Completed", "Task " + taskId + " has been completed.");
            }

            for (Map.Entry<String, TaskProgress> entry : tasks.entrySet()) {
                String codigo = entry.getKey();
                TaskProgress progress = entry.getValue();
                Duration duration = Duration.between(progress.startTime.toInstant(), Instant.now());
                
                if (progress.progress >= 100) {
                    progress.progress = 100;
                }
                if (progress.log != "") {
                    System.out.println(progress.log);
                    
                }else{
                    System.out.println("Tarea [" + codigo + "] [" +
                    "*".repeat(progress.progress / 5) + " ".repeat(20 - (progress.progress / 5)) + "]  " +
                    String.format("%2s", progress.progress)  + "%" + " [" + String.format("%3s", duration.getSeconds())  + " seg]" );
                }
               
            }
            System.out.print("Log:  ");
           
            taskProgress.progress += incremento;
        });
    }

    public JsonObject getTaskStatus(String taskId) {
        if (tasks.containsKey(taskId) && tasks.get(taskId).status.equals("inprogress")) {
            TaskProgress taskProgress = tasks.get(taskId);
            //todos los atributos de la clase
            return new JsonObject()
                    .put("status", taskProgress.status)
                    .put("progress", taskProgress.progress)
                    .put("startTime", taskProgress.startTime)
                    .put("endTime", taskProgress.endTime);
        } else {
            return new JsonObject().put("error", "Tarea {" + taskId + "} no se esta procesando.");
        }
    }

    public static class TaskProgress {
        String status;
        int progress;
        Date startTime;
        Date endTime;
        String log;

        TaskProgress(String status, int progress, Date startTime, Date endTime , String log) {
            this.status = status;
            this.progress = progress;
            this.startTime = startTime;
            this.endTime = endTime;
            this.log = log;
        }
    }

    //funcion para actualizar el estado de la tarea en la bf
    public void updateTaskStatus(String taskId) {
        String updateQuery = "UPDATE tasks SET status = 'completed' WHERE id = ?";
        dbUtils.executeQuery(updateQuery, new JsonArray().add(taskId) , new DBUtils.QueryCallback() {
            @Override
            public void onSuccess(JsonArray result) {
                System.out.println("Task " + taskId + " status changed to compelte.");
            }

            @Override
            public void onError(Throwable cause) {
                System.out.println("Error updating task status: " + cause.getMessage());
            }
        });
    }
}
