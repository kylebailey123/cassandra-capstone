package org.example;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Constant {

    private Constant() {
    }

     static class Method {
        private Method() {
        }
        public static final String SELECT = "SELECT";
        public static final String DELETE = "DELETE";
        public static final String UPDATE = "UPDATE";
        public static final String INSERT = "INSERT INTO";
    }

    static class Table {
        private Table() {
        }
        public static final String USERS = "users";
        public static final String TASKS = "tasks";
        public static final String TASKS_BY_USER_ID = "tasks_by_user_id";
    }

    public static String createOperation(String table, String columns, Object... values) {
        return Method.INSERT + " " + table + " (" + columns + ") VALUES (" + Arrays.stream(values).map(Object::toString).collect(Collectors.joining(",")) + ")";
    }

    public static String crudOperation(String method, String table, String coloumns, String conditions, Object... values) {
        return method + " " + table + " " + conditions + Arrays.stream(values).map(Object::toString).collect(Collectors.joining(","));
    }

}
