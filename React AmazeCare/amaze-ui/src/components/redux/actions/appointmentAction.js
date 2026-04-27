import axios from "axios";

export const GET_TODAY_APPOINTMENTS = "GET_TODAY_APPOINTMENTS";

export const getTodayAppointments = () => {
    return async (dispatch) => {

        const config = {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        };

        try {
            const response = await axios.get(
                "http://localhost:8080/api/appointment/all-today",
                config
            );

            dispatch({
                type: GET_TODAY_APPOINTMENTS,
                payload: response.data
            });

        } catch (err) {
            console.log(err);
        }
    };
};