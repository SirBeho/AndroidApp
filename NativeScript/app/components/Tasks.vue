<template>
    <StackLayout class="task-container">
        <FlexboxLayout justifyContent="space-between" width="100%">
            <Label :text="message" class="message-label" />
            <Button text="+ Agregar" @tap="addTask" class="btn-add" width="auto" />
        </FlexboxLayout>

        <ListView v-if="tasks.length > 0" :items="tasks" class="list-view" @itemTap="onItemTap">
            <v-template>
                <StackLayout>
                    <StackLayout class="task-card" :class="item.status">
                        <Label :text="'Título: ' + item.title" class="task-title" />
                        <Label :text="'Descripción: ' + item.description" class="task-description" />
                        <Label :text="'Proyecto: ' + item.project.name" class="task-project" />
                        <FlexboxLayout justifyContent="space-between" width="100%">
                            <Label :text="'Fecha de Creación: ' + formatDate(item.created)" class="task-date" />
                            <Label :text="'Estado: ' + Estados(item.status)" class="task-status" />
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
import ModalEditTask from './ModalEditTask.vue';


export default {
    data() {
        return {
            user: {}, // Almacenamos la información del terea aquí
            tasks: [], // Almacenamos las tareas aquí
            message: "Esperando actualizaciones...",
            ws: null,
        };
    },
    created() {

        /*  const storedUser = applicationSettings.getString("loggedInUser");
         this.user = storedUser ? JSON.parse(storedUser) : null;
 
         if (!this.user) {
             console.log('Usuario no autenticado, back to login');
             this.$navigateTo(Login);
         } */
    },
    beforeDestroy() {
        if (this.ws) {
            this.ws.close();
        }
    },
    mounted() {
        this.fetchTasks(); // Llama a fetchTasks al montar el componente
        /* this.connectToWebSocket(); */
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
                    // Si el terea agregó datos, los añadimos a la lista
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

                /* const response = await fetch(this.$config.QuarkusUrl+'/tasks/user/' + this.user.id, { */
                const response = await fetch(this.$config.QuarkusUrl + '/tasks/user/' + 1, {
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
        },
        onItemTap(event) {
            const tappedTask = this.tasks[event.index];
            this.editTask(tappedTask);
        },
        async editTask(tarea) {
            console.log('Editar tarea:', tarea);
            try {
                const result = await this.$showModal(ModalEditTask, {
                    fullscreen: false,
                    width: 100,
                    height: 60,
                    props: {
                        task: tarea // Pasa el terea como prop
                    }
                });
                console.log('Resultado:', result);
                if (result) {

                    this.fetchTasks();
                }
            } catch (error) {
                console.error('Error abriendo modal:', error);
            }
        },
        async connectToWebSocket() {
            console.log("Conectando al WebSocket...");
            /* try {

                const response = await new WebSocket(this.$config.WebSocketUrl);
                if (response.ok) {
                    this.ws = response;

                    this.ws.onopen = () => {
                        console.log("**********Conectado al WebSocket***********");
                        this.sendMessage("Hola, servidor!");
                    };

                    this.ws.onmessage = (event) => {
                        console.log("Mensaje recibido:", event.data);
                        this.message = event.data;
                    };

                    this.ws.onclose = () => {
                        console.log("WebSocket cerrado. Reintentando...");
                        setTimeout(() => {
                            this.connectToWebSocket();
                        }, 5000); // Reintentar conexión en 5 segundos
                    };

                    this.ws.onerror = (error) => {
                        console.error("Error en WebSocket:", error);
                    };

                } else {
                    console.error('Error WebSocket', response);
                }



            } catch (error) {
                console.error('Error en la conexión WebSocket:', error);
            } */
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