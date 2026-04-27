import { createStore, applyMiddleware, combineReducers } from "redux";
import { thunk } from "redux-thunk";
import appointmentReducer from "./redux/reducers/appointmentReducer";

const reducers = combineReducers({
    appointmentReducer
});

export const store = createStore(
    reducers,
    applyMiddleware(thunk)
);