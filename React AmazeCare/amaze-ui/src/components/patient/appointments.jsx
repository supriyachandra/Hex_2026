import { useEffect, useState } from "react";
import PageHeader from "../all/page-header";
import PatientSidebar from "./patient-sidebar";
import axios from "axios";

export function Appointments() {

    const api = "http://localhost:8080/api/appointment/get/patient"
    const [apps, setApps] = useState([])

    // for pagination 
    const [page, setPage] = useState(0);

    const [totalPages, setTotalPages] = useState(0);
    const [totalRecords, setTotalRecords] = useState(0);



    useEffect(() => {
        const fetchApps = async () => {
            try {
                const response = await axios.get(api, {
                    params: {
                        page: page,
                        size: 6
                    },
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                })

                const sortedDate = response.data.data.sort((a, b) => {
                    return new Date(b.app_date) - new Date(a.app_date)
                })

                setApps(sortedDate)

                setTotalPages(response.data.totalPages);
                setTotalRecords(response.data.totalRecords);

            } catch (err) { err }
        }
        fetchApps()
    }, [page])

    const handleCancel = async (id) => {
        try {
            await axios.put(`http://localhost:8080/api/appointment/cancel/${id}`,
                {}, {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }
            )

            // Update ONLY the specific appointment in the UI
            setApps(prev => prev.map(app =>
                app.app_id === id ? { ...app, status: "CANCELLED" } : app
            ))



        } catch (err) {
            console.error(err)
        }
    }

    const [consult, setConsult] = useState(undefined)
    const [showModal, setShowModel] = useState(false)

    const handleView = async (id) => {
        const consultationApi = `http://localhost:8080/api/consultation/appointment/${id}`

        const response = await axios.get(consultationApi, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
        setConsult(response.data)
    }

    return (

        <div className="container-fluid p-4">
            <div className="row">

                {/* Sidebar */}
                <div className="col-sm-2 p-4">
                    <PatientSidebar />
                </div>

                {/* Main content */}
                <div className="col-md-10 p-4">

                    <PageHeader
                        title="My Appointments"
                        subtitle="Track, reschedule or cancel your visits"
                    />

                    {/* Card wrapper */}
                    <div className="container-fluid px-4">
                        <div className="row mt-4">
                            {apps.map((app) => (
                                <div className="col-md-6 col-xl-4 mb-4" key={app.app_id}>
                                    <div className="appointment-card p-4 h-100">

                                        {/* Top Section */}
                                        <div className="d-flex justify-content-between align-items-start mb-3">
                                            <div>
                                                <h5 className="fw-bold mb-1">
                                                    Dr. {app.doctor_name}
                                                </h5>
                                                <p className="text-muted small mb-0">
                                                    {app.doctor_specialization}
                                                </p>
                                            </div>

                                            <span className={`status-badge status-${app.status.toLowerCase()}`}>
                                                {app.status}
                                            </span>
                                        </div>

                                        {/* Info Section */}
                                        <div className="mb-3">
                                            <div className="info-row">
                                                <span>Date</span>
                                                <strong>{app.app_date}</strong>
                                            </div>
                                            <div className="info-row">
                                                <span>Time</span>
                                                <strong>{app.app_time_slot}</strong>
                                            </div>
                                        </div>

                                        {/* Symptoms */}
                                        <div className="symptoms-box mb-3">
                                            {app.symptoms}
                                        </div>

                                        {/* Actions */}
                                        <div className="mt-auto">

                                            {
                                                ((app.status === "PENDING" || app.status === "CONFIRMED")
                                                    && new Date(app.app_date) >= new Date().setHours(0, 0, 0, 0)
                                                ) && (
                                                    <button
                                                        className="btn btn-cancel w-100"
                                                        onClick={() => handleCancel(app.app_id)}
                                                    >
                                                        Cancel Appointment
                                                    </button>
                                                )}

                                            {app.status === "COMPLETED" && (
                                                <button
                                                    className="btn btn-view w-100"
                                                    onClick={() => {
                                                        console.log("CLICKED APP:", app);
                                                        console.log("APP ID SENT:", app.app_id);
                                                        handleView(app.app_id)
                                                        setShowModel(true)
                                                    }}
                                                >
                                                    View Consultation
                                                </button>
                                            )}

                                        </div>

                                    </div>
                                </div>
                            ))}
                        </div>
                        {showModal && (
                            <>
                                <div className="modal show fade d-block" tabIndex="-1">

                                    <div className="modal-dialog modal-lg modal-dialog-scrollable">
                                        <div className="modal-content">

                                            {/* HEADER */}
                                            <div className="modal-header">
                                                <div>
                                                    <h5 className="mb-0">{consult?.doctor_name}</h5>
                                                    <small className="text-muted">{consult?.date}</small>
                                                </div>

                                                <span className={`badge ${consult?.patientType === "OPD" ? "bg-primary" : "bg-warning text-dark"}`}>
                                                    {consult?.patientType}
                                                </span>

                                                <button
                                                    className="btn-close"
                                                    onClick={() => setShowModel(false)}
                                                ></button>
                                            </div>

                                            {/* BODY */}
                                            <div className="modal-body">

                                                {/* CONSULTATION */}
                                                {consult?.consultationDtoList?.map((c, index) => (
                                                    <div key={index} className="card mb-3 p-3">

                                                        <h6 className="fw-bold mb-2">Consultation</h6>

                                                        <p className="mb-1">
                                                            <strong>Symptoms:</strong> {c?.symptomNotes || "—"}
                                                        </p>

                                                        <p className="mb-1">
                                                            <strong>Examination:</strong> {c?.examination || "—"}
                                                        </p>

                                                        <p className="mb-1">
                                                            <strong>Diagnosis:</strong> {c?.diagnosis || "—"}
                                                        </p>

                                                        <p className="mb-0">
                                                            <strong>Treatment:</strong> {c.treatmentPlan || "—"}
                                                        </p>

                                                    </div>
                                                ))}

                                                {/* PRESCRIPTIONS */}
                                                <div className="card mb-3 p-3">
                                                    <h6 className="fw-bold mb-2">Prescriptions</h6>

                                                    {consult?.prescriptionDtoList?.length === 0 ? (
                                                        <p className="text-muted">No prescriptions</p>
                                                    ) : (
                                                        <ul className="list-group">
                                                            {consult?.prescriptionDtoList?.map((p, i) => (
                                                                <li key={i} className="list-group-item d-flex justify-content-between">
                                                                    <span>{p.medicine_name || "—"}</span>
                                                                    <span className="text-muted">{p.dosage || "—"}</span>
                                                                </li>
                                                            ))}
                                                        </ul>
                                                    )}
                                                </div>

                                                {/* TESTS */}
                                                <div className="card p-3">
                                                    <h6 className="fw-bold mb-2">Recommended Tests</h6>

                                                    {consult?.testDtoList?.length === 0 ? (
                                                        <p className="text-muted">No tests recommended</p>
                                                    ) : (
                                                        <ul className="list-group">
                                                            {consult?.testDtoList?.map((t, i) => (
                                                                <li key={i} className="list-group-item">
                                                                    {t.test_name || "—"}
                                                                </li>
                                                            ))}
                                                        </ul>
                                                    )}
                                                </div>

                                            </div>

                                        </div>
                                    </div>
                                </div>

                                {/* BACKDROP */}
                                <div className="modal-backdrop fade show"></div>
                            </>
                        )}
                    </div>

                </div>
            </div>
            <div className="d-flex justify-content-between align-items-center mt-4">

                {/* Records */}
                <div className="text-muted small">
                    Total: {totalRecords} appointments
                </div>

                {/* Pagination */}
                <div className="d-flex gap-2">

                    <button
                        className="btn btn-outline-secondary btn-sm"
                        disabled={page === 0}
                        onClick={() => setPage(page - 1)}
                    >
                        ← Prev
                    </button>

                    <span className="px-2 small align-self-center">
                        Page {page + 1} of {totalPages}
                    </span>

                    <button
                        className="btn btn-outline-secondary btn-sm"
                        disabled={page === totalPages - 1}
                        onClick={() => setPage(page + 1)}
                    >
                        Next →
                    </button>

                </div>
            </div>
        </div>

    )

}