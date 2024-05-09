import React, {useEffect, useState} from 'react';
import apiCall from "../../apiCall";
import Rodal from "rodal";
import {connect} from "react-redux";
import {studentModel} from "../../Redux/pages/Student/reducers/studentReducer";
import {mentorModel} from "../../Redux/pages/Mentor/reducers/mentorReduceer";

function Index(props) {
    const {mentorReducer} = props
    useEffect(() => {
        props.getMentor()
    }, [])
    return (
        <div>
            <div className={"d-flex align-items-center justify-content-between"}>
                <div className={"d-flex align-items-center gap-3"}>
                    <h1>O'qituvchilar</h1>
                    <h5 style={{marginTop: "12px"}}>Jami : {mentorReducer.mentors.length}</h5>
                </div>
                <div>
                    <button onClick={()=>props.changeModalState()} className={"btn btn-dark"}><i
                        className="fa-solid fa-plus"></i></button>
                </div>
            </div>
            <table className='table my-3'>
                <thead className='table table-dark'>
                <tr>
                    <th>FirstName</th>
                    <th>LastName</th>
                    <th>BirthDate</th>
                    <th>Phone</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {
                    mentorReducer.mentors.map((item, index) => {
                        return <tr key={index}>
                            <td>{item.firstName}</td>
                            <td>{item.lastName}</td>
                            <td>{item.birthDate}</td>
                            <td>{item.phone}</td>
                            <td>
                                <button onClick={() => props.delMentor(item.id)}
                                        className="btn btn-dark"><i
                                    className="fa-solid fa-trash"></i></button>
                                <button onClick={() => props.editMentor(item)}
                                        className="btn btn-dark mx-3"><i
                                    className="fa-solid fa-pen-to-square"></i></button>
                            </td>
                        </tr>
                    })
                }
                </tbody>
            </table>

            <Rodal height={450} visible={mentorReducer.modalVisible} onClose={() => props.changeModalState()}>
                <div>
                    <h3 className={"my-3"}>AddMentor</h3>
                        <input required={true} value={mentorReducer.firstName} onChange={(e)=>props.changeFirstName(e.target.value)} type="text" placeholder={"firstName"} className={"form-control my-3"}/>
                        <input required={true} value={mentorReducer.lastName} onChange={(e)=>props.changeLastName(e.target.value)} type="text" placeholder={"lastName"} className={"form-control my-3"}/>
                        <input required={true} value={mentorReducer.phone} onChange={(e)=>props.changePhone(e.target.value)} type="text" placeholder={"phone"} className={"form-control my-3"}/>
                        <input required={true} value={mentorReducer.password} onChange={(e)=>props.changePassword(e.target.value)} type="text" placeholder={"password"} className={"form-control my-3"}/>
                        <input required={true} value={mentorReducer.birthDate} onChange={(e)=>props.changeBirthDate(e.target.value)} type="date" placeholder={"birthDate"} className={"form-control my-3"}/>
                        <button onClick={() => props.saveMentor()} style={{marginLeft: "290px"}}
                                className={"btn btn-dark"}>Save
                        </button>
                </div>
            </Rodal>
        </div>
    );
}

export default connect((state) => (state), mentorModel)(Index);
