import { useEffect, useState } from "react";
import axios from "axios";

export default function ScheduleList({ doctorId, refresh }) {

    const [schedules, setSchedules] = useState([]);

    useEffect(() => {
        fetchSchedules();
    }, [doctorId, refresh]);

    const fetchSchedules = async () => {
        try {
            const res = await axios.get(
                `http://localhost:8080/api/schedule/${doctorId}`,{
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            }
            );
            setSchedules(res.data);
        } catch (err) {
            console.log(err);
        }
    };

    const deleteSchedule = async (id) => {
        try {
            await axios.delete(
                `http://localhost:8080/api/schedule/${id}`,
                {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            }
            );
            
            // get schedules again after deleting.
            fetchSchedules();
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <div className="card p-3 mt-3">

            <h6>Schedules</h6>

            {schedules.length === 0 && (
                <div className="text-muted">No schedules added</div>
            )}

            {schedules.map((s) => (
                <div
                    key={s.id}
                    className="d-flex justify-content-between border p-2 mb-1"
                >
                    <span>
                        {s.date} • {s.startTime} - {s.endTime}
                    </span>

                    <button
                        className="btn btn-sm btn-danger"
                        onClick={() => deleteSchedule(s.id)}
                    >
                        Delete
                    </button>
                </div>
            ))}

        </div>
    );
}