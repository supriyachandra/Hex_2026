import { useEffect, useState } from "react";
import PageHeader from "../all/page-header";
import DoctorSidebar from "./doctor-sidebar";
import axios from "axios";

export function CheckAppointments() {
    const appApi = "http://localhost:8080/api/appointment/doc/upcoming"

    const [appointments, setAppointments] = useState([])

    useEffect(() => {
        const fetchApps = async () => {
            try {
                const response = await axios.get(appApi, {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                })
                setAppointments(response.data)
            }
            catch (err) {
                err
            }
        }
        fetchApps()

    }, [])


    const handleConfirm = async (id) => {
        const confirmApi = `http://localhost:8080/api/appointment/confirm/${id}`

        await axios.put(confirmApi, {}, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })

        const updated = appointments.map((a) => {
            if (a.app_id === id) {
                return { ...a, status: "CONFIRMED" };
            }
            return a;
        });

        setAppointments(updated)
    };

    const handleReject = async (id) => {
        const cancelApi = `http://localhost:8080/api/appointment/reject/${id}`

        await axios.put(cancelApi, {}, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })

        const updated = appointments.map((a) => {
            if (a.app_id === id) {
                return { ...a, status: "CANCELLED" }
            }
            return a
        })
        setAppointments(updated)
    };


    // for modal in ui
    const [showModal, setShowModal] = useState(false)


    // Add consultation
    const consultApi = "http://localhost:8080/api/consultation/add"
    const [examination, setExamination] = useState("")
    const [diagnosis, setDiagnosis] = useState("")
    const [treatmentPlan, setTreatmentPlan] = useState("")
    const [symptomNotes, setSymptomNotes] = useState("")
    const [appointmentId, setAppointmentId] = useState("")

    const [prescriptions, setPrescriptions] = useState([
        {
            "medicine_name": "",
            "dosage": ""
        }
    ])

    const [tests, setTests] = useState([
        { "test_name": "" }
    ])



    const addConsultation = async (e) => {
        e.preventDefault()

        try {
            await axios.post(consultApi, {
                "examination": examination,
                "diagnosis": diagnosis,
                "treatmentPlan": treatmentPlan,
                "symptomNotes": symptomNotes,
                "appointment_id": appointmentId,
                "prescriptions": prescriptions,
                "tests": tests
            },
                {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                })

            const update = appointments.map((a) => {
                if (appointmentId == a.app_id) {
                    return { ...a, status: "COMPLETED" }
                }

                return a
            })

            setAppointments(update)
            handleCloseModal()
        }
        catch (err) {
            err
        }
    }

    // to add another empty row to the list
    const addPrescription = () => {
        const newList = [...prescriptions];

        newList.push({ medicine_name: "", dosage: "" }); //add empty row to copied list.

        setPrescriptions(newList); //update state
    }

    // similarly add test when + is presssed
    const addTest = () => {
        const newList = [...tests]

        newList.push({ test_name: "" })
        setTests(newList);
    }

    const handlePrescriptionChange = (index, field, value) => {
        const newList = [...prescriptions]

        newList[index][field] = value
        setPrescriptions(newList)
    }

    const handleTestChange = (index, value) => {

        const newList = [...tests];
        newList[index].test_name = value;

        setTests(newList);
    };


    const handleCloseModal = () => {
        setShowModal(false);

        // reset all fields..
        setExamination("");
        setDiagnosis("");
        setTreatmentPlan("");
        setSymptomNotes("");

        setPrescriptions([{ medicine_name: "", dosage: "" }]);
        setTests([{ test_name: "" }]);

        setAppointmentId(null);
    }

    // remove prescrip..
    const removePrescription = (index) => {
        const newList = prescriptions.filter((_, i) =>
             i !== index)
        
        setPrescriptions(newList.length ? newList : [{ medicine_name: "", dosage: "" }])
    }

    //remove test
    const removeTest = (index) => {
        const newList = tests.filter((_, i) => i !== index)
        setTests(newList.length ? newList : [{ test_name: "" }])
    }



    return (
        <div className="container-fluid">
            <div className="row">
                <div className="col-sm-2 p-4">
                    <DoctorSidebar />
                </div>

                <div className="col-md-10 p-4">
                    <PageHeader
                        title="Appointments"
                        subtitle="Filter, Confirm, and run consultations"
                    />

                    {/* main section */}
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Patient</th>
                                <th>Date</th>
                                <th>Time</th>
                                <th>Symptoms</th>
                                <th>Status</th>
                                <th className="text-end">Actions</th>
                            </tr>
                        </thead>

                        <tbody>
                            {appointments.map((a) => (
                                <tr key={a.app_id}>

                                    <td>{a.patient_name}</td>
                                    <td>{a.app_date}</td>
                                    <td>{a.app_time_slot}</td>
                                    <td className="symptom-cell">
                                        <div className="symptom-text">
                                            {a.symptoms}
                                        </div>
                                    </td>

                                    {/* STATUS */}
                                    <td>
                                        {a.status === "PENDING" && (
                                            <span className="badge bg-warning text-dark">
                                                PENDING
                                            </span>
                                        )}

                                        {a.status === "CONFIRMED" && (
                                            <span className="badge bg-primary">
                                                CONFIRMED
                                            </span>
                                        )}

                                        {a.status === "CANCELLED" && (
                                            <span className="badge bg-danger">
                                                CANCELLED
                                            </span>
                                        )}

                                        {a.status === "COMPLETED" && (
                                            <span className="badge bg-success">
                                                COMPLETED
                                            </span>
                                        )}
                                    </td>

                                    {/* ACTIONS */}
                                    <td className="text-end">

                                        {/* PENDING */}
                                        {a.status === "PENDING" && (
                                            <>
                                                <button
                                                    className="btn btn-success me-2"
                                                    onClick={() => handleConfirm(a.app_id)}
                                                >
                                                    Confirm
                                                </button>

                                                <button
                                                    className="btn btn-outline-danger"
                                                    onClick={() => handleReject(a.app_id)}
                                                >
                                                    Reject
                                                </button>
                                            </>
                                        )}

                                        {/* CONFIRMED */}
                                        {a.status === "CONFIRMED" && (
                                            <button
                                                className="btn btn-primary"
                                                onClick={() => {
                                                    setShowModal(true)
                                                    setAppointmentId(a.app_id)
                                                }
                                                }
                                            >
                                                Add Consultation
                                            </button>
                                        )}

                                        {/* COMPLETED */}
                                        {a.status === "COMPLETED" && (
                                            <span className="text-muted">
                                                Done
                                            </span>
                                        )}

                                        {/* REJECTED */}
                                        {a.status === "CANCELLED" && (
                                            <span className="text-muted">
                                                -
                                            </span>
                                        )}

                                    </td>

                                </tr>
                            ))}
                        </tbody>
                    </table>


                    {/* consultation form */}
                    {showModal && (
                        <div className="modal show fade d-block" tabIndex="-1">

                            <div className="modal-dialog modal-lg modal-dialog-scrollable">
                                <div className="modal-content">

                                    {/* HEADER */}
                                    <div className="modal-header">
                                        <h5 className="modal-title">
                                            New OPD consultation
                                        </h5>
                                        <button
                                            className="btn-close"
                                            onClick={() => handleCloseModal()}
                                        ></button>
                                    </div>

                                    {/* BODY */}
                                    <form onSubmit={addConsultation}>
                                        <div className="modal-body">

                                            <p className="text-muted">
                                                Record examination, diagnosis, prescriptions and tests.
                                            </p>

                                            {/* Symptom notes */}
                                            <div className="mb-3">
                                                <label className="form-label">Symptom notes</label>
                                                <textarea className="form-control" rows="3"
                                                    placeholder="Patient-reported symptoms..."
                                                    onChange={(e) => setSymptomNotes(e.target.value)}
                                                />
                                            </div>

                                            {/* Examination */}
                                            <div className="mb-3">
                                                <label className="form-label">Examination</label>
                                                <textarea className="form-control" rows="3"
                                                    placeholder="Vitals, physical exam findings..."
                                                    onChange={(e) => setExamination(e.target.value)}
                                                />
                                            </div>

                                            {/* Diagnosis */}
                                            <div className="mb-3">
                                                <label className="form-label">Diagnosis *</label>
                                                <input className="form-control"
                                                    placeholder="Primary diagnosis"
                                                    required='required'
                                                    onChange={(e) => setDiagnosis(e.target.value)}
                                                />
                                            </div>

                                            {/* Treatment */}
                                            <div className="mb-3">
                                                <label className="form-label">Treatment plan</label>
                                                <textarea className="form-control" rows="3"
                                                    placeholder="Plan of care, follow-up..."
                                                    onChange={(e) => setTreatmentPlan(e.target.value)}
                                                />
                                            </div>

                                            {/* PRESCRIPTIONS */}
                                            <div className="mb-4">
                                                <div className="d-flex justify-content-between align-items-center mb-2">
                                                    <label className="form-label mb-0">Prescriptions</label>
                                                    <button
                                                        type="button"
                                                        className="btn btn-sm btn-outline-primary"
                                                        onClick={addPrescription}
                                                    >
                                                        + Add
                                                    </button>
                                                </div>

                                                {prescriptions.map((p, index) => (
                                                    <div className="row mb-2 align-items-center" key={index}>

                                                        <div className="col-md-5">
                                                            <input
                                                                className="form-control"
                                                                placeholder="Medicine"
                                                                value={p.medicine_name}
                                                                onChange={(e) =>
                                                                    handlePrescriptionChange(index, "medicine_name", e.target.value)
                                                                }
                                                            />
                                                        </div>

                                                        <div className="col-md-5">
                                                            <input
                                                                className="form-control"
                                                                placeholder="Dosage"
                                                                value={p.dosage}
                                                                onChange={(e) =>
                                                                    handlePrescriptionChange(index, "dosage", e.target.value)
                                                                }
                                                            />
                                                        </div>

                                                        <div className="col-md-2 text-end">
                                                            <button
                                                                type="button"
                                                                className="btn btn-sm btn-outline-danger"
                                                                onClick={() => removePrescription(index)}
                                                            >
                                                                ✕
                                                            </button>
                                                        </div>

                                                    </div>
                                                ))}
                                            </div>


                                            {/* TESTS */}
                                            <div className="mb-3">
                                                <div className="d-flex justify-content-between align-items-center mb-2">
                                                    <label className="form-label mb-0">Recommended Tests</label>
                                                    <button
                                                        type="button"
                                                        className="btn btn-sm btn-outline-primary"
                                                        onClick={addTest}
                                                    >
                                                        + Add
                                                    </button>
                                                </div>

                                                {tests.map((t, index) => (
                                                    <div className="d-flex mb-2 align-items-center" key={index}>

                                                        <input
                                                            className="form-control me-2"
                                                            placeholder="Test name"
                                                            value={t.test_name}
                                                            onChange={(e) =>
                                                                handleTestChange(index, e.target.value)
                                                            }
                                                        />

                                                        <button
                                                            type="button"
                                                            className="btn btn-sm btn-outline-danger"
                                                            onClick={() => removeTest(index)}
                                                        >
                                                            ✕
                                                        </button>

                                                    </div>
                                                ))}
                                            </div>

                                        </div>


                                        {/* FOOTER */}
                                        <div className="modal-footer">
                                            <button
                                                className="btn btn-secondary"
                                                onClick={() => handleCloseModal()}
                                            >
                                                Cancel
                                            </button>

                                            <button type="submit" className="btn btn-primary">
                                                Save Consultation
                                            </button>
                                        </div>

                                    </form>
                                </div>

                            </div>
                        </div>

                    )}

                    {/* BACKDROP */}
                    {showModal && (
                        <div
                            className="modal-backdrop fade show"
                            style={{ zIndex: 1040 }}
                        ></div>
                    )}

                </div>

            </div>
        </div >
    )
}