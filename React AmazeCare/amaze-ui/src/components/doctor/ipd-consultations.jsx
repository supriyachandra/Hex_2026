import { useEffect, useState } from "react";
import axios from "axios";

export function IpdConsultations() {

    const api = "http://localhost:8080/api/consultation/consult-history";

    const [records, setRecords] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [search, setSearch] = useState("");

    const size = 5;

    const fetchData = async (page = 0) => {
        try {
            const res = await axios.get(api, {
                params: {
                    page,
                    size,
                    type: "IPD"   
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });

            setRecords(res.data?.data || []);
            setTotalPages(res.data?.totalPages || 0);
            setCurrentPage(page);

        } catch (err) {
            console.log(err);
        }
    };

    useEffect(() => {
        fetchData(0);
    }, []);

    const filtered = records.filter(r =>
        r.patient_name.toLowerCase().includes(search.toLowerCase())
    );

    return (
        <div>

            {/* SEARCH */}
            <input
                className="form-control mb-3"
                placeholder="Search patient..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
            />

            {filtered.map((record) => (
                <div className="card p-3 mb-3 shadow-sm border-start border-4 border-primary"
                     key={record.reference_id}>

                    {/* HEADER */}
                    <div className="d-flex justify-content-between mb-2">
                        <div>
                            <h5>{record.patient_name}</h5>
                            <span className="badge bg-primary">IPD</span>
                        </div>

                        <div className="text-end">
                            <div>{record.date}</div>
                            <span className="badge bg-success">Completed</span>
                        </div>
                    </div>

                    {/* CONSULT */}
                    {record.consultationDtoList?.map((c, i) => (
                        <div className="row mt-2" key={i}>
                            <div className="col-md-6">
                                <strong>Diagnosis:</strong> {c.diagnosis}
                            </div>
                            <div className="col-md-6">
                                <strong>Examination:</strong> {c.examination}
                            </div>
                            <div className="col-md-6 mt-2">
                                <strong>Treatment:</strong> {c.treatmentPlan}
                            </div>
                            <div className="col-md-6 mt-2">
                                <strong>Notes:</strong> {c.symptomNotes}
                            </div>
                        </div>
                    ))}

                    {/* PRESCRIPTIONS */}
                    {record.prescriptionDtoList?.length > 0 && (
                        <ul className="mt-2">
                            {record.prescriptionDtoList.map((p, i) => (
                                <li key={i}>
                                    {p.medicine_name} — {p.dosage}
                                </li>
                            ))}
                        </ul>
                    )}

                    {/* TESTS */}
                    {record.testDtoList?.length > 0 && (
                        <ul>
                            {record.testDtoList.map((t, i) => (
                                <li key={i}>{t.test_name}</li>
                            ))}
                        </ul>
                    )}

                </div>
            ))}

            {/* PAGINATION */}
            <div className="d-flex justify-content-center mt-3">

                <button
                    className="btn btn-outline-secondary me-2"
                    disabled={currentPage === 0}
                    onClick={() => fetchData(currentPage - 1)}
                >
                    Prev
                </button>

                <span>
                    Page {currentPage + 1} of {totalPages}
                </span>

                <button
                    className="btn btn-outline-secondary ms-2"
                    disabled={currentPage === totalPages - 1}
                    onClick={() => fetchData(currentPage + 1)}
                >
                    Next
                </button>

            </div>

        </div>
    );
}