import axios from "axios";
import { useEffect, useState } from "react";
import DoctorSearchDropdown from "./doctor-dropdown";

export function AdminBookingModal({ patient, onClose }) {

    const [selectedDoctor, setSelectedDoctor] = useState(null);

    const [dates, setDates] = useState([]);
    const [appointmentDate, setAppointmentDate] = useState("");

    const [times, setTimes] = useState([]);
    const [timeSlot, setTimeSlot] = useState("");

    const [visitType, setVisitType] = useState("");
    const [symptoms, setSymptoms] = useState("");

    const [successMsg, setSuccessMsg] = useState("");

    //  FETCH DATES (when doctor selected)
    useEffect(() => {
        if (!selectedDoctor) return;

        const fetchDates = async () => {
            try {
                const res = await axios.get(
                    `http://localhost:8080/api/schedule/get-by/${selectedDoctor.id}`,
                    {
                        headers: {
                            Authorization: "Bearer " + localStorage.getItem("token")
                        }
                    }
                );

                const uniqueDates = [...new Set(res.data.map(d => d.date))];
                setDates(uniqueDates);

            } catch (err) {
                console.log(err);
            }
        };

        fetchDates();

    }, [selectedDoctor]);

    // FETCH TIMES
    useEffect(() => {
        if (!appointmentDate || !selectedDoctor) return;

        const fetchTimes = async () => {
            try {
                const res = await axios.get(
                    `http://localhost:8080/api/schedule/slots/${selectedDoctor.id}/${appointmentDate}`,
                    {
                        headers: {
                            Authorization: "Bearer " + localStorage.getItem("token")
                        }
                    }
                );

                setTimes(res.data);

            } catch (err) {
                console.log(err);
            }
        };

        fetchTimes();

    }, [appointmentDate, selectedDoctor]);

    //  BOOK APPOINTMENT
    const handleBook = async (e) => {
        e.preventDefault();

        if (!selectedDoctor || !appointmentDate || !timeSlot || !visitType) {
            alert("Please fill all required fields");
            return;
        }

        try {
            await axios.post(
                `http://localhost:8080/api/appointment/book/${selectedDoctor.id}/${patient.id}`,
                {
                    appointmentDate: appointmentDate,
                    timeSlot: timeSlot,
                    symptoms: symptoms,
                    visitType: visitType
                },
                {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                }
            );

            setSuccessMsg("Appointment booked successfully!");

        } catch (err) {
            console.log(err);
            alert("Booking failed");
        }
    };

    return (
        <>
            <div className="modal show d-block">
                <div className="modal-dialog modal-lg">
                    <div className="modal-content">

                        {/* HEADER */}
                        <div className="modal-header">
                            <h5>Book Appointment</h5>
                            <button className="btn-close" onClick={onClose}></button>
                        </div>

                        {/* BODY */}
                        <div className="modal-body">

                            {successMsg && (
                                <div className="alert alert-success">
                                    {successMsg}
                                </div>
                            )}

                            <form onSubmit={handleBook}>

                                {/* PATIENT */}
                                <div className="mb-3">
                                    <label>Patient</label>
                                    <div className="form-control bg-light">
                                        {patient.name}
                                    </div>
                                </div>

                                {/* DOCTOR */}
                                <DoctorSearchDropdown onSelect={setSelectedDoctor} />

                                {/* DATE */}
                                <div className="mb-3">
                                    <label>Select Date</label>
                                    <select
                                        className="form-control"
                                        value={appointmentDate}
                                        onChange={(e) => {
                                            setAppointmentDate(e.target.value);
                                            setTimeSlot("");
                                        }}
                                        required
                                    >
                                        <option value="">Select</option>
                                        {dates.map((d, i) => (
                                            <option key={i} value={d}>{d}</option>
                                        ))}
                                    </select>
                                </div>

                                {/* TIME */}
                                <div className="mb-3">
                                    <label>Select Time</label>

                                    <div className="d-flex flex-wrap gap-2 mt-2">
                                        {times.map((t, i) => (
                                            <button
                                                key={i}
                                                type="button"
                                                className={`btn ${
                                                    timeSlot === t.time
                                                        ? "btn-primary"
                                                        : "btn-outline-primary"
                                                }`}
                                                onClick={() => setTimeSlot(t.time)}
                                            >
                                                {t.time}
                                            </button>
                                        ))}
                                    </div>
                                </div>

                                {/* VISIT TYPE */}
                                <div className="mb-3">
                                    <label className="d-block mb-2">Visit Type</label>

                                    <div className="d-flex gap-3 flex-wrap">
                                        {["GENERAL_CHECKUP", "FOLLOW_UP", "EMERGENCY", "CONSULTATION"].map(v => (
                                            <label key={v}>
                                                <input
                                                    type="radio"
                                                    name="visit"
                                                    value={v}
                                                    onChange={(e) => setVisitType(e.target.value)}
                                                /> {v.replace("_", " ")}
                                            </label>
                                        ))}
                                    </div>
                                </div>

                                {/* SYMPTOMS */}
                                <div className="mb-3">
                                    <label>Symptoms</label>
                                    <textarea
                                        className="form-control"
                                        onChange={(e) => setSymptoms(e.target.value)}
                                    />
                                </div>

                                {/* SUBMIT */}
                                <button className="btn btn-primary">
                                    Book Appointment
                                </button>

                            </form>
                        </div>

                    </div>
                </div>
            </div>

            <div className="modal-backdrop fade show"></div>
        </>
    );
}