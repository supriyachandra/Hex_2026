import axios from "axios"
import { useEffect, useState } from "react"

export function UpcomingApps() {

    const api = "http://localhost:8080/api/appointment/upcoming"
    const [appointments, setAppointments] = useState([])

    useEffect(() => {

        const fetchApps = async () => {
            try {
                const response = await axios.get(api, {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                })

                setAppointments(response.data)

            } catch (err) {
                //err
            }

        }
        fetchApps()

    }, [])

    return (
        <div className="d-flex flex-column gap-3">
            {appointments.map((a, index) => (
                <div className="upcoming-card" key={index}>

                    <div className="d-flex justify-content-between mb-2">
                        <strong>{a.app_date}</strong>

                        <span className={`status-badge status-${a.status.toLowerCase()}`}>
                            {a.status}
                        </span>
                    </div>

                    <div className="small text-muted">
                        Dr. {a.doctor_name}
                    </div>

                    <div className="small text-muted">
                        {a.doctor_specialization}
                    </div>

                </div>
            ))}
        </div>

    )

}
