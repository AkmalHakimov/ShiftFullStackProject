import {createSlice} from "@reduxjs/toolkit";

const slice = createSlice({
    name:"register",
    initialState:{
        firstName:"",
        lastName:"",
        age:"",
        phone:"",
        password:"",
        navigateTo:""
    },
            reducers:{
                changeFirstName:(state, action)=>{
                    state.firstName = action.payload
                },
                changeLastName:(state, action)=>{
                    state.lastName = action.payload
                },
                changeAge:(state, action)=>{
                    state.age = action.payload
                },
                changePhone:(state, action)=>{
                    state.phone = action.payload
                },changePassword:(state, action)=>{
                    state.password = action.payload
                },
                registerUser:(state, action)=>{
                    let obj = {
                        firstName:state.firstName,
                        lastName:state.lastName,
                        age:state.age,
                        phone:state.phone,
                        password:state.password
                    }
                    action.payload = obj
                },
                navigateToLogin:(state,action)=>{
                    state.navigateTo = "/login"
                }
            }
})

export const registerModel = slice.actions;
export default slice.reducer