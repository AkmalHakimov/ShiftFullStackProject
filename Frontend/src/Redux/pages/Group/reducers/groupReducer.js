import {createSlice} from "@reduxjs/toolkit";

const slice = createSlice({
    name: "group",
    initialState: {
        groups:[],
        timeTables:[],
        mentors:[],
        name:"",
        startTime:"",
        endTime:"",
        roomId:"",
        dayType:"",
        modalVisible: false,
        clicked:false,
        title:"",
        price:"",
        mentorId:"",
        groupId:"",
        currentItm: ""
    },
    reducers: {
        getGroup:(state, action)=>{

        },
        getMentor:(state, action)=>{

        },
        getMentorSuccess:(state, action)=>{
            state.mentors = action.payload.res
        },
        yourActionFailure: (state, action) => {
            state.error = action.payload; // Update the error state
        },
        getGroupSuccess: (state, action) => {
            let x= JSON.parse(action.payload.res)
            state.groups = x
        },
        changeModalState:(state,action)=>{
            state.modalVisible = !state.modalVisible
        },
        changeClickedState:(state,action)=>{
            state.clicked = true
        },
        changeName:(state,action)=>{
            state.name = action.payload
        },
        changeStartTime:(state,action)=>{
            state.startTime = action.payload
        },
        changeEndTime:(state,action)=>{
            state.endTime = action.payload
        },
        changeDayType:(state,action)=>{
            state.dayType = action.payload
        },
        changeTitle:(state,action)=>{
            state.title = action.payload
        },
        changePrice:(state,action)=>{
            state.price = action.payload
        },
        changeMentorId:(state,action)=>{
            state.mentorId = action.payload
        },
        changeGroupId:(state,action)=>{
            state.groupId = action.payload
        },
        changeRoomId:(state,action)=>{
            state.roomId = action.payload
        },
        changeCurrentItmState:(state,action)=>{
            state.currentItm=""
        },
        reset: (state, action) => {
            state.name = ""
            state.startTime = ""
            state.endTime = ""
            state.dayType = ""
            state.roomId = ""
            state.title = ""
            state.price = ""
            state.groupId = ""
            state.mentorId = ""
        },
        saveGroup:(state, action)=>{
               let obj = {
                    name: state.name,
                    dayType: state.dayType,
                    startTime: state.startTime,
                    endTime: state.endTime,
                    roomId: state.roomId
                }
            action.payload = obj
        },
            saveDefaultTimeTable:(state, action)=>{
            let obj = {
                title: state.title,
                price: state.price,
                mentorId: state.mentorId,
                groupId: state.groupId,
            }
            action.payload = obj
        },
        saveTimeTableSuccess:(state, action)=>{
            let x = action.payload.res
            state.timeTables = x
        },
        editGroup: (state, action) => {
            state.modalVisible = true
            state.currentItm = action.payload
            state.name = action.payload.name
            state.endTime = action.payload.endTime
            state.startTime = action.payload.startTime
            state.roomId = action.payload.roomId
            state.dayType = action.payload.dayType
        }
    }
})

export const groupModel = slice.actions;
export default slice.reducer;