import React, {useEffect, useState} from 'react';
import apiCall from "../../apiCall";
import Rodal from "rodal";
import {connect} from "react-redux";
import {studentModel} from "../../Redux/pages/Student/reducers/studentReducer";
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function Index(props) {

    const {studentReducer} = props
    useEffect(() => {
        props.getStudent();
    }, [])

    return (
        <div>
            <div className={"d-flex align-items-center justify-content-between"}>
                <div className={"d-flex align-items-center gap-3"}>
                    <h1>Talabalar</h1>
                    <h5 style={{marginTop: "12px"}}>Jami : {studentReducer.students.length}</h5>
                </div>
                <div>
                    <button onClick={()=>props.changeModalState(true)} className={"btn btn-dark"}><i
                        className="fa-solid fa-plus"></i></button>
                </div>
            </div>
            <table className='table my-3'>
                <thead className='table table-dark'>
                <tr>
                    <th>FirstName</th>
                    <th>LastName</th>
                    <th>Age</th>
                    <th>Phone</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {
                    studentReducer.students.map((item, index) => {
                        return <tr key={index}>
                            <td>{item.firstName}</td>
                            <td>{item.lastName}</td>
                                <td>{item.age}</td>
                            <td>{item.phone}</td>
                            <td>{item.description}</td>
                            <td>
                                {/*<button onClick={() => props.delStudent(item.id)}*/}
                                {/*        className="btn btn-dark"><i*/}
                                {/*    className="fa-solid fa-trash"></i></button>*/}
                                <button onClick={() => props.editStudent(item)}
                                        className="btn btn-dark mx-3"><i
                                    className="fa-solid fa-pen-to-square"></i></button>
                            </td>
                        </tr>
                    })
                }
                </tbody>
            </table>

            <Rodal height={500} visible={studentReducer.modalVisible} onClose={()=>props.changeModalState(false)}>
                <div>
                    <h3 className={"my-3"}>AddStudent</h3>
                        <input type="text" value={studentReducer.firstName} onChange={(e)=>props.changFirstName(e.target.value)} placeholder={"firstName"} className={"form-control my-3"}/>
                        <input type="text" value={studentReducer.lastName} onChange={(e)=>props.changeLastName(e.target.value)} placeholder={"lastName"} className={"form-control my-3"}/>
                        <input type="text" value={studentReducer.age} onChange={(e)=>props.changeAge(e.target.value)} placeholder={"age"} className={"form-control my-3"}/>
                        <input type="text" value={studentReducer.description} onChange={(e)=>props.changeDescription(e.target.value)} placeholder={"description"} className={"form-control my-3"}/>
                        <input type="text" value={studentReducer.phone} onChange={(e)=>props.changePhone(e.target.value)} placeholder={"phone"} className={"form-control my-3"}/>
                        <input type="text" value={studentReducer.password} onChange={(e)=>props.changePassword(e.target.value)} placeholder={"password"} className={"form-control my-3"}/>
                        <button onClick={()=>props.saveStudent()} style={{marginLeft: "290px"}}
                                className={"btn btn-dark"}>Save
                        </button>
                </div>
            </Rodal>
            <ToastContainer />
        </div>
    );
}

export default connect((state) => (state), studentModel)(Index);