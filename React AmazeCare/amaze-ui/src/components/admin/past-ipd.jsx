import axios from "axios";
import { useEffect, useState } from "react";

export function PastAdmissions() {

    const api = "http://localhost:8080/api/admission/get-all-past";

    const [data, setData] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const size = 10;

    const fetchAdmissions = async (page = 0) => {
        try {
            const res = await axios.get(api, {
                params: { page, size },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });

            setData(res.data.data || []);
            setTotalPages(res.data.totalPages || 0);
            setCurrentPage(page);

        } catch (err) {
            console.log(err);
        }
    };

    useEffect(() => {
        fetchAdmissions(0);
    }, []);

    return (
        <div className="card p-3">

            <table className="table align-middle">
                <thead className="table-light">
                    <tr>
                        <th>Patient</th>
                        <th>Doctor</th>
                        <th>Room/Bed</th>
                        <th>Admitted</th>
                        <th>Discharged</th>
                        <th>Status</th>
                    </tr>
                </thead>

                <tbody>
                    {data.map((a) => (
                        <tr key={a.reference_id}>

                            <td>{a.patient_name}</td>

                            <td>
                                <div>Dr. {a.doctor_name}</div>
                                <small className="text-muted">{a.doctor_specialization}</small>
                            </td>

                            <td>{a.room_no}-{a.bed_no}</td>

                            <td>{a.admission_date}</td>

                            <td>{a.discharge_date}</td>

                            <td>
                                <span className="status-badge status-completed">
                                    {a.admission_status}
                                </span>
                            </td>

                        </tr>
                    ))}
                </tbody>
            </table>

            {/* PAGINATION same as above */}
            <div className="d-flex justify-content-center mt-3">
                <button
                    className="btn btn-outline-secondary me-2"
                    disabled={currentPage === 0}
                    onClick={() => fetchAdmissions(currentPage - 1)}
                >
                    Previous
                </button>

                <span>Page {currentPage + 1} of {totalPages}</span>

                <button
                    className="btn btn-outline-secondary ms-2"
                    disabled={currentPage === totalPages - 1}
                    onClick={() => fetchAdmissions(currentPage + 1)}
                >
                    Next
                </button>
            </div>
        </div>
    );
}