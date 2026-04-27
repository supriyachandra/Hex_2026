import { useEffect, useState } from "react";
import PatientNavbar from "./patient-sidebar";
import axios from "axios";
import PatientSidebar from "./patient-sidebar";
import PageHeader from "../all/page-header";

export function MedicalHistory() {
    const api = "http://localhost:8080/api/patient/medical-history"
    const [records, setRecord] = useState([])

    useEffect(() => {
        const fetchConsultations = async () => {
            try {
                const response = await axios.get(api, {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                })

                setRecord(response.data)
            }
            catch (err) {
                //err
            }
        }
        fetchConsultations()
    }, [])
    return (

        <div className="container-fluid">
            <div className="row">
                <div className="col-sm-2 p-4">

                    <PatientSidebar />
                </div>


                {/* MAIN CONTENT */}
                <div className="col-md-10 p-4">

                    <PageHeader
                        title="Medical History"
                        subtitle="Your full record across OPD and IPD"
                    />

                    <div className="container-fluid p-4">
                        <div className="container mt-4">

                            {records.map((record) => (
                                <div className="history-card mb-4" key={record.reference_id}>


                                    {/* HEADER */}
                                    <div className="d-flex justify-content-between align-items-start mb-3">

                                        <div>
                                            <h5 className="fw-semibold mb-1">
                                                Dr. {record.doctor_name}
                                            </h5>

                                            <span className="badge bg-info-subtle text-primary border">
                                                {record.patientType} {/* OPD / IPD */}
                                            </span>
                                        </div>

                                        <div className="text-end">
                                            <div>{record.date}</div>

                                            <span className="badge bg-success-subtle text-success border">
                                                COMPLETED
                                            </span>
                                        </div>

                                    </div>

                                    {/* CONSULTATIONS */}
                                    {record.consultationDtoList?.map((c, index) => (
                                        <div className="row mb-3" key={index}>

                                            <div className="col-md-6">
                                                <p className="label">SYMPTOMS</p>
                                                <p>{c.symptomNotes}</p>
                                            </div>

                                            <div className="col-md-6">
                                                <p className="label">EXAMINATION</p>
                                                <p>{c.examination}</p>
                                            </div>

                                            <div className="col-md-6">
                                                <p className="label">DIAGNOSIS</p>
                                                <p className="text-primary fw-medium">
                                                    {c.diagnosis}
                                                </p>
                                            </div>

                                            <div className="col-md-6">
                                                <p className="label">TREATMENT PLAN</p>
                                                <p>{c.treatmentPlan}</p>
                                            </div>

                                        </div>
                                    ))}

                                    {/* PRESCRIPTIONS */}
                                    {record.prescriptionDtoList?.length > 0 && (
                                        <div className="mb-3">
                                            <p className="label">
                                                <i className="bi bi-capsule me-2"></i>
                                                PRESCRIPTIONS
                                            </p>

                                            <ul className="ps-3">
                                                {record.prescriptionDtoList.map((p, i) => (
                                                    <li key={i}>
                                                        <span className="fw-medium">
                                                            {p.medicine_name}
                                                        </span>
                                                        {" — "}
                                                        {p.dosage}
                                                    </li>
                                                ))}
                                            </ul>
                                        </div>
                                    )}

                                    {/* TESTS */}
                                    {record.testDtoList?.length > 0 && (
                                        <div>
                                            <p className="label">
                                                <i className="bi bi-clipboard2-pulse me-2"></i>
                                                RECOMMENDED TESTS
                                            </p>

                                            <ul className="ps-3">
                                                {record.testDtoList.map((t, i) => (
                                                    <li key={i}>{t.test_name}</li>
                                                ))}
                                            </ul>
                                        </div>
                                    )}

                                </div>

                            ))}

                        </div>
                    </div>

                </div>
            </div>
        </div>
    )

}

