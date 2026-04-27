import { useState } from "react";
import axios from "axios";

export default function ScheduleForm({ doctorId, onSuccess }) {

    const [date, setDate] = useState("");
    const [start, setStart] = useState("");
    const [end, setEnd] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        //  basic validation
        if (start >= end) {
            alert("End time must be after start time");
            return;
        }

        try {
            await axios.post(
                `http://localhost:8080/api/schedule/add/${doctorId}`,
                {
                    date: date,
                    startTime: start,
                    endTime: end
                }, {
                    headers:{
                        Authorization: "Bearer "+ localStorage.getItem("token")
                    }
                }
            );

            onSuccess();

            // reset
            setDate("");
            setStart("");
            setEnd("");

        } catch (err) {
            console.log(err);
        }
    };

    return (
        <div className="card p-3 mt-3">

            <h6>Add Schedule</h6>

            <form onSubmit={handleSubmit}>

                {/* DATE */}
                <input
                    type="date"
                    className="form-control mb-2"
                    value={date}
                    onChange={(e) => setDate(e.target.value)}
                    required
                />

                {/* TIME */}
                <input
                    type="time"
                    className="form-control mb-2"
                    value={start}
                    onChange={(e) => setStart(e.target.value)}
                    required
                />

                <input
                    type="time"
                    className="form-control mb-2"
                    value={end}
                    onChange={(e) => setEnd(e.target.value)}
                    required
                />

                <button className="btn btn-primary btn-sm">
                    Add Schedule
                </button>

            </form>
        </div>
    );
}