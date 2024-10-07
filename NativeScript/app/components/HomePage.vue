<!-- HomePage.vue -->
<template>
    <Page>
      <ActionBar title="Página de Inicio" class="action-bar" />
      <StackLayout>
        <Label text="Bienvenido a la Página de Inicio" class="title" />
        <Button text="Ir a la Segunda Página" @tap="goToSecondPage" />
      </StackLayout>
    </Page>
  </template>
  
  <script>
  import SecondPage from './SecondPage';
  
  export default {
    methods: {
      goToSecondPage() {
        try{
           this.$navigateTo(SecondPage);
           console.log("Se ha navegado a la segunda página");
        }catch(e){
          console.log(e);
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .title {
    font-size: 20px;
    text-align: center;
    margin: 20px 0;
  }
  </style>
  