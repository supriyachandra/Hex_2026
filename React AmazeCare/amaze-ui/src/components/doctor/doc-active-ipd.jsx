import axios from "axios";
import { useEffect, useState } from "react";
import DoctorSidebar from "./doctor-sidebar";
import PageHeader from "../all/page-header";


export function ActiveIpdPatients() {

    const api = "http://localhost:8080/api/admission/get-all-active/doc";
    const requestApi = "http://localhost:8080/api/admission/request-discharge";
    const consultApi = "http://localhost:8080/api/consultation/add";

    const [data, setData] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const size = 10;

    // 🔁 fetch admissions
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

    // 🔴 REQUEST DISCHARGE
    const handleRequest = async (id) => {

        const confirmAction = window.confirm("Request discharge?");
        if (!confirmAction) return;

        try {
            await axios.post(`${requestApi}/${id}`, {}, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });

            alert("Discharge requested");
            fetchAdmissions(currentPage);

        } catch (err) {
            alert("Error requesting discharge");
        }
    };

    // =========================
    // 🟢 CONSULTATION MODAL
    // =========================

    const [showModal, setShowModal] = useState(false);
    const [admissionId, setAdmissionId] = useState(null);

    const [examination, setExamination] = useState("");
    const [diagnosis, setDiagnosis] = useState("");
    const [treatmentPlan, setTreatmentPlan] = useState("");
    const [symptomNotes, setSymptomNotes] = useState("");

    const [prescriptions, setPrescriptions] = useState([
        { medicine_name: "", dosage: "" }
    ]);

    const [tests, setTests] = useState([
        { test_name: "" }
    ]);

    const handleCloseModal = () => {
        setShowModal(false);

        setExamination("");
        setDiagnosis("");
        setTreatmentPlan("");
        setSymptomNotes("");
        setPrescriptions([{ medicine_name: "", dosage: "" }]);
        setTests([{ test_name: "" }]);

        setAdmissionId(null);
    };

    const addConsultation = async (e) => {
        e.preventDefault();

        try {
            await axios.post(consultApi, {
                examination,
                diagnosis,
                treatmentPlan,
                symptomNotes,
                admission_id: admissionId, // ✅ KEY CHANGE
                prescriptions,
                tests
            }, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });

            alert("Consultation added");
            handleCloseModal();

        } catch (err) {
            alert("Error saving consultation");
        }
    };

    const addPrescription = () => {
        setPrescriptions([...prescriptions, { medicine_name: "", dosage: "" }]);
    };

    const addTest = () => {
        setTests([...tests, { test_name: "" }]);
    };

    const handlePrescriptionChange = (i, field, value) => {
        const list = [...prescriptions];
        list[i][field] = value;
        setPrescriptions(list);
    };

    const handleTestChange = (i, value) => {
        const list = [...tests];
        list[i].test_name = value;
        setTests(list);
    };

    return (
        <div className="card p-3">

            <table className="table align-middle">
                <thead className="table-light">
                    <tr>
                        <th>Patient</th>
                        <th>Doctor</th>
                        <th>Room</th>
                        <th>Admitted</th>
                        <th>Status</th>
                        <th className="text-end">Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {data.map((a) => (
                        <tr key={a.reference_id}>

                            <td>{a.patient_name}</td>

                            <td>
                                Dr. {a.doctor_name}
                                <br />
                                <small className="text-muted">
                                    {a.doctor_specialization}
                                </small>
                            </td>

                            <td>{a.room_no}-{a.bed_no}</td>

                            <td>{a.admission_date}</td>

                            <td>
                                <span className="badge bg-primary">
                                    {a.admission_status}
                                </span>
                            </td>

                            <td className="text-end">

                                {/* CONSULT */}
                                <button
                                    className="btn btn-outline-primary btn-sm me-2"
                                    onClick={() => {
                                        setShowModal(true);
                                        setAdmissionId(a.reference_id);
                                    }}
                                >
                                    Add Consultation
                                </button>

                                {/* DISCHARGE */}
                                {a.admission_status === "ACTIVE" && (
                                    <button
                                        className="btn btn-outline-danger btn-sm"
                                        onClick={() => handleRequest(a.reference_id)}
                                    >
                                        Request Discharge
                                    </button>
                                )}

                            </td>

                        </tr>
                    ))}
                </tbody>
            </table>

            {/* PAGINATION */}
            <div className="d-flex justify-content-center mt-3">

                <button
                    className="btn btn-outline-secondary me-2"
                    disabled={currentPage === 0}
                    onClick={() => fetchAdmissions(currentPage - 1)}
                >
                    Previous
                </button>

                <span>
                    Page {currentPage + 1} of {totalPages}
                </span>

                <button
                    className="btn btn-outline-secondary ms-2"
                    disabled={currentPage === totalPages - 1}
                    onClick={() => fetchAdmissions(currentPage + 1)}
                >
                    Next
                </button>

            </div>

            {/* MODAL */}
            {showModal && (
                <>
                    <div className="modal show d-block">
                        <div className="modal-dialog modal-lg modal-dialog-scrollable">
                            <div className="modal-content">

                                <div className="modal-header">
                                    <h5>Add IPD Consultation</h5>
                                    <button className="btn-close" onClick={handleCloseModal}></button>
                                </div>

                                <form onSubmit={addConsultation}>
                                    <div className="modal-body">

                                        <textarea className="form-control mb-2"
                                            placeholder="Symptoms"
                                            onChange={(e) => setSymptomNotes(e.target.value)}
                                        />

                                        <textarea className="form-control mb-2"
                                            placeholder="Examination"
                                            onChange={(e) => setExamination(e.target.value)}
                                        />

                                        <input className="form-control mb-2"
                                            placeholder="Diagnosis"
                                            value={diagnosis}
                                            onChange={(e) => setDiagnosis(e.target.value)}
                                            required
                                        />

                                        <textarea className="form-control mb-3"
                                            placeholder="Treatment Plan"
                                            onChange={(e) => setTreatmentPlan(e.target.value)}
                                        />

                                        {/* PRESCRIPTIONS */}
                                        <button type="button" className="btn btn-sm btn-outline-primary mb-2"
                                            onClick={addPrescription}>
                                            + Add Prescription
                                        </button>

                                        {prescriptions.map((p, i) => (
                                            <div className="row mb-2" key={i}>
                                                <div className="col-md-6">
                                                    <input className="form-control"
                                                        placeholder="Medicine"
                                                        value={p.medicine_name}
                                                        onChange={(e) =>
                                                            handlePrescriptionChange(i, "medicine_name", e.target.value)
                                                        }
                                                    />
                                                </div>
                                                <div className="col-md-6">
                                                    <input className="form-control"
                                                        placeholder="Dosage"
                                                        value={p.dosage}
                                                        onChange={(e) =>
                                                            handlePrescriptionChange(i, "dosage", e.target.value)
                                                        }
                                                    />
                                                </div>
                                            </div>
                                        ))}

                                        {/* TESTS */}
                                        <button type="button" className="btn btn-sm btn-outline-primary mb-2"
                                            onClick={addTest}>
                                            + Add Test
                                        </button>

                                        {tests.map((t, i) => (
                                            <input key={i}
                                                className="form-control mb-2"
                                                placeholder="Test"
                                                value={t.test_name}
                                                onChange={(e) =>
                                                    handleTestChange(i, e.target.value)
                                                }
                                            />
                                        ))}

                                    </div>

                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" onClick={handleCloseModal}>
                                            Cancel
                                        </button>

                                        <button type="submit" className="btn btn-primary">
                                            Save
                                        </button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>

                    <div className="modal-backdrop fade show"></div>
                </>
            )}

        </div>
    );
}