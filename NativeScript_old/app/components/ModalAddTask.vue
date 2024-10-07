
<template>
    <StackLayout class="modal-container">
        <Label text="Agregar Tarea" class="modal-title" />

        <TextField v-model="task.title" hint="Título" class="campo" />
        <TextField v-model="task.description" hint="Descripción" class="campo" />


        <FlexboxLayout class="btns">
            <Button text="Elejir Usuario" @tap="showActionSheet" class="botum" />
            <Label  v-if="task.user" :text="selectedOption" class="campo" />
        </FlexboxLayout>


        <Label v-if="errorMessage" :text="errorMessage" class="error-label" />

        <FlexboxLayout class="btns">
            <Button width="100%" text="Guardar" @tap="saveTask" class="btn1" />
            <Button width="100%" text="Cerrar" @tap="close" class="btn2" />
        </FlexboxLayout>
    </StackLayout>
</template>

<script>


export default {
    data() {
        return {
            task: {
                title: '',
                description: '',
                project: 4,
                user: 0

            },
            selectedOption: null,
            users: [],
            password2: '',
            errorMessage: '',
        };
    },
    async mounted() {
        this.fetchUsers();
    },
    methods: {
        async fetchUsers() {
            console.log('Obtener usuarios');
            try {
                const response = await fetch('http://10.0.2.2:8080/users');
                const data = await response.json();
                this.tasks = data;
                console.log('Users:', this.tasks);
            } catch (error) {
                console.error('Error fetching users', error);
            }
        },
        validate() {
            this.errorMessage = '';
        
            /*validar campos vacios*/
            if (this.task.title === '' || this.task.description === '' || this.task.user == 0) {
                this.errorMessage = 'Todos los campos son obligatorios';
                return false;
            }

            this.errorMessage = '';
            return true;
        },
        async saveTask() {

            if (!this.validate()) {
                return;
            }

            try {
                const response = await fetch('http://10.0.2.2:8080/tasks', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(this.task)
                });

                console.log('enviado', JSON.stringify(this.task));
                console.log('response', response);

                if (response.ok) {
                    this.task = await response.json();
                    alert('Tarea creada correctamente');
                    this.$modal.close(this.task);
                } else {
                    const errorMessage = await response.text() || 'Error desconocido';
                    alert(`Error: ${errorMessage}`);
                }
            } catch (error) {
                console.error('Error en la solicitud:', error);
            }

        },
        close() {
            this.$modal.close();
        },
        async showActionSheet() {
            const actionOptions = {
                title: "Seleccione una opción",
                message: "¿Qué te gustaría hacer?",
                cancelButtonText: "Cancelar",
                actions: this.tasks.map((user) => user.name)
            };

            const result = await action(actionOptions);

            if (result !== 'Cancelar') {
                this.selectedOption = result;
                this.task.user = this.tasks.find((user) => user.name === result).id;
            }
        }
    }   
};

</script>

<style scoped>
.modal-container {
    background-color: white;
    border-radius: 30px;
    shadow-color: black;
    shadow-opacity: 0.5;
    shadow-radius: 10;
    width: 900px;
    /* Aquí defines el ancho del modal */
    height: 900px;
    /* Aquí defines la altura del modal */
    align-items: center;
    /* Centra el contenido horizontalmente */
    justify-content: center;
    /* Centra el contenido verticalmente */
}

.modal-title {
    font-size: 20px;
    margin-bottom: 20px;
    padding-left: 50px;
}

.btns {
    margin-top: 20px;
    width: 100%;
}


.btn1 {
    background-color: #007BFF;
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

.botum {
    width: 300px;
}

.campo {
    font-size: 16px;
}
</style>


