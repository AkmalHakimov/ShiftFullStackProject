import {createSlice} from "@reduxjs/toolkit";

const slice = createSlice({
    name: "mentor",
    initialState: {
        mentors: [],
        firstName: "",
        lastName: "",
        phone: "",
        password: "",
        birthDate: "",
        currentItm: "",
        modalVisible: false,
        error: null
    },
    reducers: {
        getMentor: (state, action) => {
        },
        yourActionFailure: (state, action) => {
            state.error = action.payload; // Update the error state
        },
        getMentorSuccess: (state, action) => {
            state.mentors = action.payload.res
        },
        changeFirstName: (state, action) => {
            state.firstName = action.payload
        },
        changeLastName: (state, action) => {
            state.lastName = action.payload
        },
        changePhone: (state, action) => {
            state.phone = action.payload
        },
        changePassword: (state, action) => {
            state.password = action.payload
        },
        changeBirthDate: (state, action) => {
            state.birthDate = action.payload
        },
        changeModalState: (state, action) => {
            state.modalVisible = !state.modalVisible
        },
        changeCurrentItmState: (state, action) => {
            state.currentItm = ""
        },
        reset: (state, action) => {
            state.firstName = ""
            state.lastName = ""
            state.birthDate = ""
            state.phone = ""
        },
        saveMentor: (state, action) => {
            let obj;
            if (state.currentItm === "") {
                obj = {
                    firstName: state.firstName,
                    lastName: state.lastName,
                    birthDate: state.birthDate,
                    phone: state.phone,
                    password: state.password
                }
            } else {
                obj = {
                    firstName: state.firstName,
                    lastName: state.lastName,
                    birthDate: state.birthDate,
                    phone: state.phone,
                }
            }
            action.payload = obj
        },
        delMentor: (state, action) => {
        },
        editMentor: (state, action) => {
            state.modalVisible = true
            state.currentItm = action.payload
            state.firstName = action.payload.firstName
            state.lastName = action.payload.lastName
            state.birthDate = action.payload.birthDate
            state.phone = action.payload.phone
        }
    }
})

export const mentorModel = slice.actions;
export default slice.reducer;