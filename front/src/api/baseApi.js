import axios from 'axios'
import {renewAccessToken, setCookie} from "./mainAPI";
import {AWS_PUBLIC_IP, AWS_PUBLIC_PORT, TOAST_OPTION} from "../constant/constants";
import {toast} from "react-toastify";

export default axios.create({
        baseURL: `http://${AWS_PUBLIC_IP}:${AWS_PUBLIC_PORT}`
    }
)

export const bluemarlinapis = ()=> {
    let instance = axios.create({
        baseURL: `http://${AWS_PUBLIC_IP}:${AWS_PUBLIC_PORT}`
    });
    instance.interceptors.response.use(
        (response) => {
            setCookie('access-token', response.headers["x-auth-token"], {'httoOnly':true})
            return response;
        },
        async (error) => {
            await renewAccessToken();
            await toast.info('Session expired.', TOAST_OPTION);
            await window.location.reload();
        });
    return instance;
}