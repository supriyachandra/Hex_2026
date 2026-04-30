import axios from "axios";
import { useEffect, useState } from "react";
import AdminSidebar from "./admin-sidebar";
import PageHeader from "../all/page-header";

export function AdminAppointments() {

    const api = "http://localhost:8080/api/appointment/get-all";

    const [appointments, setAppointments] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const size = 5;

    //  FETCH APPOINTMENTS
    const fetchAppointments = async (page = 0) => {
        try {
            const res = await axios.get(api, {
                params: { page, size },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });

            setAppointments(res.data?.data || []);
            setTotalPages(res.data?.totalPages || 0);
            setCurrentPage(page);

        } catch (err) {
            console.log(err);
        }
    };

    useEffect(() => {
        fetchAppointments(0);
    }, []);

    //  STATUS BADGE
    const getStatusBadge = (status) => {
        if (status === "PENDING") return "badge bg-warning text-dark";
        if (status === "CONFIRMED") return "badge bg-primary";
        if (status === "COMPLETED") return "badge bg-success";
        if (status === "CANCELLED") return "badge bg-danger";
        return "badge bg-secondary";
    };

    return (
        <div className="container-fluid p-4">
            <div className="row">

                {/* SIDEBAR */}
                <div className="col-sm-2">
                    <AdminSidebar />
                </div>

                <div className="col-md-10 p-4">

                    <PageHeader
                        title="Appointments"
                        subtitle="View and manage all upcoming appointments"
                    />

                    {/* TABLE */}
                    <div className="card p-3">

                        <table className="table align-middle">
                            <thead className="table-light">
                                <tr>
                                    <th>Patient</th>
                                    <th>Doctor</th>
                                    <th>Date</th>
                                    <th>Time</th>
                                    <th>Status</th>
                                </tr>
                            </thead>

                            <tbody>
                                {appointments.length === 0 ? (
                                    <tr>
                                        <td colSpan="5" className="text-center text-muted">
                                            No appointments found
                                        </td>
                                    </tr>
                                ) : (
                                    appointments.map((a) => (
                                        <tr key={a.app_id}>

                                            <td>
                                                <strong>{a.patient_name}</strong>
                                            </td>

                                            <td>
                                                <div>{a.doctor_name}</div>
                                                <small className="text-muted">
                                                    {a.specialization}
                                                </small>
                                            </td>

                                            <td>{a.app_date}</td>

                                            <td>{a.app_time_slot}</td>

                                            <td>
                                                <span className={getStatusBadge(a.status)}>
                                                    {a.status}
                                                </span>
                                            </td>

                                        </tr>
                                    ))
                                )}
                            </tbody>
                        </table>

                        {/* PAGINATION */}
                        <div className="d-flex justify-content-center mt-3">

                            <button
                                className="btn btn-outline-secondary me-2"
                                disabled={currentPage === 0}
                                onClick={() => fetchAppointments(currentPage - 1)}
                            >
                                Previous
                            </button>

                            <span className="align-self-center">
                                Page {currentPage + 1} of {totalPages}
                            </span>

                            <button
                                className="btn btn-outline-secondary ms-2"
                                disabled={currentPage === totalPages - 1}
                                onClick={() => fetchAppointments(currentPage + 1)}
                            >
                                Next
                            </button>

                        </div>

                    </div>

                </div>
            </div>
        </div>
    );
}