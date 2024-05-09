import {takeEvery, select, put, call} from "redux-saga/effects"
import apiCall from "../../apiCall";
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {groupModel} from "../pages/Group/reducers/groupReducer";
import {mentorModel} from "../pages/Mentor/reducers/mentorReduceer";

function* watchGroup(action) {
    try {
        const res = yield apiCall({url: "/api/group", method: "get"})
        let x = JSON.stringify(res.data)
        yield put(groupModel.getGroupSuccess({res: x}))
    } catch (err) {
        console.log(err)
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupModel.yourActionFailure(err.message));
    }

}

function* watchMentor(action) {
    try {
        const res = yield apiCall({url: "/api/mentor", method: "get"})
        yield put(groupModel.getMentorSuccess({res: res.data}))
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupModel.yourActionFailure(err.message));
    }

}

function* watchSaveGroup(action) {
    const currentState = yield select((state) => state.groupReducer);
    if(action.payload.name==="" || action.payload.dayType==="" || action.payload.startTime==="" || action.payload.endTime==="" || action.payload.roomId===""){
        alert("qiymat kirtilmadi!")
        return;
    }
    try {
        if (currentState.currentItm === "") {
            const res = yield apiCall({url: "/api/group", method: "post", data: action.payload})
            yield put(groupModel.changeGroupId(res.data.id))
            yield put(groupModel.changeClickedState(true))
        }else {
            const res = yield apiCall({
                url: "/api/group?groupId=" + currentState.currentItm.id,
                method: "put",
                data: action.payload
            })
            yield put(groupModel.changeCurrentItmState())
            yield put(groupModel.changeModalState())
            yield put(groupModel.reset())
            yield call(watchGroup)
        }

    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupModel.yourActionFailure(err.message));
    }
}

function* watchSaveTimeTable(action) {
    try {
        const currentState = yield select((state) => state.groupReducer);
            if (currentState.timeTables.length === 0) {
                const res = yield apiCall({url: "/api/timetable", method: "post", data: action.payload})
                let x = JSON.stringify(res.data)
                yield put(groupModel.saveTimeTableSuccess({res: x}))
                yield put(groupModel.reset());
                yield put(groupModel.changeModalState())
                yield call(watchGroup)            }
    } catch (err) {
        if(err.response.status!==403 && err.response.status!==401){
            toast.error(err.response.data)
        }
        yield put(groupModel.yourActionFailure(err.message));
    }
}

export function* groupSaga() {
    yield takeEvery("group/getGroup", watchGroup)
    yield takeEvery("group/saveGroup", watchSaveGroup)
    yield takeEvery("group/saveDefaultTimeTable", watchSaveTimeTable)
    yield takeEvery("group/getMentor", watchMentor)
}