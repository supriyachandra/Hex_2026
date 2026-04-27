import { NavLink, Outlet } from "react-router-dom";
import DoctorSidebar from "./doctor-sidebar";
import PageHeader from "../all/page-header";

export function DoctorAdmissionsLayout() {

    return (
        <div className="container-fluid p-4">
            <div className="row">

                <div className="col-sm-2">
                    <DoctorSidebar />
                </div>

                <div className="col-lg-10">

                    <PageHeader
                        title="Admissions"
                        subtitle="Manage your patients"
                    />

                    {/* TABS */}
                    <div className="mb-3">

                        <NavLink
                            to="/ipd-patients"
                            end
                            className={({ isActive }) =>
                                `btn me-2 ${isActive ? "btn-primary" : "btn-outline-primary"}`
                            }
                        >
                            Active
                        </NavLink>

                        <NavLink
                            to="/ipd-patients/doc-past-ipd"
                            className={({ isActive }) =>
                                `btn ${isActive ? "btn-primary" : "btn-outline-primary"}`
                            }
                        >
                            Past
                        </NavLink>

                    </div>

                    <Outlet />

                </div>
            </div>
        </div>
    );
}