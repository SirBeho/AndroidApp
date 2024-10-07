import { createStore } from 'redux';

// Estado inicial del store
const initialState = {
    users: []
};

// Acción para establecer los usuarios en el estado
const SET_USERS = 'SET_USERS';

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_USERS:
            console.log('Setting users:', action.payload);
            return {
                ...state,
                users: action.payload
            };
        case 'ADD_USER':
            return {
                ...state,
                users: [...state.users, action.payload]
            };
        case 'UPDATE_USER':
            return {
                ...state,
                users: state.users.map(user => 
                    user.id === action.payload.id ? action.payload : user
                )
            };
        default:
            return state;
    }
};

// Crea el store
const store = createStore(reducer);

// Función para cargar usuarios por defecto
export const fetchUsers = async () => {
    console.log('Fetching users...');
    try {
        const response = await fetch('http://10.0.2.2:8080/users');
        const data = await response.json();
        store.dispatch({
            type: SET_USERS,
            payload: data
        });
    } catch (error) {
        console.error('Error fetching users:', error);
    }
};

// Llamar a la función para cargar los usuarios al iniciar la aplicación
fetchUsers();

export default store;
