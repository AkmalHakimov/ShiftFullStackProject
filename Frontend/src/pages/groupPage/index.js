import React, {useEffect, useState} from 'react';
import Rodal from "rodal";
import {connect, useSelector} from "react-redux";
import {groupModel} from "../../Redux/pages/Group/reducers/groupReducer";
import {toast, ToastContainer} from 'react-toastify';

function Index(props) {

    const {groupReducer} = props
    const clicked = useSelector((state) => state.groupReducer.clicked);
    useEffect(() => {
        props.getGroup()
        props.getMentor()
    }, [])
    return (
        <div>
            <div className={"d-flex align-items-center justify-content-between"}>
                <div className={"d-flex align-items-center gap-3"}>
                    <h1>Guruhlar</h1>
                    <h5 style={{marginTop: "12px"}}>Jami : {groupReducer.groups.length}</h5>
                </div>
                <div>
                    <button onClick={()=>props.changeModalState()} className={"btn btn-dark"}><i
                        className="fa-solid fa-plus"></i></button>
                </div>
            </div>
            <table className='table my-3'>
                <thead className='table table-dark'>
                <tr>
                    <th>Name</th>
                    <th>dayType</th>
                    <th>startTime</th>
                    <th>endTime</th>
                    <th>roomName</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {
                    groupReducer.groups.map((item, index) => {
                        return <tr key={index}>
                            <td>{item.name}</td>
                            <td>{item.dayType}</td>
                            <td>{item.startTime}</td>
                            <td>{item.endTime}</td>
                            <td>{item.roomName}</td>
                            <td>
                                <button onClick={() => props.editGroup(item)}
                                        className="btn btn-dark mx-3"><i
                                    className="fa-solid fa-pen-to-square"></i></button>
                            </td>
                        </tr>
                    })
                }
                </tbody>
            </table>

            <Rodal height={(groupReducer.timeTables.length===0 && clicked)?700:450} visible={groupReducer.modalVisible} onClose={()=>props.changeModalState()}>
                <div>
                    <h3 className={"my-3"}>AddGroup</h3>
                        <input value={groupReducer.name} onChange={(e)=>props.changeName(e.target.value)} type="text" placeholder={"name"} className={"form-control my-3"}/>
                    <select value={groupReducer.dayType} onChange={(e)=>props.changeDayType(e.target.value)} className={"form-select my-3"} defaultValue={""}>
                            <option value={""} disabled={true}>Hafta kunini Tanlang</option>
                            <option value="EVEN">Juft</option>
                            <option value="ODD">Toq</option>
                            <option value="ALL">Har kun</option>
                        </select>
                    {/*<select defaultValue={""}>*/}
                    {/*    <option value="" disabled={true}>Boshlanish vaqtini tanlang</option>*/}
                    {/*    <option value=""></option>*/}
                    {/*</select>*/}
                        <input value={groupReducer.startTime} onChange={(e)=>props.changeStartTime(e.target.value)} type="time" className={"form-control my-3"}/>
                        <input value={groupReducer.endTime} onChange={(e)=>props.changeEndTime(e.target.value)} type="time" className={"form-control my-3"}/>
                        <select value={groupReducer.roomId} onChange={(e)=>props.changeRoomId(e.target.value)} className={"form-select my-3"} defaultValue={""}>
                            <option value={""} disabled={true}>Xonani Tanlang</option>
                            <option value="1">Future Skills</option>
                            <option value="2">Google</option>
                            <option value="3">Motivation</option>
                            <option value="4">Security</option>
                        </select>
                        <button onClick={()=>props.saveGroup()} style={{marginLeft: "290px"}}
                                className={"btn btn-dark"}>Save
                        </button>
                    {

                        groupReducer.timeTables.length===0 && clicked?
                            <div>
                                <h3 className={"my-3"}>AddTimeTable</h3>
                                    <input value={groupReducer.title} onChange={(e)=>props.changeTitle(e.target.value)} type="text" placeholder={"timeTableTitle"} className={"form-control my-3"}/>
                                    <input value={groupReducer.price} onChange={(e)=>props.changePrice(e.target.value)} type="text" placeholder={"timeTablePrice"} className={"form-control my-3"}/>
                                    <select value={groupReducer.mentorId} onChange={(e)=>props.changeMentorId(e.target.value)} className={"form-select my-3"} defaultValue={""}>
                                        <option value={""} disabled={true}>Mentorni Tanlang</option>
                                        {
                                            groupReducer.mentors.map((item,index)=>{
                                                return <option key={index} value={item.id}>{item.firstName }  { item.lastName}</option>
                                            })
                                        }
                                    </select>
                                    <button onClick={()=>props.saveDefaultTimeTable()} style={{marginLeft: "290px"}}
                                            className={"btn btn-dark"}>Save
                                    </button>
                            </div>
                            : ""
                    }
                </div>
            </Rodal>
        </div>
    );
}

export default connect(state=>state,groupModel)(Index);