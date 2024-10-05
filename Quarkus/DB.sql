DROP DATABASE task_management;
CREATE DATABASE task_management;




CREATE DATABASE task_management;

USE task_management;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE projects (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE tasks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    status ENUM('pending', 'in_progress', 'completed') DEFAULT 'pending',
    created DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,	
    project_id INT,
    user_id INT,
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE comments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    task_id INT,
    user_id INT,
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE attachments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    file_path VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    task_id INT,
    FOREIGN KEY (task_id) REFERENCES tasks(id)
);


-- Insertar 3 usuarios
INSERT INTO users (username, password, email) VALUES
('john_doe', 'password123', 'john@example.com'),
('jane_smith', 'securePass456', 'jane@example.com'),
('alice_jones', 'alicePass789', 'alice@example.com');

-- Insertar 3 proyectos
INSERT INTO projects (name, description, user_id) VALUES
('Proyecto Alpha', 'Descripción del Proyecto Alpha', 1),  -- Asignado a john_doe
('Proyecto Beta', 'Descripción del Proyecto Beta', 2),   -- Asignado a jane_smith
('Proyecto Gamma', 'Descripción del Proyecto Gamma', 3);  -- Asignado a alice_jones

-- Insertar tareas para Proyecto Alpha
INSERT INTO tasks (title, description, status, created, project_id, user_id) VALUES
('Tarea 1 Alpha', 'Descripción de la Tarea 1 de Alpha', 'pending', '2024-10-10', 1, 1),
('Tarea 2 Alpha', 'Descripción de la Tarea 2 de Alpha', 'in_progress', '2024-10-15', 1, 1),
('Tarea 3 Alpha', 'Descripción de la Tarea 3 de Alpha', 'completed', '2024-10-20', 1, 1);

-- Insertar tareas para Proyecto Beta
INSERT INTO tasks (title, description, status, created, project_id, user_id) VALUES
('Tarea 1 Beta', 'Descripción de la Tarea 1 de Beta', 'pending', '2024-10-12', 2, 2),
('Tarea 2 Beta', 'Descripción de la Tarea 2 de Beta', 'in_progress', '2024-10-18', 2, 2),
('Tarea 3 Beta', 'Descripción de la Tarea 3 de Beta', 'completed', '2024-10-25', 2, 2),
('Tarea 4 Beta', 'Descripción de la Tarea 4 de Beta', 'pending', '2024-10-30', 2, 2);

-- Insertar tareas para Proyecto Gamma
INSERT INTO tasks (title, description, status, created, project_id, user_id) VALUES
('Tarea 1 Gamma', 'Descripción de la Tarea 1 de Gamma', 'completed', '2024-10-05', 3, 3),
('Tarea 2 Gamma', 'Descripción de la Tarea 2 de Gamma', 'pending', '2024-10-15', 3, 3),
('Tarea 3 Gamma', 'Descripción de la Tarea 3 de Gamma', 'in_progress', '2024-10-20', 3, 3),
('Tarea 4 Gamma', 'Descripción de la Tarea 4 de Gamma', 'completed', '2024-10-25', 3, 3),
('Tarea 5 Gamma', 'Descripción de la Tarea 5 de Gamma', 'pending', '2024-10-30', 3, 3);



-- Insertar comentarios para Tarea 1 Alpha
INSERT INTO comments (content, task_id, user_id) VALUES
('Comentario para Tarea 1 Alpha', 1, 1),
('Segundo comentario para Tarea 1 Alpha', 1, 2);

-- Insertar comentarios para Tarea 2 Alpha
INSERT INTO comments (content, task_id, user_id) VALUES
('Comentario para Tarea 2 Alpha', 2, 1);

-- Insertar comentarios para Tarea 3 Alpha
INSERT INTO comments (content, task_id, user_id) VALUES
('Comentario para Tarea 3 Alpha', 3, 2),
('Otro comentario para Tarea 3 Alpha', 3, 3);

-- Insertar comentarios para Tarea 1 Beta
INSERT INTO comments (content, task_id, user_id) VALUES
('Comentario para Tarea 1 Beta', 4, 2);

-- Insertar comentarios para Tarea 2 Beta
INSERT INTO comments (content, task_id, user_id) VALUES
('Comentario para Tarea 2 Beta', 5, 2),
('Comentario adicional para Tarea 2 Beta', 5, 3);

-- Insertar comentarios para Tarea 1 Gamma
INSERT INTO comments (content, task_id, user_id) VALUES
('Comentario para Tarea 1 Gamma', 6, 3);

-- Insertar comentarios para Tarea 2 Gamma
INSERT INTO comments (content, task_id, user_id) VALUES
('Comentario para Tarea 2 Gamma', 7, 1);
