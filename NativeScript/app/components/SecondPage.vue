<!-- SecondPage.vue -->
<template>
    <Page>
      <ActionBar title="Segunda Página" class="action-bar" />
      <StackLayout>
        <Label text="Estás en la Segunda Página" class="title" />
        <Button text="Regresar a la Página de Inicio" @tap="goBack" />
      </StackLayout>
    </Page>
  </template>
  
  <script>
  export default {
    methods: {
      goBack() {
        this.$navigateBack();
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
  