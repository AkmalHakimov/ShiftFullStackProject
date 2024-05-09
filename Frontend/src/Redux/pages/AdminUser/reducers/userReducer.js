import {createSlice} from "@reduxjs/toolkit";

const slice = createSlice({
    name: "user",
    initialState: {
        currentUser: ""
    },
    reducers: {
        getCurrentUserSuccess: (state, action) => {
            state.currentUser = action.payload
        },
        getMe: (state, action) => {

        }
    }
})

export const userModel = slice.actions;
export default slice.reducer;