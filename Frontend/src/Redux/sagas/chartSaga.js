import {takeEvery, select, put, call} from "redux-saga/effects"
import apiCall from "../../apiCall";
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {chartModel} from "../pages/Chart/reducers/chartReducer";

function* watchGroup(action) {
    try {
        const res = yield apiCall({url: "/api/group", method: "get"})
        let x = JSON.stringify(res.data)
        yield put(chartModel.getGroupSuccess({res: x}))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(chartModel.yourActionFailure(err.message));
    }
}

function* watchOddGroup(action) {
    try {
        const res = yield apiCall({url: "/api/group/odds", method: "get"})
        let x = JSON.stringify(res.data)
        yield put(chartModel.getEvenGroupSuccess({res: x}))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(chartModel.yourActionFailure(err.message));
    }
}
function* watchEvenGroup(action) {
    try {
        const res = yield apiCall({url: "/api/group/evens", method: "get"})
        let x = JSON.stringify(res.data)
        yield put(chartModel.getEvenGroupSuccess({res: x}))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(chartModel.yourActionFailure(err.message));
    }
}
function* watchRoom(action) {
    try {
        const res = yield apiCall({url: "/api/group/room", method: "get"})
        let x = JSON.stringify(res.data)
        yield put(chartModel.getRoomSuccess({res: x}))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(chartModel.yourActionFailure(err.message));
    }
}

export function* chartSaga() {
    yield takeEvery("chart/getGroup", watchGroup)
    yield takeEvery("chart/getEvenGroup", watchEvenGroup)
    yield takeEvery("chart/getOddGroup", watchOddGroup)
    yield takeEvery("chart/getRoom", watchRoom)
}