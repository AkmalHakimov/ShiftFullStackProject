import {takeEvery, select, put, call} from "redux-saga/effects"
import {mentorModel} from "../pages/Mentor/reducers/mentorReduceer";
import apiCall from "../../apiCall";
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function* watchMentor(action) {
    try {
        const res = yield apiCall({url: "/api/mentor", method: "get"})
        yield put(mentorModel.getMentorSuccess({res: res.data}))
    } catch (err) {
        toast.error(err.message)
        yield put(mentorModel.yourActionFailure(err.message()));
    }

}


function* saveMentor(action) {
    try {
        const currentState = yield select((state) => state.mentorReducer);
        if(action.payload.firstName==="" || action.payload.lastName==="" || action.payload.birthDate==="" || action.payload.phone){
            alert("qiymat kiritilmadi!")
            return;
        }
        if (currentState.currentItm === "") {
            const res = yield apiCall({url: "/api/mentor", method: "post", data: action.payload})
        } else {
            const res = yield apiCall({
                url: "/api/mentor?mentorId=" + currentState.currentItm.id,
                method: "put",
                data: action.payload
            })
            yield put(mentorModel.changeCurrentItmState())
        }
        yield call(watchMentor)
        yield put(mentorModel.reset());
        yield put(mentorModel.changeModalState())
    } catch (err) {
        toast.error(err.message)
        yield put(mentorModel.yourActionFailure(err));
    }
}

function* watchDelMentor(action) {
    try {
        const res = yield apiCall({url: "/api/mentor?mentorId=" + action.payload, method: "delete"})
        yield call(watchMentor)
    } catch (err) {
        toast.error(err.message)
        yield put(mentorModel.yourActionFailure(err));
    }
}

export function* mentorSaga() {
    yield takeEvery("mentor/getMentor", watchMentor)
    yield takeEvery("mentor/saveMentor", saveMentor)
    yield takeEvery("mentor/delMentor", watchDelMentor)
}