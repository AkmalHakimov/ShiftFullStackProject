import axios from "axios";
import {toast} from "react-toastify";

export default ({url,method,data})=>{
    return  axios({
        baseURL: "http://localhost:8080",
        url: url,
        method: method,
        data: data,
        headers: {
            Authorization : localStorage.getItem("token")
        }
    }).catch(err=>{
       if(err.response.status===401){
           let x = localStorage.getItem("refreshToken")
           return axios({
               url: "http://localhost:8080/api/auth/refresh?refreshToken=" + x,
               method :"post"
           }).then((res)=>{
               localStorage.setItem("token",res.data)
           })
       }else if(err.response.status===403){
               toast.error("An error has accured")
       }else if(err.response.status===500){
           toast.error(err.response.data)
       }
        throw err
    })
}