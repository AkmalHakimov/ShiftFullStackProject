import {all,fork} from "redux-saga/effects"
import {registerSaga} from "./registerSaga";
import {loginSaga} from "./loginSaga";
import {studentSaga} from "./studentSaga";
import {mentorSaga} from "./mentorSaga";
import {groupSaga} from "./groupSaga";
import {chartSaga} from "./chartSaga";
import {groupInfoSaga} from "./groupInfoSaga";
import {userSaga} from "./userSaga";



export function* rootSaga(){
    yield all([
            fork(registerSaga),
            fork(loginSaga),
            fork(studentSaga),
            fork(mentorSaga),
            fork(groupSaga),
            fork(chartSaga),
            fork(groupInfoSaga),
            fork(userSaga),
    ])
}