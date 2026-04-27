import axios from "axios"
import { useEffect, useState } from "react"

export function PatientStats() {

    const statsApi = "http://localhost:8080/api/patient/stats"
    const [stat, setStat] = useState([])

    useEffect(() => {

        const fetchStats = async () => {
            try {
                const statResponse = await axios.get(statsApi, {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                })

                setStat(statResponse.data)

            } catch (err) {
                //err
            }
        }

        fetchStats()
    }, [])

    return (
    <div className="card p-3">
        <div className="row">
            {stat.map((s, index) => (
                <div className="col-md-4 mb-3" key={index}>
                    <div className="card stat-card text-center p-3 h-100">
                        <h6 className="text-muted mb-2">{s.title}</h6>
                        <h2 className="mb-0">{s.count}</h2>
                    </div>
                </div>
            ))}
        </div>
    </div>
)

}
