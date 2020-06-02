import React, {useEffect, useState, useMemo, useCallback} from "react";
import {getAllUserList} from "../../api/mainAPI";
import {useCookies} from "react-cookie";

const AdminPage = () => {
    const [users, setUsers] = useState([]);
    const [cookies, setCookie] = useCookies(['access-token']);

    const getAllUserListCallback = useCallback(async()=>{
        let result = await getAllUserList(cookies['access-token']);
        if(result.status === 'success'){
            setUsers(result.data);
        }
    }, []);

    useEffect(()=>{
        getAllUserListCallback();
    }, [users]);

    const renderTableHeader = () => {
        return(
            <tr>
                <td>userId</td>
                <td>email</td>
                <td>urls</td>
                <td>created</td>
                <td>modified</td>
            </tr>
        )
    }

    const renderTableBody = () => {
        debugger;
        console.log('users=>',users);
        return users.map(user=>{
            return(
                <tr>
                    <td>{user.userId}</td>
                    <td>{user.email}</td>
                    <td><button className={"ui button blue medium"}>urlInfos</button></td>
                    <td>{user.createdDate}</td>
                    <td>{user.lastModifiedDate}</td>
                </tr>
            )
        })
    }

    return (
        <div>
            <table className={"ui celled stripped table"}>
                <tbody>
                {renderTableHeader()}
                {renderTableBody()}
                </tbody>
            </table>
        </div>
    )
}

export default AdminPage;