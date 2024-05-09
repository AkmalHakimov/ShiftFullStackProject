import {createSlice} from "@reduxjs/toolkit";

const slice = createSlice({
    name: "groupInfo",
    initialState: {
        students: [],
        mentors: [],
        timeTables: [],
        timeTableDays: [],
        groupId: "",
        lastTimeTable: "",
        lastTimeTableId: "",
        modalVisiblePlus: false,
        modalVisible: false,
        modalVisibleTime: false,
        modalVisibleTable: false,
        expressPlus: "",
        studentTimeTables: [],
        timeTableId: "",
        studentId: "",
        price: "",
        startDate: "",
        title: "",
        mentorId: "",
        priceTimeTable: "",
        startDateTimeTable: "",
        doBySelect:"",
        currentGroup:"",
        currentUser:""
    },
    reducers: {
        getStudent: (state, action) => {

        },
        getCurrentGroupSuccess: (state, action) => {
            state.currentGroup = action.payload;
        },
        getCurrentGroup: (state, action) => {

        },
        changeModalVisiblePlusTrue: (state, action) => {
            state.modalVisiblePlus = true
        },
        changeDoBySelect: (state, action) => {
            state.doBySelect = action.payload
        },
        changeModalVisiblePlusFalse: (state, action) => {
            state.modalVisiblePlus = false
        },
        changeModalVisible: (state, action) => {
            state.modalVisible = !state.modalVisible
        },
        changeModalVisibleTable: (state, action) => {
            state.modalVisibleTable = !state.modalVisibleTable
        },
        changeModalVisibleTime: (state, action) => {
            state.modalVisibleTime = !state.modalVisibleTime
        },
        changeExpressPlus: (state, action) => {
            state.expressPlus = action.payload
        },
        getStudentSuccess: (state, action) => {
            let x = JSON.parse(action.payload.res)
            state.students = x
        },
        getMentor: (state, action) => {

        },
        getMentorSuccess: (state, action) => {
            state.mentors = action.payload.res
        },
        getTimeTable: (state, action) => {

        },
        getTimeTableSuccess: (state, action) => {
            state.timeTables = action.payload.res
        },
        getTimeTableDay: (state, action) => {

        },
        getTimeTableDaySuccess: (state, action) => {
            state.timeTableDays = action.payload.res
        },
        getLastTimeTable: (state, action) => {

        },
        getLastTimeTableSuccess: (state, action) => {
            state.lastTimeTable = action.payload.res
        },
        changeGroupId: (state, action) => {
            state.groupId = action.payload
        },
        yourActionFailure: (state, action) => {

        },
        resetStudent: (state, action) => {
            state.studentId = ""
            state.price = ""
        },
        resetTimeTable: (state, action) => {
            state.title = ""
            state.priceTimeTable = ""
            state.mentorId = ""
            state.startDateTimeTable = ""
        },
        changeStudents: (state, action) => {
            state.students = []
        },
        changeStudentTimeTable: (state, action) => {
            state.studentTimeTables = []
        },
        changeTimeTableDay: (state, action) => {
            state.timeTableDays = []
        },
        changeLastTimeTable: (state, action) => {
            state.lastTimeTable = action.payload
        },
        getStudentTimeTable: (state, action) => {

        },
        getStudentTimeTableSuccess: (state, action) => {
            state.studentTimeTables = action.payload.res
        },
        editAbsent: (state, action) => {
        },
        changeTimeTableId: (state, action) => {
            state.timeTableId = action.payload
        },
        changeStudentId: (state, action) => {
            state.studentId = action.payload
        },
        changePrice: (state, action) => {
            state.price = action.payload
        },
        saveStudentTimeTable: (state, action) => {
            let obj = {
                timeTableId: state.lastTimeTable.id,
                studentId: state.studentId,
                price: state.price
            }
            action.payload = obj
        },
        changeStartDate: (state, action) => {
            state.startDate = action.payload
        },
        changeTitleTable: (state, action) => {
            state.title = action.payload
        },
        changePriceTable: (state, action) => {
            state.priceTimeTable = action.payload
        },
        changeStartDateTable: (state, action) => {
            state.startDateTimeTable = action.payload
        },
        changeMentorId: (state, action) => {
            state.mentorId = action.payload
        },

        StartDefaultTimeTable: (state, action) => {
            let obj = {
                startDate: state.startDate
            }
            action.payload = obj
        },
        SaveTimeTable: (state, action) => {
            let obj = {
                title: state.title,
                price: state.priceTimeTable,
                mentorId: state.mentorId,
                startDate: state.startDateTimeTable,
                groupId: state.groupId
            }
            action.payload = obj
        },
        selectTimeTable: (state, action) => {
            state.lastTimeTableId = action.payload
        },
        CompleteTimeTable: (state, action) => {

        },
        getCurrentUserSuccess: (state, action) => {
            state.currentUser = action.payload
        },
        getMe: (state, action) => {

        }
    }
})

export const groupInfoModel = slice.actions;
export default slice.reducer;