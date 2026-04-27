import { Link, useLocation } from "react-router-dom";

function DoctorSidebar() {

    const location = useLocation();

    return (
        <div className="sidebar">

            {/* LOGO */}
            <h5 className="sidebar-logo">
                <img src="/image/full logo.png" alt="Amazecare logo" />
            </h5>

            <p className="auth-link">Doctor</p>

            <ul className="nav flex-column sidebar-nav">

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/doctor-dashboard" ? "active" : ""}`}
                        to="/doctor-dashboard"
                    >
                        Overview
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/check-appointments" ? "active" : ""}`}
                        to="/check-appointments"
                    >
                        Appointments
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/ipd-patients" ? "active" : ""}`}
                        to="/ipd-patients"
                    >
                        IPD Patients
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/past-consultations" ? "active" : ""}`}
                        to="/past-consultations"
                    >
                        Consultations
                    </Link>
                </li>

            </ul>

        </div>
    );
}

export default DoctorSidebar;