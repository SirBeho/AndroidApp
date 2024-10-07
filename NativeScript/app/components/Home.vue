<template>
    <Page>
        <ActionBar title="Task Generator" class="action-bar" />
        <TabView @selectedIndexChange="onTabChange" androidTabsPosition="bottom">

            <TabViewItem title="Usuarios" iconSource="~/assets/icon/user.png">
                <Users ref="myUsersTab" />
            </TabViewItem>

            <TabViewItem title="Mis Tareas" iconSource="~/assets/icon/list.png">
                <MyTasks ref="myTasksTab" />
            </TabViewItem>

        </TabView>
    </Page>
</template>

<script>
// Importa las páginas como en el ejemplo anterior
import * as https from 'nativescript-https';
import MyTasks from './MyTasks.vue';
import Users from './Users.vue';

export default {
    components: {

        MyTasks,
        Users
    },
    methods: {
        onTabChange(event) {
            // Obtén el índice de la pestaña seleccionada
            const selectedIndex = event.value;

            // Si seleccionamos la pestaña de "Mis Tareas" (índice 0)
            if (selectedIndex === 0) {
                // Llama la función fetchTasks dentro de MyTasks
                this.$refs.myUsersTab.fetchUsers();
            }else if (selectedIndex === 1) {
                // Llama la función fetchUsers dentro de Users
                this.$refs.myTasksTab.fetchTasks();
            }    

            
        }
    }
};
</script>

<style scoped>
/* Estilo para el contenido principal */
.main-content {
    padding: 20px;
    background-color: #f5f5f5;
}

.action-bar {
    background-color: #007BFF;
    color: white;
}

/* Asegura que el TabView esté en la parte inferior */
TabView {
    background-color: #fff;
    border-top-width: 1px;
    border-top-color: #ddd;
}
</style>