import AdminSidebar from "./admin-sidebar";
import PageHeader from "../all/page-header";
import { NavLink, Outlet, useNavigate } from "react-router-dom";

export function AdminAdmissions() {

    const navigate = useNavigate();


    return (
        <div className="container-fluid p-4">
            <div className="row">

                {/* SIDEBAR */}
                <div className="col-sm-2">
                    <AdminSidebar />
                </div>

                {/* MAIN */}
                <div className="col-md-10 p-4">

                    <PageHeader
                        title="Admissions"
                        subtitle="Manage admitted patients and discharge records"
                    />

                    {/* TOP BAR */}
                    <div className="d-flex justify-content-between align-items-center mb-3">

                        {/* TABS */}
                        <div className="mt-2">
                            <NavLink
                                to="/admin-admissions"
                                className={({ isActive }) =>
                                    `btn me-2 ${isActive ? "btn-primary" : "btn-outline-primary"}`
                                }
                                end
                            >
                                Active
                            </NavLink>

                            <NavLink
                                to="/admin-admissions/past-ipd"
                                className={({ isActive }) =>
                                    `btn ${isActive ? "btn-primary" : "btn-outline-primary"}`
                                }
                            >
                                Past
                            </NavLink>
                        </div>

                        {/* ADD BUTTON */}
                        <button
                            className="btn btn-success"
                            onClick={() => navigate("/admin-patients")}
                        >
                            + Admit Patient
                        </button>
                    </div>

                    {/* OUTLET */}
                    <Outlet />

                </div>

            </div>
        </div>
    )
}