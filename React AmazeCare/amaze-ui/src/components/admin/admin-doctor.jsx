import { useEffect, useState } from "react";
import axios from "axios";
import AdminSidebar from "./admin-sidebar";
import PageHeader from "../all/page-header";

export function AdminDoctors() {

    const doctorApi = "http://localhost:8080/api/doctor/get-all/v1";
    const addDoctorApi = "http://localhost:8080/api/doctor/add-doctor";
    const specApi = "http://localhost:8080/api/specialization/get-all";

    const [doctors, setDoctors] = useState([]);
    const [specializations, setSpecializations] = useState([]);

    //pagination states
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    // modal states
    const [selectedDoctorId, setSelectedDoctorId] = useState(null);
    const [showScheduleModal, setShowScheduleModal] = useState(false);

    
    const [showModal, setShowModal] = useState(false);

    const size = 10;

    // form state
    const [name, setName] = useState("");
    const [experience, setExperience] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [qualification, setQualification] = useState("");
    const [designation, setDesignation] = useState("");
    const [specializationId, setSpecializationId] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");


    // fetch doctors
    const fetchDoctors = async (page = 0) => {
        try {
            const res = await axios.get(doctorApi, {
                params: { page, size },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });

            setDoctors(res.data.data);
            setTotalPages(res.data.totalPages);
            setCurrentPage(page);

        } catch (err) {
            console.log(err);
        }
    }

    


    useEffect(() => {
        fetchDoctors(currentPage);
    }, [currentPage]);


    useEffect(() => {
        //  fetch specializations
        const fetchSpecs = async () => {
            try {
                const res = await axios.get(specApi);
                setSpecializations(res.data);
            } catch (err) {
                console.log(err);
            }
        };
        fetchSpecs()

    }, [])



    // ➕ ADD DOCTOR
    const handleAddDoctor = async (e) => {
        e.preventDefault();

        try {
            await axios.post(addDoctorApi, {
                "name": name,
                "experience": experience,
                "phone": phone,
                "email": email,
                "qualification": qualification,
                "designation": designation,
                "specialization_id": specializationId,
                "username": username,
                "password": password
            }, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            })

            // reset
            setName("");
            setExperience("");
            setEmail("");
            setPhone("");
            setQualification("");
            setDesignation("");
            setSpecializationId("");

            setShowModal(false);

            // refresh
            fetchDoctors(currentPage);

        } catch (err) {
            console.log(err);
        }
    }

    return (
        <div className="container-fluid p-4">
            <div className="row">

                {/* SIDEBAR */}
                <div className="col-sm-2">
                    <AdminSidebar />
                </div>

                <div className="col-md-10 p-4">

                    <PageHeader
                        title="Doctors"
                        subtitle="Onboard and manage hospital staff"
                    />

                    {/* ADD BUTTON- for add doctor */}
                    <div className="d-flex justify-content-end mb-3">
                        <button
                            className="btn btn-primary"
                            onClick={() => setShowModal(true)}
                        >
                            + Add Doctor
                        </button>
                    </div>

                    {/* TABLE */}
                    <div className="card p-3">

                        <table className="table align-middle">
                            <thead className="table-light">
                                <tr>
                                    <th>Name</th>
                                    <th>Specialization</th>
                                    <th>Experience</th>
                                    <th>Contact</th>
                                </tr>
                            </thead>

                            <tbody>
                                {
                                    doctors.map((d, index) => (
                                        <tr key={index}>

                                            <td>
                                                <div className="d-flex align-items-center">
                                                    <div className="me-2 bg-light rounded-circle p-2">
                                                        {d.name?.charAt(0)}
                                                    </div>
                                                    Dr. {d.name}
                                                </div>
                                            </td>

                                            <td className="text-primary fw-semibold">
                                                {d.specialization}
                                            </td>

                                            <td>{d.experience} years</td>

                                            <td>
                                                <div>{d.email}</div>
                                                <small className="text-muted">{d.phone}</small>
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
                                onClick={() => fetchDoctors(currentPage - 1)}
                            >
                                Previous
                            </button>

                            <span className="align-self-center">
                                Page {currentPage + 1} of {totalPages}
                            </span>

                            <button
                                className="btn btn-outline-secondary ms-2"
                                disabled={currentPage === totalPages - 1}
                                onClick={() => fetchDoctors(currentPage + 1)}
                            >
                                Next
                            </button>

                        </div>
                    </div>

                </div>
            </div>

            {/* schedule modal */}
            {showScheduleModal && (
                <DoctorScheduleModal
                    doctorId={selectedDoctorId}
                    onClose={() => setShowScheduleModal(false)}
                />
            )}

            {/* MODAL */}
            {showModal && (
                <>
                    <div className="modal show d-block">
                        <div className="modal-dialog modal-lg">
                            <div className="modal-content">

                                {/* HEADER */}
                                <div className="modal-header">
                                    <h5>Add Doctor</h5>
                                    <button
                                        className="btn-close"
                                        onClick={() => setShowModal(false)}
                                    ></button>
                                </div>

                                {/* BODY */}
                                <form onSubmit={handleAddDoctor}>
                                    <div className="modal-body">

                                        <input className="form-control mb-2" placeholder="Full Name"
                                            value={name} onChange={(e) => setName(e.target.value)} required />

                                        <select className="form-control mb-2"
                                            value={specializationId}
                                            onChange={(e) => setSpecializationId(e.target.value)}
                                            required>
                                            <option value="">Select specialization</option>
                                            {specializations?.map((s) => (
                                                <option key={s.id} value={s.id}>{s.name}</option>
                                            ))}
                                        </select>

                                        <input type="number" className="form-control mb-2"
                                            placeholder="Experience"
                                            value={experience}
                                            onChange={(e) => setExperience(e.target.value)} required />

                                        <input className="form-control mb-2"
                                            placeholder="Phone"
                                            value={phone}
                                            onChange={(e) => setPhone(e.target.value)} required />

                                        <input type="email" className="form-control mb-2"
                                            placeholder="Email"
                                            value={email}
                                            onChange={(e) => setEmail(e.target.value)} required />

                                        <input className="form-control mb-2"
                                            placeholder="Qualification"
                                            value={qualification}
                                            onChange={(e) => setQualification(e.target.value)} />

                                        <input className="form-control mb-2"
                                            placeholder="Designation"
                                            value={designation}
                                            onChange={(e) => setDesignation(e.target.value)} />

                                        <input className="form-control mb-2"
                                            placeholder="Username"
                                            value={username}
                                            onChange={(e) => setUsername(e.target.value)} required />

                                        <input type="password" className="form-control"
                                            placeholder="Password"
                                            value={password}
                                            onChange={(e) => setPassword(e.target.value)} required />

                                    </div>

                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary"
                                            onClick={() => setShowModal(false)}>
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
    )
}