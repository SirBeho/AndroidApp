<template>
    <StackLayout class="task-container">
       
        <Button text="+ Agregar" @tap="addTask" class="btn-add" width="auto" />

        <ListView v-if="tasks.length > 0" for="task in tasks" class="list-view">
            <v-template>
                <StackLayout>
                    <StackLayout class="task-card" :class="task.status">
                        <Label :text="'Título: ' + task.title" class="task-title" />
                        <Label :text="'Descripción: ' + task.description" class="task-description" />

                        <Label :text="'Proyecto: ' + task.project.name" class="task-project" />
                        <FlexboxLayout justifyContent="space-between" width="100%">
                            <Label :text="'Fecha de Creación: ' + formatDate(task.created)" class="task-date" />
                            <Label :text="'Estado: ' + Estados(task.status)" class="task-status" />
                        </FlexboxLayout>

                    </StackLayout>
                </StackLayout>
            </v-template>
        </ListView>

        <!-- Mensaje si no hay tareas -->
        <Label v-else text="No hay tareas asignadas" class="empty-message" />
    </StackLayout>
</template>

<script>

import ModalAddTask from './ModalAddTask.vue';

export default {
    data() {
        return {
            user: {}, // Almacenamos la información del usuario aquí
            tasks: [] // Almacenamos las tareas aquí
        };
    },
    /* created() {
        const storedUser = applicationSettings.getString("loggedInUser");
        this.user = storedUser ? JSON.parse(storedUser) : null;

        if (!this.user) {
            console.log('Usuario no autenticado, back to login');
            this.$navigateTo(Login);
        }
    }, */
    async mounted() {
        this.fetchTasks(); // Llama a fetchTasks al montar el componente
    },
    methods: {
        formatDate(date) {
            //foramtea la fecha en español

            return new Date(date).toLocaleDateString();
        },
        async addTask() {
            console.log('Agregar tarea');
            try {

                const result = await this.$showModal(ModalAddTask, {
                    fullscreen: false,
                    width: 100,
                    height: 60,
                    context: {}
                });
                if (result) {
                    // Si el usuario agregó datos, los añadimos a la lista
                    this.tasks.push(result);
                }
            } catch (error) {
                console.error('Error abriendo modal:', error);
            }
        },
        Estados(status) {
            // Mapeo de los estados de las tareas
            const estadosMap = {
                inprogress: 'En Progreso',
                completed: 'Completada',
                pending: 'Pendiente'
            };
            return estadosMap[status] || 'Desconocido';
        },
        async fetchTasks() {
            console.log('Obteniendo tareas');
            try {

                /* const response = await fetch('http://10.0.2.2:8080/tasks/user/' + this.user.id, { */
                const response = await fetch('http://10.0.2.2:8080/tasks/user/' + 2, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

                if (response.ok) {
                    this.tasks = await response.json();
                } else {
                    console.error('Error al obtener tareas');
                }
            } catch (error) {
                console.error('Error en la solicitud:', error);
            }
        }
    }
};
</script>

<style scoped>
/* Estilos globales */
body {
    font-family: 'Roboto', sans-serif;
    background-color: #f0f2f5;
    color: #333;
}

/* Estilos para la barra superior */
.action-bar {
    background-color: #007BFF;
    color: white;
}

/* Mensaje si no hay tareas */
.empty-message {
    text-align: center;
    color: #888;
    font-size: 18px;
    margin-top: 50px;
}


/* Contenedor principal de las tareas */
.task-container {
    /*    padding: 20px; */
    background-color: #c0d0f075;
    min-height: 100vh;
}

/* Estilo básico para las tarjetas de tareas */
.task-card {
    background-color: white;

    border-radius: 50px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s, box-shadow 0.2s;

    border-width: 5px;
    border-left-width: 60px;
    border-style: solid;



}

.task-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
    border-radius: 50px;
}

/* Colores por estado */
.inprogress {
    border-color: #f9bd4f;
    /* Amarillo para en progreso */
}

.completed {
    border-color: #43aa8b;
    /* Verde para completadas */
}

.pending {
    border-color: #f94144;
    /* Rojo para pendientes */
}

/* Estilo de los títulos y textos */
.task-title {
    font-size: 20px;
    font-weight: bold;
    color: #333;

}

.task-description {
    font-size: 16px;
    color: #666;
}

.task-status {
    font-size: 14px;
    font-weight: bold;
    color: #333;
}

.task-project {
    font-size: 14px;
    color: #888;
}

.task-date {
    font-size: 12px;
    color: #999;
}

.separator {
    background-color: transparent;
}

.btn-add {
    height: 80px;
    /* Ocupa el 25% del ancho */
    horizontal-align: right;
    /* Alinea el botón a la derecha */
    background-color: #3c7dcf;
    color: white;
    border-radius: 30px;
    margin-top: 10px;
    padding: 10px 20px;
}

.btn-add:hover {
    background-color: #ee0909;
}

</style>