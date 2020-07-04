import React, {useCallback, useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {getUrlSourceList} from "../../api/mainAPI";
import {useCookies} from "react-cookie";
import {logOut} from "../../actions";
import UrlSource from "./UrlSource";
import AdminPage from "./AdminPage";

const Main = () => {
    const [urlSource, setUrlSource] = useState({});
    const [showAdminPage, setShowAdminPage] = useState(false);
    const [cookies, setCookie] = useCookies(['access-token'])

    const userId = useSelector(state => state.auth.userId);
    const dispatch = useDispatch();

    const showAdmin = async () => {
        await setShowAdminPage(!showAdminPage);
   }

    const getUrlSources = useCallback(async () => {
        let response = await getUrlSourceList(cookies['access-token']);
        if(response.status  === 'success'){
            setUrlSource(response.data);
        }
    }, []);

    useEffect(()=>{
        getUrlSources();
    }, []);

    useEffect( ()=>{
    }, [urlSource]);

    const onLogOut = () => {
        dispatch(logOut());
        setCookie('access-token', null);
        localStorage.setItem('refresh-token', null);
    };

    const isEmtpyObject = (param) => {
        return Object.keys(param).length === 0;
    }

    const renderUrlSources = () => {
        if(!isEmtpyObject(urlSource)) {
            return (
                <div className={"key-list"}>
                    {urlSource.map((value, index) => {
                        return (
                            <div className={"ui celled grid"}>
                                <div className={"row"}>
                                    <UrlSource
                                        url = {value.url}
                                        urlId = {value.id}
                                        keyList={value.keywordDtoList.map(v=>{return v.word})}
                                        isScheduling={value.isScheduling}
                                    />
                                </div>
                            </div>
                        )
                    })}
                </div>
            )
        }
    };

    return (
        <div>
            <div className={"ui blue button massive"}>User : {userId}</div>
            <div className={"ui grey button massive"} onClick={onLogOut}>Logout</div>
            {userId==='admin' && <div className={"ui red button massive"} onClick={showAdmin}>Admin Page</div>}
            <div className="ui horizontal divider"/>
            <div>
                 {showAdminPage? <AdminPage />: renderUrlSources()}
            </div>

        </div>
    )
}


export default Main;