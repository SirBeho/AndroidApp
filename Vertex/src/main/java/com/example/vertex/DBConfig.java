package com.example.vertex;

import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.core.json.JsonObject;

public class DBConfig {

    public static SQLClient getJDBCClient(Vertx vertx) {
        JsonObject config = new  JsonObject()
        .put("url", "jdbc:mysql://host.docker.internal:3306/task_management")
        .put("driver_class", "com.mysql.cj.jdbc.Driver")
        .put("user", "root")
        .put("password", "@Motor0009896")
        .put("max_pool_size", 30);

        return JDBCClient.createShared(vertx, config);
    }
}
