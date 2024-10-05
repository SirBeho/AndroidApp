<template>
  <Page>
    <ActionBar title="Login" class="action-bar" />
    <StackLayout class="login-container">
      <!-- Título -->
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

      <!-- Botón de envío -->
      
      <!-- Botón para obtener usuarios (pruebas) -->
      <Button text="Obtener Usuarios" @tap="fetchUsers" />

      <!-- ListView para mostrar usuarios -->
      <ListView v-if="users.length > 0" :items="users">
        <v-template>
          <Label :text="item.username" class="user-label" />
        </v-template>
      </ListView>
    </StackLayout>
  </Page>
</template>



<script>
export default {
  data() {
    return {
      username: '', // Almacena el valor del campo de usuario
      password: '', // Almacena el valor del campo de contraseña
      users: [] // Donde almacenarás los datos de la API
    };
  },
  methods: {
    async fetchUsers() {
      try {
        // Cambia localhost por 10.0.2.2
        //const response = await fetch('https://pokeapi.co/api/v2/pokemon/ditto', {
        const response = await fetch("https://10.0.2.2:8080/hello");


        if (!response.ok) {
          throw new Error('Error al obtener los usuarios');
        }

        const data = await response.json();
        console.log('Usuarios obtenidos:', data);
        this.users = data; // Asigna los datos al array users

      } catch (error) {
        console.error('Error en la solicitud:', error);
        alert('Error al obtener los usuarios. Por favor, intenta de nuevo.');
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
  padding: 30px;
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
  margin-bottom: 30px;
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
</style>
