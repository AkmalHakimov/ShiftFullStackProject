import {createSlice} from "@reduxjs/toolkit";

const slice = createSlice({
    name: "chart",
    initialState: {
        groups:[],
        rooms:[],
    },
    reducers: {
        getGroup:(state, action)=>{

        },
        getGroupSuccess: (state, action) => {
            let x= JSON.parse(action.payload.res)
            state.groups = x
        },
        getEvenGroup:(state, action)=>{

        },
        getEvenGroupSuccess: (state, action) => {
            let x= JSON.parse(action.payload.res)
            state.groups = x
        },
        getOddGroup:(state, action)=>{

        },
        getOddGroupSuccess: (state, action) => {
            let x= JSON.parse(action.payload.res)
            state.groups = x
        },
        getRoom:(state, action)=>{

        },
        getRoomSuccess: (state, action) => {
            let x= JSON.parse(action.payload.res)
            state.rooms = x
        },
        yourActionFailure: (state, action) => {
            state.error = action.payload; // Update the error state
        },
    }
})

export const chartModel = slice.actions;
export default slice.reducer;