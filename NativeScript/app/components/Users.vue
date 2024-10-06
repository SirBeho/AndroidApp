<template>
    <StackLayout class="task-container">

        <Button text="+ Agregar" @tap="addUser" class="btn-add" width="auto" />

        <GridLayout rows="auto" columns="30, 4*, 6*, 3*" class="user-table-header">
            <Label text="ID" row="0" col="0" class="table-header" />
            <Label text="Nombre" row="0" col="1" class="table-header" />
            <Label text="Correo" row="0" col="2" class="table-header" />
            <Label text="Usuario" row="0" col="3" class="table-header" />
        </GridLayout>
        <GridLayout v-for="(user, index) in users" :key="index" rows="auto" columns="30, 4*, 6*, 3*"
            @tap="editUser(user)" class="user-row">
            <Label :text="user.id" col="0" class="cell" />
            <Label :text="user.name" col="1" class="cell" />
            <Label :text="user.email" col="2" class="cell" />
            <Label :text="user.username" col="3" class="cell" />
        </GridLayout>
    </StackLayout>
</template>

<style scoped>
/* Contenedor de la tabla */
.task-container {
    padding: 10px;
}

/* Estilo para la cabecera */
.user-table-header {
    background-color: #3c7dcf;
    padding: 10px;
    border-radius: 4px;
    color: white;
}

/* Estilo para las celdas de la cabecera */
.table-header {
    font-weight: bold;
    color: white;
    font-size: 16px;
    text-align: left;
    /* Alinea el texto a la izquierda */
    horizontal-align: left;
    /* NativeScript para asegurar alineación horizontal */
}

/* Filas de usuarios */
.user-row {
    border-bottom-width: 1px;
    border-color: #ccc;
    padding: 10px;
}

/* Estilo para las celdas */
.cell {
    font-size: 14px;
    padding: 5px;
    text-align: left;
    /* Alinea el texto a la izquierda */
    horizontal-align: left;
    /* NativeScript para asegurar alineación horizontal */
}

/* Alternar color de fondo de las filas */
.user-row:nth-child(even) {
    background-color: #f9f9f9;
}

.user-row:nth-child(odd) {
    background-color: #fff;
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


<script>

import ModalAddUser from './ModalAddUser.vue';
import ModalEditUser from './ModalEditUser.vue';
export default {
    data() {
        return {
            users: []
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
                this.users = data;
                console.log('Users:', this.users);
            } catch (error) {
                console.error('Error fetching users', error);
            }
        },
        async addUser() {
            console.log('Agregar usuario');
            try {

                const result = await this.$showModal(ModalAddUser, {
                    fullscreen: false,
                    width: 100,
                    height: 60,
                    context: {}
                });
                if (result) {
                    // Si el usuario agregó datos, los añadimos a la lista
                    this.users.push(result);
                }
            } catch (error) {
                console.error('Error abriendo modal:', error);
            }
        },
        async editUser(usuario) {
            console.log('Editar usuario:', usuario);
            try {
                const result = await this.$showModal(ModalEditUser, {
                    fullscreen: false,
                    width: 100,
                    height: 60,
                    props: {
                        user: usuario // Pasa el usuario como prop
                    }
                });
                console.log('Resultado:', result);
                if (result) {
                    // Si el usuario editó datos, los actualizamos en la lista
                    const index = this.users.findIndex(u => u.id === result.id);
                    this.users[index] = result;
                }
            } catch (error) {
                console.error('Error abriendo modal:', error);
            }
        }

    }
};
</script>