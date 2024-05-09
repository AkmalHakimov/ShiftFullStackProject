import React, {useEffect} from 'react';
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
// import "@fortawesome/fontawesome-free/css/all.min.css";
import {
    MDBBtn,
    MDBContainer,
    MDBRow,
    MDBCol,
    MDBCard,
    MDBCardBody,
    MDBCardImage,
    MDBInput,
    MDBIcon,
    MDBCheckbox,
    MDBRadio
}
    from 'mdb-react-ui-kit';
import {useLocation, useNavigate} from "react-router-dom";
import {connect} from "react-redux";
import {registerModel} from "../../../Redux/pages/AuthPage/Register/reducers/registerReducer";
import {registerSaga} from "../../../Redux/sagas/registerSaga";

function Index(props) {

    const {registerReducer} = props
    const navigate = useNavigate()
    const location = useLocation()

    useEffect(()=>{
        if(registerReducer.navigateTo!=="" && location.pathname!==registerReducer.navigateTo){
            navigate(registerReducer.navigateTo)
        }
    })


    return (
        <MDBContainer fluid>

            <MDBCard className='text-black m-5' style={{borderRadius: '25px'}}>
                <MDBCardBody>
                    <MDBRow>
                        <MDBCol md='10' lg='6' className='order-2 order-lg-1 d-flex flex-column align-items-center'>

                            <p className="text-center h1 fw-bold mb-3 mx-1 mx-md-4">Sign up</p>

                            <div className="d-flex flex-row align-items-center mb-4 ">
                                <MDBIcon fas icon="user me-3" size='lg'/>
                                <MDBInput label='Your first_name' id='form1' value={registerReducer.firstName}  onChange={(e)=>props.changeFirstName(e.target.value)} type='text' className='w-100'/>
                            </div>

                            <div className="d-flex flex-row align-items-center mb-4 ">
                                <MDBIcon fas icon="user me-3" size='lg'/>
                                <MDBInput label='Your last_name' id='form1' value={registerReducer.lastName} onChange={(e)=>props.changeLastName(e.target.value)}  type='text' className='w-100'/>
                            </div>

                            <div className="d-flex flex-row align-items-center mb-4 ">
                                <MDBIcon fas icon="user me-3" size='lg'/>
                                <MDBInput value={registerReducer.age} onChange={(e)=>props.changeAge(e.target.value)} label='Your age' id='form1'  type='number' className='w-100'/>
                            </div>

                            <div className="d-flex flex-row align-items-center mb-4">
                                <MDBIcon fas icon="mobile me-3" size='lg'/>
                                <MDBInput value={registerReducer.phone} onChange={(e)=>props.changePhone(e.target.value)} label='Your Phone'  id='form2' type='text'/>
                            </div>

                            <div className="d-flex flex-row align-items-center mb-4">
                                <MDBIcon fas icon="lock me-3" size='lg'/>
                                <MDBInput value={registerReducer.password} onChange={(e)=>props.changePassword(e.target.value)} label='Password' id='form3' type='text'/>
                            </div>
                            <MDBBtn onClick={()=>props.registerUser()} className='mb-4' size='lg'>Register</MDBBtn>

                        </MDBCol>

                        <MDBCol md='10' lg='6' className='order-1 order-lg-2 d-flex align-items-center'>
                            <MDBCardImage src='https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp' fluid/>
                        </MDBCol>

                    </MDBRow>
                </MDBCardBody>
            </MDBCard>

        </MDBContainer>
    );
}

export default connect((state)=>(state),{...registerModel}) (Index);