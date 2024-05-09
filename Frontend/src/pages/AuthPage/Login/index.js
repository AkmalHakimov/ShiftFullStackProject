import React, {useEffect, useRef} from 'react';
import {MDBContainer, MDBCol, MDBRow, MDBBtn, MDBIcon, MDBInput, MDBCheckbox } from 'mdb-react-ui-kit';
import "./index.css"
import {Link, useLocation, useNavigate} from "react-router-dom";
import {connect} from "react-redux";
import {loginModel} from "../../../Redux/pages/AuthPage/Login/reducers/loginReducer";
function Index(props) {

    const {loginReducer} = props
    const navigate = useNavigate()
    const location = useLocation()

    useEffect(()=>{
        if(loginReducer.navigateTo!=="" && location.pathname!==loginReducer.navigateTo){
            navigate(loginReducer.navigateTo)
        }
    })

    return (
        <MDBContainer fluid className="p-3 my-5 h-custom">

            <MDBRow>

                <MDBCol col='10' md='6'>
                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp" className="img-fluid" alt="Sample image" />
                </MDBCol>

                <MDBCol col='4' md='6'>

                    <div className="d-flex flex-row align-items-center justify-content-center">

                        <p className="lead fw-normal mb-0 me-3">Sign in with</p>

                        <MDBBtn floating size='md' tag='a' className='me-2'>
                            <MDBIcon fab icon='facebook-f' />
                        </MDBBtn>

                        <MDBBtn floating size='md' tag='a'  className='me-2'>
                            <MDBIcon fab icon='twitter' />
                        </MDBBtn>

                        <MDBBtn floating size='md' tag='a'  className='me-2'>
                            <MDBIcon fab icon='linkedin-in' />
                        </MDBBtn>

                    </div>

                    <div className="divider d-flex align-items-center my-4">
                        <p className="text-center fw-bold mx-3 mb-0">Or</p>
                    </div>

                    <MDBInput wrapperClass='mb-4' label='PhoneNumber' value={loginReducer.phone} onChange={(e)=>props.changePhone(e.target.value)}  id='formControlLg' type='tel' size="lg"/>
                    <MDBInput wrapperClass='mb-4' label='Password' id='formControlLg' value={loginReducer.password} onChange={(e)=>props.changePassword(e.target.value)} type='text' size="lg"/>

                    <div className="d-flex justify-content-between mb-4">
                        <MDBCheckbox name='flexCheck' value='' id='flexCheckDefault' label='Remember me' />
                        <a href="/register">Forgot password?</a>
                    </div>

                    <div className='text-center text-md-start mt-4 pt-2'>
                        <MDBBtn onClick={()=>props.loginUser()} className="mb-0 px-5" size='lg'>Login</MDBBtn>
                        <p className="small fw-bold mt-2 pt-1 mb-2">Don't have an account? <Link to={"/register"} className="link-danger">Register</Link></p>
                    </div>

                </MDBCol>

            </MDBRow>

        </MDBContainer>
    );
}

export default connect((state)=>(state),loginModel)(Index);