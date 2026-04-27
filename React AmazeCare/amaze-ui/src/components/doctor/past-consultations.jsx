
import PageHeader from "../all/page-header";
import DoctorSidebar from "./doctor-sidebar";

import { Outlet, NavLink } from "react-router-dom";


export function PastConsultations() {


    return (
        <div className="container-fluid">
            <div className="row">

                <div className="col-sm-2 p-4">
                    <DoctorSidebar />
                </div>

                <div className="col-md-10 p-4">

                    <PageHeader
                        title="Consultations"
                        subtitle="View OPD and IPD consultation history"
                    />


                    {/* 🔁 TABS */}
                    <div className="mb-3">
                        <NavLink
                            to="/past-consultations"
                            className="btn btn-outline-primary me-2"
                        >
                            OPD
                        </NavLink>

                        <NavLink
                            to="/past-consultations/ipd-consultations"
                            className="btn btn-outline-secondary"
                        >
                            IPD
                        </NavLink>
                    </div>

                    {/* 🔁 CHILD */}
                    <Outlet />

                </div>
            </div>
        </div>
    );
}