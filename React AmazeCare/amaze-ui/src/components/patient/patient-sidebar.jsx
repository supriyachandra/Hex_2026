import { Link, useLocation } from "react-router-dom";

function PatientSidebar() {

    const location = useLocation();

    return (
        <div className="sidebar">

            {/* LOGO */}
            <h5 className="sidebar-logo">
                <img src="/image/full logo.png" alt="Amazecare logo" />
            </h5>

            <p className="auth-link">Patient</p>

            <ul className="nav flex-column sidebar-nav">

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/patient-dashboard" ? "active" : ""}`}
                        to="/patient-dashboard"
                    >
                        Overview
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/book-by-doctor" ? "active" : ""}`}
                        to="/book-by-doctor"
                    >
                        Book by Doctor
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/book-by-date" ? "active" : ""}`}
                        to="/book-by-date"
                    >
                        Book by Date
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/appointments" ? "active" : ""}`}
                        to="/appointments"
                    >
                        My Appointments
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/medical-history" ? "active" : ""}`}
                        to="/medical-history"
                    >
                        Medical History
                    </Link>
                </li>

            </ul>

        </div>
    );
}

export default PatientSidebar;