import {all, takeEvery, take, select, put, call, fork, } from "redux-saga/effects"
import {studentModel} from "../pages/Student/reducers/studentReducer";
import apiCall from "../../apiCall";
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function* watchStudent() {
        try {
            const res = yield apiCall({url: "/api/student", method: "get"})
            yield put(studentModel.getStudentSuccess({res: res.data}))
        } catch (err) {
                if(err.response.status!==403 && err.response.status!==401){
                    toast.error(err.response.data)
                }
            yield put(studentModel.yourActionFailure(err.message));
        }
}

function* watchSaveStudent(action) {
    try {
        const currentState = yield select((state) => state.studentReducer);
        if(action.payload.firstName==="" || action.payload.lastName==="" || action.payload.age==="" || action.payload.description==="" || action.payload.phone===""){
            alert("qiymat kiritilmadi!")
            return;
        }
        if (currentState.currentItm === "") {
            const res = yield apiCall({url: "/api/student", method: "post", data: action.payload})
        } else {
            const res = yield apiCall({
                url: "/api/student?userId=" + currentState.currentItm.id,
                method: "put",
                data: action.payload
            })
            yield put(studentModel.changeCurrentItmState)
        }
        yield call(watchStudent)
        yield put(studentModel.reset());
        yield put(studentModel.changeModalState(false))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(studentModel.yourActionFailure(err.message));
    }
}

function* watchDelStudent(action) {
    try {
        const res = yield apiCall({url: "/api/student?userId=" + action.payload, method: "delete"})
        yield call(watchStudent)
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(studentModel.yourActionFailure(err.message));
    }
}

export function* studentSaga() {
    yield takeEvery("student/getStudent", watchStudent)
    yield takeEvery("student/saveStudent", watchSaveStudent)
    yield takeEvery("student/delStudent", watchDelStudent)
}