<template>
  <Page>
    <ActionBar title="Login" class="action-bar" />
    <StackLayout class="login-container">
      <!-- Título -->
      <Label :text="`${this.$config.QuarkusUrl}/login`" font-size="8" />
      <Label text="Inicia sesión" class="title" />
      

      <!-- Contenedor del campo de usuario con icono -->
      <FlexboxLayout class="input-container">
        <Image src="~/assets/icons/user.png" class="input-icon" />
        <TextField v-model="username" hint="Username" class="input" />
      </FlexboxLayout>

      <!-- Contenedor del campo de contraseña con icono -->
      <FlexboxLayout class="input-container">
        <Image src="~/assets/icons/lock.png" class="input-icon" />
        <TextField v-model="password" hint="Password" secure="true" class="input" />
      </FlexboxLayout>

      <!-- Enlace para recuperar la contraseña -->
      <Label text="¿Olvidaste tu contraseña?" class="forgot-password" />

      <Label v-if="errorMessage" :text="errorMessage" class="error-label" />

      <!-- Botón de envío -->
      <Button text="Iniciar sesión" @tap="login" class="submit-button" />



    </StackLayout>
  </Page>
</template>



<script>

/* import * as applicationSettings from '@nativescript/core/application-settings';*/
 import Home from './Home';


export default {
  data() {
    return {
      username: 'john_doe',
      password: 'admin',
      errorMessage: ''
    };
  },
  methods: {
    async login() {
      if (this.username && this.password) {
        try {
          const response = await fetch(this.$config.QuarkusUrl+'/login', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              username: this.username,
              password: this.password
            })
          });
          

          if (response.ok) {
            const userData = await response.json();
           // applicationSettings.setString("loggedInUser", JSON.stringify(userData));

            this.errorMessage = '';
           

            console.log('Usuario autenticado:', userData);
            this.$navigateTo(Home, { transition: { name: 'slide' } });

          } else if (response.status === 401) {
            console.log('Credenciales incorrectas');
            this.errorMessage = 'Credenciales incorrectas. Intenta de nuevo.';
          } else {
            const errorText = await response.text();
            this.errorMessage = `Error: ${errorText}`;
            console.error('Error en la solicitud:', errorText);
          }
        } catch (error) {
          console.error('Error en la solicitud:', error);
          alert('Error en la conexión con el servidor.');
        }
      } else {
        alert('Por favor, complete ambos campos.');
      }


    }
  }
};
</script>



<style scoped>
.input-icon {
  width: 150px;
  height: 150px;
  margin-right: 10px;
  border: solid 1px #0056b3;
}



/* Estilos para el contenedor del formulario */
.login-container {
  padding: 55% 30%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: #fff;

  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);

  margin-bottom: 15px;
  border: 10px solid #ca2222;
}

/* Título del formulario */
.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 80px;
  color: #333;
}

/* Campos de entrada */
.input {
  width: 100%;
  padding: 25px 20px;
  font-size: 16px;
  border-radius: 25px;
  margin-bottom: 15px;
  border: 1px solid #ddd;


  outline: none;
}

.input:focus {
  border-color: #007BFF;
}

/* Enlace de recuperación de contraseña */
.forgot-password {
  font-size: 14px;
  color: #007BFF;
  margin-bottom: 30px;
  cursor: pointer;
  text-align: right;
  width: 100%;
}

/* Botón de envío */
.submit-button {
  background-color: #007BFF;
  color: white;
  padding: 15px;
  font-size: 16px;
  border-radius: 25px;
  text-align: center;
  width: 100%;
  box-shadow: 0 3px 6px rgba(0, 123, 255, 0.3);
}

.submit-button:hover {
  background-color: #0056b3;
}

/* Estilo de la barra superior */
.action-bar {
  background-color: #007BFF;
  color: white;
}

.error-label {
  color: red;
  font-size: 16px;
  margin-top: 10px;
  text-align: center;
}
</style>
