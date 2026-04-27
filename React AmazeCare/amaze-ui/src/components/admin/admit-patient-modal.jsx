import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

export default function AdmitPatientModal({ show, onClose, patientId, onSuccess }) {

    const doctorApi = "http://localhost:8080/api/doctor/get-all/v2";
    const admitApi = "http://localhost:8080/api/admission/admit";

    const [doctors, setDoctors] = useState([]);
    const [search, setSearch] = useState("");

    const [selectedDoctor, setSelectedDoctor] = useState("");
    const [reason, setReason] = useState("");
    const [roomNumber, setRoomNumber] = useState("");
    const [bedNumber, setBedNumber] = useState("");
    const [showDropdown, setShowDropdown] = useState(false);

    const [error, setError] = useState("");

    //  fetch all doctors
    useEffect(() => {
        if (!show) return;

        const fetchDoctors = async () => {
            try {
                const res = await axios.get(doctorApi, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                });
                setDoctors(res.data);
            } catch (err) {
                console.log(err);
            }
        };

        fetchDoctors();
    }, [show]);

    //  filter doctors - based on doctor name or specialization
    const filteredDoctors = doctors.filter(d =>
        d.name.toLowerCase().includes(search.toLowerCase())
        ||
        d.specialization.toLowerCase().includes(search.toLowerCase())
    );

    //submit admit
    const handleAdmit = async (e) => {
        e.preventDefault();

        try {
            await axios.post(admitApi, {
                patient_id: patientId,
                doctor_id: selectedDoctor,
                reason: reason,
                roomNumber: roomNumber,
                bedNumber: bedNumber
            }, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });

            onClose();
            if (onSuccess) onSuccess();


            // reset
            setSelectedDoctor("");
            setReason("");
            setRoomNumber("");
            setBedNumber("");
            setSearch("");
            setError("");

        } catch (err) {
            if (err.response && err.response.data?.Message) {
                setError(err.response.data.Message);
            } else {
                setError("Something went wrong");
            }
        }
    };

    if (!show) return null;

    return (
        <>
            <div className="modal show d-block">

                <div className="modal-dialog modal-lg">
                    <div className="modal-content">

                        {/* HEADER */}
                        <div className="modal-header">
                            <h5>Admit Patient</h5>
                            <button className="btn-close" onClick={onClose}></button>
                        </div>

                        {error && (
                            <div className="alert alert-danger">
                                {error}
                                <Link to='/admin-admissions'>View Admissions</Link>
                            </div>
                        )}

                        {/* BODY */}
                        <form onSubmit={handleAdmit}>
                            <div className="modal-body">

                                {/* Doctor Search */}
                                
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Search doctor..."
                                    value={search}
                                    onFocus={() => setShowDropdown(true)}
                                    onChange={(e) => setSearch(e.target.value)}
                                />

                                {showDropdown && (
                                    <div
                                        className="list-group position-absolute w-100"
                                        style={{ zIndex: 1000, maxHeight: "150px", overflowY: "auto" }}
                                    >
                                        
                                        {filteredDoctors.map((d) => (
                                            <button
                                                key={d.id}
                                                type="button"
                                                className="list-group-item list-group-item-action"
                                                onClick={() => {
                                                    setSelectedDoctor(d.id);
                                                    setSearch(d.name);
                                                    setShowDropdown(false);
                                                }}
                                            >
                                                <div className="d-flex flex-column text-start">
                                                    <span>{d.name}</span>

                                                    <small className="text-muted">
                                                        {d.specialization}
                                                    </small>
                                                </div>
                                            </button>
                                        ))}
                                    </div>
                                )}

                                {/* Reason */}
                               
                                <input
                                    className="form-control mb-2 mt-2"
                                    placeholder="Reason"
                                    value={reason}
                                    onChange={(e) => setReason(e.target.value)}
                                    required
                                />

                                {/* Room */}
                               
                                <input
                                    className="form-control mb-2"
                                    placeholder="Room Number"
                                    value={roomNumber}
                                    onChange={(e) => setRoomNumber(e.target.value)}
                                    required
                                />

                                {/* Bed */}
                                
                                <input
                                    className="form-control"
                                    placeholder="Bed Number"
                                    value={bedNumber}
                                    onChange={(e) => setBedNumber(e.target.value)}
                                    required
                                />

                            </div>

                            {/* FOOTER */}
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" onClick={onClose}>
                                    Cancel
                                </button>

                                <button type="submit" className="btn btn-primary">
                                    Admit Patient
                                </button>
                            </div>

                        </form>

                        {showDropdown && (
                            <div className="list-group mt-1" style={{ maxHeight: "150px", overflowY: "auto" }}>
                                {filteredDoctors.map((d) => (
                                    <button
                                        key={d.id}
                                        className="list-group-item list-group-item-action"
                                        onClick={() => {
                                            setSelectedDoctor(d.id);
                                            setSearch(d.name); // fill input
                                            setShowDropdown(false);  // 🔥 CLOSE DROPDOWN
                                        }}
                                    >
                                        {d.name}
                                    </button>
                                ))}
                            </div>
                        )}

                    </div>
                </div>
            </div>

            {/* BACKDROP */}
            <div className="modal-backdrop fade show"></div>
        </>
    );
}