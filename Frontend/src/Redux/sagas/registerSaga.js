import {takeEvery, put, select, call} from "redux-saga/effects";
import {registerModel} from "../pages/AuthPage/Register/reducers/registerReducer";
import axios from "axios";

function* watchRegisterUser(action) {
    const response = yield axios.post("http://localhost:8080/api/auth/register", action.payload);
    yield put(registerModel.navigateToLogin())
}

export function* registerSaga() {
    yield takeEvery("register/registerUser", watchRegisterUser);
}