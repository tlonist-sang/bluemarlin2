import React, {useRef, useState} from "react";
import {registerUser} from "../../api/loginAPI";
import {CLOSE_POPUP, SUCCESS, TOAST_OPTION} from "../../constant/constants";
import {closePopup} from "../../actions/PopupActions";
import {toast} from "react-toastify";
import {useDispatch} from "react-redux";
import {getErrorMessage} from "../common/error"
import "../BlueMarlin.css"
import {checkUserNameExist} from "../../api/loginAPI";

const RegisterPopup = () => {

    const dispatch = useDispatch();
    const userIdRef = useRef(null);
    const emailRef = useRef(null);
    const passwordRef = useRef(null);
    const passwordConfirmRef = useRef(null);
    const [nameChecked, setNameChecked] = useState(false);

    const checkUserName = async () => {
        let userId = userIdRef.current.value;
        let result = await checkUserNameExist(userId);
        if(result.status === 'success'){
            setNameChecked(false);
            toast.error("Please make a different ID", TOAST_OPTION);
        }else{
            setNameChecked(true);
            toast.success("You can use ID", TOAST_OPTION);
        }
    }

    const onRegisterUser = async () => {
        let userId = userIdRef.current.value;
        let email = emailRef.current.value;
        let password = passwordRef.current.value;
        let passwordConfirm = passwordConfirmRef.current.value;
        if(nameChecked == false){
            toast.error("You should validate your ID before registering.", TOAST_OPTION);
            return;
        }

        await registerUser(userId, email, password, passwordConfirm)
            .then(res => {
                let {status, data} = res;
                if (status === 200 || data.status === SUCCESS) {
                    dispatch(closePopup(CLOSE_POPUP));
                    toast.success('successfully registered!', TOAST_OPTION);
                }
            })
            .catch(e => {
                let {status, data} = e.response;
                let errorMessage = getErrorMessage(data.errorCode);
                toast.error(`failed to register with cause : ${errorMessage}`, TOAST_OPTION);
            });
    }
    return(
        <div>
            <h4 className={"ui dividing header"}> Register for bluemarlin! </h4>
            <div>
                <table className={"ui very padded table"}>
                    <tbody>
                        <tr>
                            <td>Name</td>
                            <td>
                                <div className={"ui action input"}>
                                    <input type={"text"} ref={userIdRef} placeholder={"Your username"}/>
                                    <button className={"ui button"} onClick={()=>checkUserName()}>check!</button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>Email</td>
                            <td>
                                <div className={"ui input"}>
                                    <input type={"text"} ref={emailRef} placeholder={"Your email address"}/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>Password</td>
                            <td>
                                <div className={"ui input"}>
                                    <div className={"ui input"}>
                                        <input type={"password"} ref={passwordRef} placeholder={"password"}/>
                                    </div>
                                    <div className={"ui input"}>
                                        <input type={"password"} ref={passwordConfirmRef} placeholder={"confirm password"}/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div className={"register-button"}>
                <button className={"ui button big orange"} onClick={onRegisterUser}>Click to register!</button>
            </div>
        </div>
    )
}

export default RegisterPopup;