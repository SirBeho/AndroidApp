package com.example.vertex;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.example.vertex.TaskManager.TaskProgress;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.layout.element.Table;

public class PdfReportGenerator {

    public static void generatePdf(DBUtils dbUtils, String taskId, TaskProgress taskProgress) {
        // Consulta SQL para obtener la tarea, usuario y proyecto asociados
        String query = "SELECT * FROM task_details WHERE task_id = ?";

        dbUtils.executeQuery(query, new JsonArray().add(taskId), new DBUtils.QueryCallback() {
            @Override
            public void onSuccess(JsonArray result) {
                // Asegurarse de que la consulta devuelva resultados
                if (result.size() > 0) {

                    JsonObject taskData = result.getJsonObject(0);

                    String id = taskData.getString("task_id");
                    String title = taskData.getString("task_title");
                    String description = taskData.getString("task_description");
                    String status = taskData.getString("task_status");
                    String created = taskData.getString("task_created");
                    String userName = taskData.getString("user_name");
                    String userUsername = taskData.getString("user_username");
                    String userEmail = taskData.getString("user_email");
                    String projectName = taskData.getString("project_name");
                    String projectDescription = taskData.getString("project_description");
                    String projectUserName = taskData.getString("project_user_name");
                    String projectUserEmail = taskData.getString("project_user_email");
                    String projectUserUsername = taskData.getString("project_user_username");

                    // Fechas de inicio y fin del progreso
                    Date startTime = taskProgress.startTime;
                    Date endTime = taskProgress.endTime;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                    // Definir la ruta para guardar el PDF
                    String currentDirectory = System.getProperty("user.dir");
                    currentDirectory += "/archivos/task_report_" + id + ".pdf";

                    try {
                        // Crear el PDF
                        PdfWriter writer = new PdfWriter(currentDirectory);
                        PdfDocument pdfDoc = new PdfDocument(writer);
                        Document document = new Document(pdfDoc);
            
                        // Establecer colores y estilo para el documento
                        DeviceRgb headerColor = new DeviceRgb(63, 81, 181);
                        DeviceRgb tableHeaderColor = new DeviceRgb(197, 202, 233);
            
                        // Encabezado del reporte
                        document.add(new Paragraph("Reporte de Finalización de Tarea")
                                .setBold()
                                .setFontSize(18)
                                .setFontColor(headerColor)
                                .setTextAlignment(TextAlignment.CENTER));
                       
            
                        // Introducción del reporte
                        document.add(new Paragraph("Este reporte detalla la información relacionada con la finalización de una tarea específica, incluyendo datos de la tarea, el usuario responsable, y el proyecto asociado. Además, se proporciona un resumen del progreso y las fechas importantes."));
                        
            
                        // Tabla resumen de la tarea
                        document.add(new Paragraph("Resumen de la Tarea").setBold().setFontSize(14).setFontColor(headerColor));
                        float[] columnWidths = {3, 5};
                        Table taskTable = new Table(UnitValue.createPercentArray(columnWidths));
                        taskTable.setWidth(UnitValue.createPercentValue(100));
                        taskTable.setBorder(new SolidBorder(1));
            
                        taskTable.addCell(new Paragraph("ID de la Tarea:").setBold().setBackgroundColor(tableHeaderColor));
                        taskTable.addCell(new Paragraph(id));
                        taskTable.addCell(new Paragraph("Título de la Tarea:").setBold().setBackgroundColor(tableHeaderColor));
                        taskTable.addCell(new Paragraph(title));
                        taskTable.addCell(new Paragraph("Descripción:").setBold().setBackgroundColor(tableHeaderColor));
                        taskTable.addCell(new Paragraph(description));
                        taskTable.addCell(new Paragraph("Fecha de Creación:").setBold().setBackgroundColor(tableHeaderColor));
                        taskTable.addCell(new Paragraph(created));
            
                        document.add(taskTable);
                        document.add(new Paragraph("\n"));
            
                        // Explicación sobre el usuario responsable
                        document.add(new Paragraph("El siguiente apartado muestra los datos del usuario responsable de la ejecución y finalización de la tarea.").setItalic());
                        
            
                        // Datos del usuario responsable
                        document.add(new Paragraph("Usuario Responsable").setBold().setFontSize(14).setFontColor(headerColor));
                        Table userTable = new Table(UnitValue.createPercentArray(columnWidths));
                        userTable.setWidth(UnitValue.createPercentValue(100));
                        userTable.setBorder(new SolidBorder(1));
            
                        userTable.addCell(new Paragraph("Nombre:").setBold().setBackgroundColor(tableHeaderColor));
                        userTable.addCell(new Paragraph(userName));
                        userTable.addCell(new Paragraph("Nombre de Usuario:").setBold().setBackgroundColor(tableHeaderColor));
                        userTable.addCell(new Paragraph(userUsername));
                        userTable.addCell(new Paragraph("Correo Electrónico:").setBold().setBackgroundColor(tableHeaderColor));
                        userTable.addCell(new Paragraph(userEmail));
            
                        document.add(userTable);
                        document.add(new Paragraph("\n"));
            
                        // Explicación sobre el proyecto asociado
                        document.add(new Paragraph("El siguiente apartado describe el proyecto asociado a la tarea, proporcionando información sobre el objetivo del proyecto y los datos de contacto del responsable.").setItalic());
                        
            
                        // Datos del proyecto
                        document.add(new Paragraph("Proyecto Asociado").setBold().setFontSize(14).setFontColor(headerColor));
                        Table projectTable = new Table(UnitValue.createPercentArray(columnWidths));
                        projectTable.setWidth(UnitValue.createPercentValue(100));
                        projectTable.setBorder(new SolidBorder(1));
            
                        projectTable.addCell(new Paragraph("Nombre del Proyecto:").setBold().setBackgroundColor(tableHeaderColor));
                        projectTable.addCell(new Paragraph(projectName));
                        projectTable.addCell(new Paragraph("Descripción del Proyecto:").setBold().setBackgroundColor(tableHeaderColor));
                        projectTable.addCell(new Paragraph(projectDescription));
                        projectTable.addCell(new Paragraph("Responsable del Proyecto:").setBold().setBackgroundColor(tableHeaderColor));
                        projectTable.addCell(new Paragraph(projectUserName));
                        projectTable.addCell(new Paragraph("Correo Electrónico:").setBold().setBackgroundColor(tableHeaderColor));
                        projectTable.addCell(new Paragraph(projectUserEmail));
                        projectTable.addCell(new Paragraph("Nombre de Usuario:").setBold().setBackgroundColor(tableHeaderColor));
                        projectTable.addCell(new Paragraph(projectUserUsername));
            
                        document.add(projectTable);
                        document.add(new Paragraph("\n"));
            
                        // Explicación sobre el progreso de la tarea
                        document.add(new Paragraph("El progreso de la tarea se detalla a continuación, incluyendo las fechas de inicio y finalización, las cuales permiten evaluar el cumplimiento de los plazos establecidos.").setItalic());
                        
            
                        // Fechas del progreso de la tarea
                        document.add(new Paragraph("Progreso de la Tarea").setBold().setFontSize(14).setFontColor(headerColor));
                        Table progressTable = new Table(UnitValue.createPercentArray(columnWidths));
                        progressTable.setWidth(UnitValue.createPercentValue(100));
                        progressTable.setBorder(new SolidBorder(1));
            
                        progressTable.addCell(new Paragraph("Fecha de Inicio:").setBold().setBackgroundColor(tableHeaderColor));
                        progressTable.addCell(new Paragraph(dateFormat.format(startTime)));
                        progressTable.addCell(new Paragraph("Fecha de Finalización:").setBold().setBackgroundColor(tableHeaderColor));
                        progressTable.addCell(new Paragraph(dateFormat.format(endTime)));
                        //duracion en segundos
                        long duration = (endTime.getTime() - startTime.getTime()) / 1000;
                        progressTable.addCell(new Paragraph("Duración :").setBold().setBackgroundColor(tableHeaderColor));
                        progressTable.addCell(new Paragraph(String.valueOf(duration)+" segundos"));


            
                        document.add(progressTable);
            
                        // Cerrar el documento
                        document.close();
                       
                       //System.out.println("Reporte PDF generado exitosamente");
                    }                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("No se encontró la tarea con ID: " + taskId);
                }
            }

            @Override
            public void onError(Throwable cause) {
                System.err.println("Error al ejecutar la consulta: " + cause.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        generatePdf(new DBUtils(null), "1", new TaskProgress("inprogress", 0, null, null, ""));
    }

}
