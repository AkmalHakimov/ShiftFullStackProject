import 'bootstrap/dist/css/bootstrap.min.css';
import {Route, Routes, useLocation, useNavigate} from "react-router-dom"
import "./index.css"
import Home from "./pages/Home";
import Login from "./pages/AuthPage/Login";
import Register from "./pages/AuthPage/Register";
import AdminHome from "./pages/AdminPage/AdminHome";
import AdminGroupInfo from "./pages/AdminPage/AdminGroupInfo";
import Group from "./pages/groupPage"
import Mentor from "./pages/groupPage"
import Student from "./pages/studentPage"
import {ToastContainer} from "react-toastify";
import React, {useEffect} from "react";
import apiCall from "./apiCall";

function App() {

    const location = useLocation();
    const navigate = useNavigate();
    const permissions = [
        {url:"/adminHome",roles:["ROLE_ADMIN"]},
        {url:"/",roles:["ROLE_ADMIN","ROLE_USER"]},
    ]

    function hasPermissions(){
        let count = 0;
        permissions.map((item,index)=>{
            if(item.url===location.pathname){
                count = count +1;
            }
        })
        if(count===1){
            if(localStorage.getItem("token")!==null){
                apiCall({
                    url:"/api/student/me",
                    method:"get"
                }).then((res)=>{
                    let s = false
                    permissions.map(item=>{
                        if(item.url===location.pathname){
                            res.data.roles.map(i1=>{
                                if(item.roles.includes(i1.name)){
                                    s = true
                                }
                            })
                        }
                    })
                    if(!s){
                        navigate("/404")
                    }
                })
            }else {
                navigate("/404")
            }

        }
    }

    useEffect(()=>{
        hasPermissions();
    },[])
    return (
        <div>
            <ToastContainer
                containerId="an id"
                draggable={false}
            />
            <Routes>
                <Route path='/' element={<Home></Home>}></Route>
                <Route path='/login' element={<Login></Login>}></Route>
                <Route path='/register' element={<Register></Register>}></Route>
                <Route path='/adminHome' element={<AdminHome></AdminHome>}></Route>
                <Route path='/adminHome/group' element={<Group></Group>}></Route>
                <Route path='/adminHome/student' element={<Student></Student>}></Route>
                <Route path='/adminHome/mentor' element={<Mentor></Mentor>}></Route>
                <Route path='/adminHome/adminGroup/:groupId' element={<AdminGroupInfo></AdminGroupInfo>}></Route>
            </Routes>
        </div>
    );
}

export default App

