import {takeEvery, take, select, put, call} from "redux-saga/effects"
import {studentModel} from "../pages/Student/reducers/studentReducer";
import apiCall from "../../apiCall";
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {userModel} from "../pages/AdminUser/reducers/userReducer";

function* watchStudent(action) {
    try {
        const res = yield apiCall({url: "/api/student/me", method: "get"})
        yield put(userModel.getCurrentUserSuccess(res.data))
    } catch (err) {
        toast.error(err.message)
        yield put(studentModel.yourActionFailure(err.message()));
    }
}

export function* userSaga() {
        yield takeEvery("user/getMe", watchStudent)
}