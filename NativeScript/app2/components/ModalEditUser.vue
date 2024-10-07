<!-- ModalAddUser.vue -->
<template>
    <StackLayout class="modal-container">
        <Label text="Modificar Usuario" class="modal-title" />
        <TextField v-model="user.name" hint="Nombre" />
        <TextField v-model="user.email" hint="Correo" />
        <TextField v-model="user.username" hint="Nombre de usuario" />

        <FlexboxLayout>
            <TextField width="100%" v-model="user.password" hint="Contraseña" secure="true" />
            <TextField width="100%" v-model="user.password2" hint="Repetir Contraseña" secure="true" />
        </FlexboxLayout>

        <Label v-if="errorMessage" :text="errorMessage" class="error-label" />

        <FlexboxLayout class="btns">
            <Button width="100%" text="Guardar" @tap="saveUser" class="btn1" />
            <Button width="100%" text="Eliminar" @tap="deleteUser" class="btn2" />
            <Button width="100%" text="Cerrar" @tap="close" class="btn3" />
        </FlexboxLayout>
    </StackLayout>
</template>

<script>
export default {
    props: {
        user: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            /* user: {
                nombre: '',
                username: '',
                email: '',
                password: '',

            }, */
            password2: '',
            errorMessage: '',
        };
    },
     /* created() {

        if (this.user_) {
            this.user = this.user_;
        } 
    },  */
    methods: {
        validate() {
            this.errorMessage = '';
            console.log('nombre', this.user.name);
            console.log('user', this.user.username);
            console.log('email', this.user.email);
            console.log('password', this.user.password);

            console.log('password2', this.password2);
            console.log('errorMessage', this.errorMessage);


            /*validar campos vacios*/
            if (this.user.name === '' || this.user.email === '' || this.user.username === '') {
                this.errorMessage = 'Todos los campos son obligatorios';
                return false;
            }

            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            if (!emailRegex.test(this.user.email)) {
                this.errorMessage = 'El formato del correo no es válido2';
                return false;
            }

            /*validar contraseñas iguales*/
            if (this.user.password !== this.user.password2) {
                this.errorMessage = 'Las contraseñas no coinciden';
                return false;
            }



            this.errorMessage = '';
            return true;
        },
        async saveUser() {

            if (!this.validate()) {
                return;
            }

            try {
                const response = await fetch('http://10.0.2.2:8080/users/' + this.user.id, {
                    method: 'put',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(this.user)
                });

                console.log('enviado', JSON.stringify(this.user));
                console.log('response', response);

                if (response.ok) {
                    this.user = await response.json();
                    alert('Usuario guardado correctamente');
                    this.$modal.close(this.user);
                } else {

                    const errorMessage = response.text() || 'Error desconocido';
                    alert(`Error al guardar el usuario: ${errorMessage}`);
                }
            } catch (error) {
                console.error('Error en la solicitud:', error);
            }

        },
        async deleteUser() {

            try {
                const response = await fetch('http://10.0.2.2:8080/users/' + this.user.id, {
                    method: 'delete',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                   
                });


                if (response.ok) {
                    this.user = await response.json();
                    console.log('Usuario eliminado:', this.user);
                    alert('Usuario eliminado correctamente');
                    this.$modal.close(this.user);
                } else if (response.status === 404){
                    alert(`Este usuario no existe`);

                }else {

                    const errorMessage = await response.text() || 'Error desconocido';
                    alert(`Error al eliminar el usuario: ${errorMessage}`);
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
</style>