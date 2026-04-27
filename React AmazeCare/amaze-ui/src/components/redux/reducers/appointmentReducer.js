import { GET_TODAY_APPOINTMENTS } from "../actions/appointmentAction";

const initialState = {
    appointments: []
};

const appointmentReducer = (state = initialState, action) => {

    switch (action.type) {

        case GET_TODAY_APPOINTMENTS:
            return {
                ...state,
                appointments: action.payload
            };

        default:
            return state;
    }
};

export default appointmentReducer;