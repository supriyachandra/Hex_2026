import { useEffect, useState } from "react"
import PatientNavbar from "./patient-sidebar"
import axios from "axios"
import { useNavigate } from "react-router-dom"
import { PatientStats } from "./patient-stats"
import { UpcomingApps } from "./upcoming-apps"
import { Doctors } from "./doctors"
import PageHeader from "../all/page-header"

function PatientDashboard() {
    const getPatientApi = "http://localhost:8080/api/patient/get-one"
    const [patient, setPatient] = useState(undefined)

    const navigate = useNavigate()

    useEffect(() => {

        const fetchPatient = async () => {

            const config = {
                headers: {
                    "Authorization": 'Bearer ' + localStorage.getItem('token')
                }
            }

            try {
                const response = await axios.get(getPatientApi, config)
                setPatient(response.data)
            }
            catch (err) {
                navigate('/login')
                console.log(err)
            }
        }

        fetchPatient()
    }, [])
    return (
        <div className="container-fluid p-4">

            <div className="row">

                {/* SIDEBAR */}
                <div className="col-sm-2 p-4">
                    <PatientNavbar />
                </div>

                {/* MAIN */}
                <div className="col-md-10 p-4">

                    <PageHeader
                        title={`Welcome Back, ${patient?.name || 'Guest'}`}
                        subtitle="Here's a quick look at your care"
                    />


                    <div className="row g-4 mt-2">

                        {/* LEFT */}
                        <div className="col-lg-6">
                            <div className="dashboard-card h-100 p-4">

                                <h5 className="mb-4 fw-semibold">Book an appointment</h5>

                                <div className="row g-3">

                                    {/* BY DOCTOR */}
                                    <div className="col-6">
                                        <div className="mini-card green h-100">
                                            <div>
                                                <i className="bi bi-hospital fs-3 mb-2"></i>
                                                <h6 className="fw-semibold">By Doctor</h6>
                                                <p className="small text-muted">
                                                    Browse specialists
                                                </p>
                                            </div>

                                            <button
                                                className="mini-link green"
                                                onClick={() => navigate("/book-by-doctor")}
                                            >
                                                Start →
                                            </button>
                                        </div>
                                    </div>

                                    {/* BY DATE */}
                                    <div className="col-6">
                                        <div className="mini-card blue h-100">
                                            <div>
                                                <i className="bi bi-calendar-event fs-3 mb-2"></i>
                                                <h6 className="fw-semibold">By Date</h6>
                                                <p className="small text-muted">
                                                    Pick a day
                                                </p>
                                            </div>

                                            <button
                                                className="mini-link blue"
                                                onClick={() => navigate("/book-by-date")}
                                            >
                                                Start →
                                            </button>
                                        </div>
                                    </div>

                                </div>

                            </div>
                        </div>

                        {/* RIGHT */}
                        <div className="col-lg-6">
                            <div className="dashboard-card h-100 p-4">

                                <div className="d-flex justify-content-between align-items-center mb-4">
                                    <h5 className="fw-semibold mb-0">Upcoming</h5>

                                    <button
                                        className="btn btn-sm btn-outline-primary"
                                        onClick={() => navigate("/appointments")}
                                    >
                                        View all
                                    </button>
                                </div>

                                <UpcomingApps />

                            </div>
                        </div>

                    </div>

                </div>
            </div>
        </div>


    )
}

export default PatientDashboard