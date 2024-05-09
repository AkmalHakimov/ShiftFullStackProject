import {createSlice} from "@reduxjs/toolkit";
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const slice = createSlice({
    name: "student",
    initialState: {
        students: [],
        firstName: "",
        lastName: "",
        age: "",
        description: "",
        phone: "",
        password: "",
        currentItm: "",
        modalVisible: false,
        error: null
    },
    reducers: {
        getStudent: (state, action) => {

        },
        yourActionFailure: (state, action) => {
            state.error = action.payload; // Update the error state
        },
        getStudentSuccess: (state, action) => {
            state.students = action.payload.res
        },
        changFirstName: (state, action) => {
            state.firstName = action.payload
        },
        changeLastName: (state, action) => {
            state.lastName = action.payload
        },
        changeAge: (state, action) => {
            state.age = action.payload
        },
        changeDescription: (state, action) => {
            state.description = action.payload
        },
        changePhone: (state, action) => {
            state.phone = action.payload
        },
        changePassword: (state, action) => {
            state.password = action.payload
        },
        changeModalState: (state, action) => {
            state.modalVisible = action.payload
        },
        changeCurrentItmState: (state, action) => {
            state.currentItm=""
        },
        reset: (state, action) => {
            state.firstName = ""
            state.lastName = ""
            state.age = ""
            state.description = ""
            state.phone = ""
        },
        saveStudent: (state, action) => {
            let obj;
            if(state.currentItm===""){
                obj = {
                    firstName: state.firstName,
                    lastName: state.lastName,
                    age: state.age,
                    description: state.description,
                    phone: state.phone,
                    password: state.password
                }
            }else {
                obj = {
                    firstName: state.firstName,
                    lastName: state.lastName,
                    age: state.age,
                    description: state.description,
                    phone: state.phone,
                }
            }
            action.payload = obj
        },
        delStudent: (state, action) => {

        },
        editStudent: (state, action) => {
            state.modalVisible = true
            state.currentItm = action.payload
            state.firstName = action.payload.firstName
            state.lastName = action.payload.lastName
            state.age = action.payload.age
            state.description = action.payload.description
            state.phone = action.payload.phone
        }
    }
})

export const studentModel = slice.actions;
export default slice.reducer;