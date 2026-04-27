import { useNavigate } from "react-router-dom";
import PageHeader from "../all/page-header";
import AdminSidebar from "./admin-sidebar";
import { useEffect, useState } from "react";
import axios from "axios";
import RegisterPatientModal from "./register-patient";
import AdmitPatientModal from "./admit-patient-modal";
import { AdminBookingModal } from "./admin-booking-modal";

export function AdminPatients() {

    const navigate = useNavigate();

    const [showBookingModal, setShowBookingModal] = useState(false);
    const [selectedPatient, setSelectedPatient] = useState(null);


    const [allPatients, setAllPatients] = useState([])
    const [displayPatient, setDisplayPatient] = useState([])

    const [currentPage, setCurrentPage] = useState(0)
    const [totalPages, setTotalPages] = useState(0)

    const [search, setSearch] = useState("")

    const [showModal, setShowModal] = useState(false)

    const size = 5


    const [showAdmitModal, setShowAdmitModal] = useState(false);
    const [selectedPatientId, setSelectedPatientId] = useState(null);

    const allPatientsApi = "http://localhost:8080/api/patient/all"



    const applyPagination = (data, page, searchText) => {

        //search on FULL data (filtered list of patients)
        let filtered = data

        if (searchText) {
            filtered = data.filter(p =>
                p.name.toLowerCase().includes(searchText.toLowerCase()) ||
                p.phone.includes(searchText)
            )
        }

        // now pagination 
        const start = page * size
        const end = start + size

        setDisplayPatient(filtered.slice(start, end))
        setTotalPages(Math.ceil(filtered.length / size))
    }



    useEffect(() => {
        const fetchPatient = async () => {
            const response = await axios.get(allPatientsApi, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            })

            setAllPatients(response.data)

            // initial load → page 0
            applyPagination(response.data, 0, search)
        }

        fetchPatient()
    }, [])


    const handleSearch = (value) => {
        setSearch(value)
        setCurrentPage(0)

        applyPagination(allPatients, 0, value)
    }

    const handlePageChange = (page) => {
        setCurrentPage(page)

        applyPagination(allPatients, page, search)
    }



    return (
        <div className="container-fluid p-4">
            <div className="row">

                {/* SIDEBAR */}
                <div className="col-sm-2">
                    <AdminSidebar />
                </div>

                <div className="col-lg-10">

                    <PageHeader
                        title="Patients"
                        subtitle="Hospital wide patient registry"
                    />

                    {/*SEARCH + REGISTER */}
                    <div className="card p-3 mb-4">

                        <div className="d-flex justify-content-between align-items-center">

                            <input
                                className="form-control w-50"
                                placeholder="Search by Name or Mobile"
                                value={search}
                                onChange={(e) => handleSearch(e.target.value)}
                            />

                            <button
                                className="btn btn-primary ms-3"
                                onClick={() => setShowModal(true)}
                            >
                                + Register Patient
                            </button>

                        </div>
                    </div>

                    {/* TABLE */}
                    <div className="card p-3">

                        {displayPatient.length === 0 ? (
                            <div className="text-center p-4">
                                <p className="text-muted">No patients found</p>
                                <button
                                    className="btn btn-primary"
                                    onClick={() => setShowModal(true)}
                                >
                                    Register New Patient
                                </button>
                            </div>
                        ) : (

                            <>
                                <table className="table table-hover align-middle">
                                    <thead className="table-light">
                                        <tr>
                                            
                                            <th>Name</th>
                                            <th>Mobile</th>
                                            <th>DOB</th>
                                            <th>Gender</th>
                                            <th className="text-end">Actions</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        {displayPatient.map((p, index) => (
                                            <tr key={index}>
                                               
                                                <td>{p.name}</td>
                                                <td>{p.phone}</td>
                                                <td>{p.dob}</td>
                                                <td>{p.gender}</td>

                                                <td className="text-end">

                                                    <button
                                                        className="btn btn-outline-primary btn-sm me-2"
                                                        onClick={() => {
                                                            setSelectedPatientId(p.id);
                                                            setShowAdmitModal(true);
                                                        }}
                                                    >
                                                        Admit
                                                    </button>

                                                    <button
                                                        className="btn btn-primary btn-sm"
                                                        onClick={() => {
                                                            setSelectedPatient(p);
                                                            setShowBookingModal(true);
                                                        }}
                                                    >
                                                        Book
                                                    </button>

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
                                        onClick={() => handlePageChange(currentPage - 1)}
                                    >
                                        Previous
                                    </button>

                                    <span className="align-self-center">
                                        Page {currentPage + 1} of {totalPages}
                                    </span>

                                    <button
                                        className="btn btn-outline-secondary ms-2"
                                        disabled={currentPage === totalPages - 1}
                                        onClick={() => handlePageChange(currentPage + 1)}
                                    >
                                        Next
                                    </button>

                                </div>
                            </>
                        )}
                    </div>

                    {showModal && (
                        <>
                            <RegisterPatientModal
                                show={showModal}
                                onClose={() => setShowModal(false)}
                                onSuccess={async () => {
                                    //refresh patient list after adding
                                    const response = await axios.get(allPatientsApi, {
                                        headers: {
                                            Authorization: "Bearer " + localStorage.getItem("token")
                                        }
                                    });

                                    setAllPatients(response.data);
                                    applyPagination(response.data, 0, "");
                                }}
                            />


                            {/* BACKDROP */}
                            <div className="modal-backdrop fade show"></div>
                        </>
                    )}


                    {/* ADMIT MODAL */}
                    {showAdmitModal && (
                        <>
                            <AdmitPatientModal
                                show={showAdmitModal}
                                patientId={selectedPatientId}
                                onClose={() => setShowAdmitModal(false)}
                                onSuccess={() => {
                                    // optional refresh later
                                }}
                            />
                            <div className="modal-backdrop fade show"></div>
                        </>
                    )}

                    {showBookingModal && (
                        <AdminBookingModal
                            patient={selectedPatient}
                            onClose={() => setShowBookingModal(false)}
                        />
                    )}
                </div>
            </div>
        </div>


    )
}