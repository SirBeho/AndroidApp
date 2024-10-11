<!-- ModalAddUser.vue -->
<template>
    <StackLayout class="modal-container">
        <Label :text="'Título: ' + task.title" class="task-title" />
        <Label :text="'Descripción: ' + task.description" class="task-description" />
        <Label :text="'Proyecto: ' + task.project.name" class="task-project" />

        <FlexboxLayout justifyContent="space-between" width="100%">
            <Label :text="'Creación: ' + formatDate(task.created)" class="task-project" />
            <Label :text="Estados(task.status)" class="task-status" width="auto" :class="task.status" />
        </FlexboxLayout>

        <FlexboxLayout class="btns">
            <Button width="100%" text="Procesar" @tap="procesar" class="btn1" />
            <Button width="100%" text="Eliminar" @tap="daleteTask" class="btn2" />
            <Button width="100%" text="Cerrar" @tap="close" class="btn3" />
        </FlexboxLayout>
    </StackLayout>
</template>

<script>
import { confirm, action, alert } from '@nativescript/core';
export default {
    props: {
        task: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            /* task: {
                nombre: '',
                taskname: '',
                email: '',
                password: '',

            }, */
            password2: '',
            errorMessage: '',
        };
    },
    /* created() {

       if (this.task_) {
           this.task = this.task_;
       } 
   },  */
    methods: {
        formatDate(date) {
            //foramtea la fecha en español

            return new Date(date).toLocaleDateString();
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

        async procesar() {

            const result = await confirm({
                title: "Procesar tarea",
                message: "¿Estás seguro de que deseas continuar?",
                okButtonText: "Sí",
                cancelButtonText: "No"
            });
            
            if (result) {
                try {
                    const response = await fetch(this.$config.VertxUrl+'/task/' + this.task.id, {
                        method: 'post',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    });
                    console.log('response:', response);

                    if (response.ok) {
                        this.task = await response.json();
                        alert('La tarea se envio a procesar correctamente');
                        this.$modal.close(this.task);
                    } else {

                        const errorMessage = await response.text() || 'Error desconocido';
                        alert(`Error: ${errorMessage}`);
                    }
                } catch (error) {
                    console.error('Error en la solicitud:', error);
                }
            }
        },
        async daleteTask() {

            try {
                const response = await fetch(this.$config.QuarkusUrl+'/tasks/' + this.task.id, {
                    method: 'delete',
                    headers: {
                        'Content-Type': 'application/json'
                    }

                });


                if (response.ok) {
                    this.task = await response.json();
                    console.log('Tarea eliminado:', this.task);
                    alert('Tarea eliminada correctamente');
                    this.$modal.close(this.task);
                } else if (response.status === 404) {
                    alert(`Esta Tarea no existe`);
                } else {
                    const errorMessage = await response.text() || 'Error desconocido';
                    alert(`Error al eliminar Tarea: ${errorMessage}`);
                }
            } catch (error) {
                console.error('Error en la solicitud:', error);
            }

        },
        close() {
            this.$modal.close();
        }

    }
};
</script>

<style scoped>
.modal-container {
    width: 900px;
    padding: 20px;
    background-color: white;
    margin: 10px;

}

.task-title {
    font-size: 20px;
    font-weight: bold;
    color: #333;

}

.task-description {
    font-size: 16px;
    color: #666;
    text-wrap: true;

}

.task-status {
    font-size: 14px;
    font-weight: bold;
    color: #333;

    height: 70px;
    /* Ocupa el 25% del ancho */
    horizontal-align: right;
    /* Alinea el botón a la derecha */
    color: white;
    border-radius: 30px;
    margin-top: 10px;
    padding: 10px 20px;

}

.task-project {
    font-size: 14px;
    color: #888;
}

.task-date {
    font-size: 12px;
    color: #999;
}

.modal-title {
    font-size: 20px;
    margin-bottom: 20px;
    padding-left: 50px;
}

.btns {
    width: 100%;
    margin-top: 20px;
    padding: 10px;
    background-color: #b0d6e62d;
    border-radius: 50px;
}

.btn1 {
    background-color: #007BFF;
    color: white;
}

.btn2 {
    background-color: #cf3e3e;
    color: white;
}

.btn2 {
    background-color: #6C757D;
    color: white;
}

.error-label {
    color: red;
    margin-top: 10px;
    text-align: center;
}

.inprogress {
    background-color: #f9bd4f;
    /* Amarillo para en progreso */
}

.completed {
    background-color: #43aa8b;
    /* Verde para completadas */
}

.pending {
    background-color: #f94144;
    /* Rojo para pendientes */
}
</style>