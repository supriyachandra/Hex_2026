import PatientNavbar from "./patient-sidebar";
import { useNavigate } from "react-router-dom";

export function Doctors({ doctors, selectedDate}) {


    const navigate = useNavigate()

    return (
        <div className="row">

            {
                doctors.map((doc, index) => (
                    <div className="col-lg-4 col-md-6 mb-4" key={index}>
                        <div className="doctor-card h-100">

                            {/* TOP SECTION */}
                            <div className="d-flex mb-3">

                                {/* IMAGE */}
                                <img
                                    src="public/image/default_doctor.jpg"
                                    alt="doctor"
                                    className="doctor-img"
                                />

                                {/* BASIC INFO */}
                                <div className="ms-3">
                                    <h5 className="mb-1">{doc.name}</h5>

                                    <div className="text-muted small">
                                        {doc.designation}
                                    </div>

                                    <div className="text-muted small">
                                        {doc.specialization}
                                    </div>
                                </div>
                            </div>

                            {/* DETAILS */}
                            <div className="doctor-info mb-3">

                                <p>
                                    <i className="bi bi-mortarboard me-2"></i>
                                    {doc.qualification}
                                </p>

                                <p>
                                    <i className="bi bi-briefcase me-2"></i>
                                    {doc.experience} years experience
                                </p>

                                <p>
                                    <i className="bi bi-envelope me-2"></i>
                                    {doc.email}
                                </p>
                            </div>

                            {/* BUTTON */}
                            <button
                                className="btn btn-book w-100 mt-auto"
                                onClick={() =>navigate(`/book-appointment/${doc.id}`,
                                        {state: { selectedDate }})
                                }
                            >
                                Book Appointment
                            </button>

                        </div>
                    </div>
                ))
            }
        </div>
    )
}