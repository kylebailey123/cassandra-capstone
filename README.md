# cassandra-capstone

## Project Structure
Java Version: 8.0.422-amzn
Maven Version: 3.6.1
CASANDRA Version: 5.0.2

## Cassandra table creation
### Create Keyspace
```
CREATE KEYSPACE task_manager_2 WITH REPLICATION = { 
    'class' : 'SimpleStrategy', 
    'replication_factor' : 1 
    };
```
### Create User Table
```
CREATE TABLE task_manager.users (
    user_id uuid,
    email text,
    username text,
    PRIMARY KEY(user_id)
);
```
### Create Task Table
```
CREATE TABLE task_manager.tasks (
    task_id uuid,
    user_id uuid,
    task_name text,
    task_description text,
    task_status text,
    PRIMARY KEY(task_id)
);
```
### Create Task by user id Table
```
CREATE TABLE task_manager.tasks_by_user_id (
    user_id uuid,
    task_id uuid,
    task_name text,
    task_description text,
    task_status text,
    PRIMARY KEY(user_id, task_id)
);
```