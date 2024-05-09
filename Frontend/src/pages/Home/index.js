import React, {useEffect, useState} from 'react';

import 'rodal/lib/rodal.css';
import {useNavigate} from "react-router-dom";

function Index(props) {

    const navigate = useNavigate()

    useEffect(()=>{
        // if(localStorage.getItem("token")===null){
        //     navigate("/login")
        // }
    },[])

    return (
        <div className={"container my-4"}>
            <h1>Hello World</h1>
        </div>
    );
}

export default Index;