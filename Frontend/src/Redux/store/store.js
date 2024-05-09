import { configureStore } from '@reduxjs/toolkit'
import registerReducer from  "../pages/AuthPage/Register/reducers/registerReducer"
import createSagaMiddleware from 'redux-saga';
import {rootSaga} from "../sagas/rootSaga";
import loginReducer from "../pages/AuthPage/Login/reducers/loginReducer";
import studentReducer from "../pages/Student/reducers/studentReducer";
import mentorReducer from "../pages/Mentor/reducers/mentorReduceer";
import groupReducer from "../pages/Group/reducers/groupReducer";
import chartReducer from "../pages/Chart/reducers/chartReducer";
import groupInfoReducer from "../pages/GroupInfo/reducers/groupInfoReducer";
import userReducer from "../pages/AdminUser/reducers/userReducer";

const sagaMiddleware = createSagaMiddleware();

const store =  configureStore({
    reducer: {
        registerReducer,
        loginReducer,
        studentReducer,
        mentorReducer,
        groupReducer,
        chartReducer,
        groupInfoReducer,
        userReducer
    },
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(sagaMiddleware),
})

sagaMiddleware.run(rootSaga);

export default store

