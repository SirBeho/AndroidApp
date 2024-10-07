// DBUtils.java
package com.example.vertex;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.ResultSet;
import java.util.List;


public class DBUtils {

    private final SQLClient client;

    public DBUtils(Vertx vertx) {
        this.client = DBConfig.getJDBCClient(vertx);
    }
    

    public void executeQuery(String query, JsonArray params, QueryCallback callback) {
        client.getConnection(ar -> {
            if (ar.succeeded()) {
                SQLConnection connection = ar.result();
                connection.queryWithParams(query, params, res -> {
                    if (res.succeeded()) {
                        ResultSet resultSet = res.result();
                        List<JsonObject> rows = resultSet.getRows();

                        // Convertimos las filas a un JSON Array
                        JsonArray jsonArray = new JsonArray(rows);

                        // Llamamos al callback con el resultado
                        callback.onSuccess(jsonArray);

                    } else {
                        callback.onError(res.cause());
                    }
                    connection.close();
                });
            } else {
                callback.onError(ar.cause());
            }
        });
    }
    
    public interface QueryCallback {
        void onSuccess(JsonArray result);
        void onError(Throwable cause);
    }
}
