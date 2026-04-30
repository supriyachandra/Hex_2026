
import AdminSidebar from "./admin-sidebar";
import PageHeader from "../all/page-header";
import DoctorSearchDropdown from "./doctor-dropdown";
import ScheduleForm from "./schedule-form";
import ScheduleList from "./schedule-list";
import { useState } from "react";

export function ManageDoctorSchedule() {

    const [selectedDoctor, setSelectedDoctor] = useState(null);
    const [refresh, setRefresh] = useState(false);

    return (
        <div className="container-fluid p-4">
            <div className="row">

                <div className="col-sm-2">
                    <AdminSidebar />
                </div>

                <div className="col-md-10 p-4">

                    <PageHeader
                        title="Doctor Schedule"
                        subtitle="Manage doctor's schedules"
                    />

                    {/*  SEARCH DOCTOR */}
                    <DoctorSearchDropdown
                        onSelect={(doc) => setSelectedDoctor(doc)}
                    />


                    {/*  ONLY SHOW WHEN DOCTOR SELECTED */}
                    {selectedDoctor && (
                        <>
                            <div className="mt-4">

                                <h5>
                                    Selected: <span className="text-primary">
                                        Dr. {selectedDoctor?.name}
                                    </span>
                                </h5>

                                {/*  ADD */}
                                <ScheduleForm
                                    doctorId={selectedDoctor.id}
                                    onSuccess={() => setRefresh(!refresh)}
                                />

                                {/*  VIEW */}
                                <ScheduleList
                                    doctorId={selectedDoctor.id}
                                    refresh={refresh}
                                />

                            </div>
                        </>
                    )}

                </div>
            </div>
        </div>
    );
}