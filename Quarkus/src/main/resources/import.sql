
-- Insertar 3 usuarios
INSERT INTO users (name, username, password, email) VALUES
('John Doe','john_doe', 'admin', 'john@example.com'),
('Jane Smith','jane_smith', 'admin', 'jane@example.com'),
('Alice Jones','alice_jones', 'admin', 'alice@example.com');


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

CREATE VIEW task_details AS
SELECT 
    t.id AS task_id,
    t.title AS task_title,
    t.description AS task_description,
    t.status AS task_status,
    t.created AS task_created,
    u.name AS user_name,
    u.username AS user_username,
    u.email AS user_email,
    p.name AS project_name,
    p.description AS project_description,
    pu.name AS project_user_name,
    pu.username AS project_user_username,
    pu.email AS project_user_email
FROM 
    tasks t
JOIN 
    users u ON t.user_id = u.id 
JOIN 
    projects p ON t.project_id = p.id 
JOIN 
    users pu ON p.user_id = pu.id;