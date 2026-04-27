import { Link, useLocation } from "react-router-dom";

function AdminSidebar() {

    const location = useLocation();

    return (
        <div className="sidebar">

            {/* LOGO */}
            <h5 className="sidebar-logo">
                <img src="/image/full logo.png" alt="Amazecare logo" />
            </h5>

            <p className="auth-link">Admin</p>

            <ul className="nav flex-column sidebar-nav">

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/admin-dashboard" ? "active" : ""}`}
                        to="/admin-dashboard"
                    >
                        Overview
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/admin-doctors" ? "active" : ""}`}
                        to="/admin-doctors"
                    >
                        Doctors
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/admin-doctor-schedule" ? "active" : ""}`}
                        to="/admin-doctor-schedule"
                    >
                        Manage Doctor Schedule
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/admin-patients" ? "active" : ""}`}
                        to="/admin-patients"
                    >
                        Patients
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/admin-admissions" ? "active" : ""}`}
                        to="/admin-admissions"
                    >
                        Admissions
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className={`nav-link sidebar-link ${location.pathname === "/admin-appointments" ? "active" : ""}`}
                        to="/admin-appointments"
                    >
                        Appointments
                    </Link>
                </li>

            </ul>

        </div>
    );
}

export default AdminSidebar;