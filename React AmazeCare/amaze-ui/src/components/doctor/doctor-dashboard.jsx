import { useEffect, useState } from "react"
import PageHeader from "../all/page-header"
import DoctorSidebar from "./doctor-sidebar"
import { Link, useNavigate } from "react-router-dom"
import axios from "axios"


function DoctorDashboard() {
    const navigate = useNavigate()
    const fetchApi = "http://localhost:8080/api/doctor/get-one/v1"

    const [doctor, setDoctor] = useState(undefined)

    useEffect(() => {
        const fetchDoctor = async () => {
            try {
                const response = await axios.get(fetchApi, {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                })
                setDoctor(response.data)
            }
            catch (err) {
                err
            }
        }
        fetchDoctor()

    }, [])



    const [pendingList, setPendingList] = useState([])

    const pendingApi = "http://localhost:8080/api/appointment/upcoming/pending"

    useEffect(() => {
        const fetchPending = async () => {
            try {
                const response = await axios.get(pendingApi, {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                })

                setPendingList(response.data)

            } catch (err) {
                err
            }
        }
        fetchPending()
    }, [])

    const [confirmedList, setConfirmedList] = useState([]);


    const confirmApi = "http://localhost:8080/api/appointment/today/confirm"

    useEffect(() => {
        const fetchConfirm = async () => {
            try {
                const response = await axios.get(confirmApi, {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                })

                setConfirmedList(response.data)

            } catch (err) {
                err
            }
        }
        fetchConfirm()
    }, [])

    const ipdApi = "http://localhost:8080/api/admission/get-all-active/doc";

    const [ipdPatients, setIpdPatients] = useState([]);

    useEffect(() => {
        const fetchIpd = async () => {
            try {
                const res = await axios.get(ipdApi, {
                    params: { page: 0, size: 6 }, // small list for dashboard - select view all for all
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                });

                setIpdPatients(res.data?.data || []);
            } catch (err) {
                console.log(err);
            }
        };

        fetchIpd();
    }, []);



    return (
        <div className="container-fluid">
            <div className="row">
                <div className="col-sm-2 p-4">

                    <DoctorSidebar />
                </div>


                {/* MAIN CONTENT */}
                <div className="col-md-10 p-4">

                    <PageHeader
                        title={`Welcome Back, Dr. ${doctor?.name || 'Doctor'}`}
                        subtitle="Here's your day at glance"
                    />

                    <div className="container-fluid p-4">


                        {/* MIDDLE SECTION */}
                        <div className="row mb-4">

                            {/* LEFT: Pending */}
                            <div className="col-md-6 mb-4">
                                <div className="card h-100">
                                    <div className="card-body">

                                        {/* HEADER */}
                                        <div className="d-flex justify-content-between mb-3">
                                            <h5>Pending requests</h5>

                                            <button className="btn btn-link" onClick={() => navigate("/check-appointments")}>Review all</button>
                                        </div>

                                        <p className="text-muted">
                                            Confirm or reject incoming bookings.
                                        </p>

                                        {/* pending list */}
                                        {
                                            pendingList.length === 0 ? (
                                                <p className="text-muted">No Pending Appointments</p>
                                            ) : (
                                                pendingList.map((p, index) => (
                                                    <div
                                                        key={index}
                                                        className="bg-light rounded p-3 mb-3 d-flex justify-content-between align-items-center"
                                                    >
                                                        <div>
                                                            <h6 className="mb-1">{p.patient_name}</h6>
                                                            <small className="text-muted">{p.app_date}</small>
                                                        </div>

                                                        <span className="badge bg-warning text-dark">
                                                            PENDING
                                                        </span>
                                                    </div>
                                                ))
                                            )
                                        }

                                    </div>
                                </div>
                            </div>

                            {/* RIGHT: Confirmed */}
                            <div className="col-md-6 mb-4">
                                <div className="card h-100">
                                    <div className="card-body">

                                        {/* HEADER */}
                                        <div className="d-flex justify-content-between mb-3">
                                            <h5>Today's confirmed</h5>
                                            <button className="btn btn-link" onClick={() => navigate("/check-appointments")}>Open queue</button>
                                        </div>

                                        <p className="text-muted">
                                            Ready to consult — start when patient arrives.
                                        </p>

                                        {/* LIST */}
                                        {
                                            confirmedList.length === 0 ? (
                                                <p className="text-muted">No Confirmed Appointments Today</p>
                                            ) : (
                                                confirmedList.map((c, index) => (
                                                    <div
                                                        key={index}
                                                        className="bg-light rounded p-3 mb-3 d-flex justify-content-between align-items-center"
                                                    >
                                                        <div>
                                                            <h6 className="mb-1">{c.patient_name}</h6>
                                                            <small className="text-muted">
                                                                {c.app_time_slot} · {c.symptoms}
                                                            </small>
                                                        </div>

                                                        <button className="btn btn-outline-secondary"
                                                            onClick={() => navigate('/check-appointments')}>
                                                            Start
                                                        </button>
                                                    </div>
                                                ))
                                            )
                                        }

                                    </div>
                                </div>
                            </div>

                        </div>

                        {/* IPD SECTION */}
                        {/* ACTIVE IPD PATIENTS */}
                        <div className="card h-100">
                            <div className="card-body">

                                {/* HEADER */}
                                <div className="d-flex justify-content-between mb-3">
                                    <h5>Active IPD Patients</h5>

                                    <button
                                        className="btn btn-link"
                                        onClick={() => navigate("/ipd-patients")}
                                    >
                                        View all
                                    </button>
                                </div>

                                <p className="text-muted">
                                    Patients currently admitted under your care.
                                </p>

                                {/* LIST */}
                                {
                                    ipdPatients.length === 0 ? (
                                        <p className="text-muted">No active admissions</p>
                                    ) : (
                                        ipdPatients.map((p) => (
                                            <div
                                                key={p.reference_id}
                                                className="border rounded p-3 mb-3 d-flex justify-content-between align-items-center"
                                            >

                                                <div>
                                                    <h6 className="mb-1">{p.patient_name}</h6>
                                                    <small className="text-muted">
                                                        Room {p.room_no}-{p.bed_no} · {p.reason}
                                                    </small>
                                                </div>

                                                <span
                                                    className={
                                                        p.admission_status === "ADMITTED"
                                                            ? "badge bg-info text-dark"
                                                            : "badge bg-warning text-dark"
                                                    }
                                                >
                                                    {p.admission_status}
                                                </span>

                                            </div>
                                        ))
                                    )
                                }

                            </div>
                        </div>

                    </div>

                </div>
            </div>

        </div>
    )
}

export default DoctorDashboard