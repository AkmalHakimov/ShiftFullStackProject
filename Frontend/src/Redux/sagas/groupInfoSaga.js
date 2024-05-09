import {takeEvery, takeLatest, select, put, call} from "redux-saga/effects"
import apiCall from "../../apiCall";
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {groupInfoModel} from "../pages/GroupInfo/reducers/groupInfoReducer";
import {userModel} from "../pages/AdminUser/reducers/userReducer";
import {studentModel} from "../pages/Student/reducers/studentReducer";

function* watchStudent(action) {
    try {
        const res = yield apiCall({url: "/api/student", method: "get"})
        let x = JSON.stringify(res.data)
        yield put(groupInfoModel.getStudentSuccess({res: x}))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}
function* watchMentor(action) {
    try {
        const res = yield apiCall({url: "/api/mentor", method: "get"})
        yield put(groupInfoModel.getMentorSuccess({res: res.data}))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}

function* watchTimeTable(action) {
    try {
        const currentState = yield select((state) => state.groupInfoReducer);
        const res = yield apiCall({url: "/api/timetable?groupId=" + currentState.groupId, method: "get"})
        yield put(groupInfoModel.getTimeTableSuccess({res: res.data}))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}

function* watchTimeTableDay(action) {
    try {
        const res = yield apiCall({url: "/api/timetableday", method: "get"})
        yield put(groupInfoModel.getTimeTableDaySuccess({res: res.data}))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}



function* watchStudentTimeTable() {
    try {
        yield call(watchLastTimeTable)
        const currentState = yield select((state) => state.groupInfoReducer);
        if(currentState.lastTimeTable){
            const res = yield apiCall({url: "/api/studenttimetable?id=" + currentState.lastTimeTable.id, method: "get"})
                yield put(groupInfoModel.getStudentTimeTableSuccess({res: res.data}))
            yield call(watchStudent);
        }
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}

function* watchEditAbsent(action) {
    try {
            let obj = {
                absent:action.payload.value
            }
        const res = yield apiCall({url: "/api/timetableday/absent?id=" + action.payload.item.id, method: "put",data:obj})
        yield call(watchTimeTableDay);
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}

function* watchSaveStudentTimeTable(action) {
    try {
        if(action.payload.timeTableId==="" || action.payload.studentId==="" || action.payload.price===""){
            alert("qiymat kiritilmadi!")
            return;
        }
        const res = yield apiCall({url: "/api/studenttimetable", method: "post",data:action.payload})
        if(res === undefined){
            alert("Birinchi dars boshlanish sanasini kiriting")
            return;
        }
        yield call(watchTimeTableDay);
        yield call(watchStudentTimeTable);
        yield put(groupInfoModel.changeModalVisiblePlusFalse())
        yield put(groupInfoModel.resetStudent())
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}

function* watchStartDefaultTimeTable(action) {
    try {
        const currentState = yield select((state) => state.groupInfoReducer);
            const res = yield apiCall({url: "/api/timetable/startTime?id=" + currentState.lastTimeTable.id, method: "put",data:action.payload})
            if(res === undefined){
                alert("Please, Select weekdays")
                return;
            }
            yield put(groupInfoModel.changeLastTimeTable(res.data));
            yield call(watchLastTimeTable)
            yield put(groupInfoModel.changeModalVisibleTime())
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}

function* watchSaveTimeTable(action) {
    try {
        if(action.payload.title==="" || action.payload.price==="" || action.payload.mentorId==="" || action.payload.startDate==="" || action.payload.groupId===""){
                alert("qiymat kiritilmadi!")
                return;
            }
                const res = yield apiCall({url: "/api/timetable/all", method: "post",data:action.payload})
        yield call(watchLastTimeTable)
        yield call(watchTimeTable)
        yield call(watchStudentTimeTable)
        yield call(watchTimeTableDay)
        yield put(groupInfoModel.resetTimeTable())
        yield put(groupInfoModel.changeModalVisibleTable())
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}

function* watchLastTimeTable(action) {
    try {
        const currentState = yield select((state) => state.groupInfoReducer);
        const res = yield apiCall({url: "/api/timetable/last?groupId=" + currentState.groupId, method: "get"})
        if(res.data.startDate){
            yield put(groupInfoModel.changeExpressPlus("able"))
        }
        if(currentState.doBySelect===""){
            yield put(groupInfoModel.getLastTimeTableSuccess({res: res.data}))
        }
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}

function* watchSelectTimeTable(action) {
    try {
        const res = yield apiCall({url: "/api/timetable/one?id=" + action.payload, method: "get"})
        yield put(groupInfoModel.changeLastTimeTable(res.data))
        yield put(groupInfoModel.changeDoBySelect(res.data))
        yield call(watchStudentTimeTable)
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}

function* watchCompleteTimeTable(action) {
    try {
        const currentState = yield select((state) => state.groupInfoReducer);
        const res = yield apiCall({url: "/api/timetable/complete?id=" + currentState.lastTimeTable.id, method: "put"})
        // console.log(res.data)
        yield call(watchLastTimeTable)
        yield put(groupInfoModel.changeLastTimeTable(""))
        yield put(groupInfoModel.changeExpressPlus(""))
        yield put(groupInfoModel.changeTimeTableDay())
        yield put(groupInfoModel.changeStudentTimeTable())
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}

function* watchGetCurrentGroup(action) {
    try {
        const res = yield apiCall({url: "/api/group/one/" + action.payload, method: "get"})
        yield put(groupInfoModel.getCurrentGroupSuccess(res.data))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupInfoModel.yourActionFailure(err.message));
    }
}

function* watchGetMe(action) {
    try {
        const res = yield apiCall({url: "/api/student/me", method: "get"})
        yield put(groupInfoModel.getCurrentUserSuccess(res.data))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(studentModel.yourActionFailure(err.message));
    }
}

export function* groupInfoSaga() {
    yield takeEvery("groupInfo/SaveTimeTable",watchSaveTimeTable)
    yield takeEvery("groupInfo/getCurrentGroup",watchGetCurrentGroup)
    yield takeEvery("groupInfo/getStudent", watchStudent)
    yield takeEvery("groupInfo/getMentor", watchMentor)
    yield takeEvery("groupInfo/getTimeTable", watchTimeTable)
    yield takeEvery("groupInfo/getTimeTableDay", watchTimeTableDay)
    yield takeEvery("groupInfo/getStudentTimeTable", watchStudentTimeTable)
    yield takeEvery("groupInfo/editAbsent",watchEditAbsent)
    yield takeEvery("groupInfo/saveStudentTimeTable",watchSaveStudentTimeTable)
    yield takeEvery("groupInfo/StartDefaultTimeTable",watchStartDefaultTimeTable)
    yield takeEvery("groupInfo/selectTimeTable",watchSelectTimeTable)
    yield takeEvery("groupInfo/getLastTimeTable", watchLastTimeTable)
    yield takeEvery("groupInfo/CompleteTimeTable",watchCompleteTimeTable)
    yield takeEvery("groupInfo/getMe", watchGetMe)
}