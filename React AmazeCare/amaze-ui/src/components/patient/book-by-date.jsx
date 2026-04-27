import PageHeader from "../all/page-header";
import PatientSidebar from "./patient-sidebar";

import { useEffect, useState } from "react";
import axios from "axios";
import { Doctors } from "./doctors";

export function BookByDate() {

    const api = "http://localhost:8080/api/doctor/available"

    const [selectedDate, setSelectedDate] = useState("")
    const [doctors, setDoctors] = useState([])

    useEffect(() => {

        if (!selectedDate) return; //don't call API if empty

        const fetchDoctors = async () => {
            try {
                const response = await axios.get(api, {
                    params: {
                        date: selectedDate
                    },
                });

                setDoctors(response.data)

            } catch (err) {
                console.error(err);
            }
        };

        fetchDoctors();

    }, [selectedDate]);


    return (
        <div className="container-fluid main-content">
    <div className="row">

        {/* SIDEBAR */}
        <div className="col-md-2 sidebar p-3">
            <PatientSidebar />
        </div>

        {/* MAIN */}
        <div className="col-md-10 p-4">

            <PageHeader
                title="Book Appointment"
                subtitle="Select a date to see available doctors"
            />

            {/* DATE + FILTER BAR */}
            <div className="dashboard-card p-3 mb-4 d-flex align-items-center gap-3">

                <div style={{ maxWidth: "250px" }}>
                    <label className="label">Select Date</label>
                    <input
                        type="date"
                        className="form-control"
                        value={selectedDate}
                        onChange={(e) => setSelectedDate(e.target.value)}
                    />
                </div>

                {/*  spacing filler */}
                <div className="flex-grow-1"></div>

                {/*  small helper text */}
                <span className="text-muted small">
                    Showing available doctors
                </span>
            </div>

            {/* DOCTORS */}
            <div className="dashboard-card p-3">
                <Doctors doctors={doctors} selectedDate={selectedDate} />
            </div>

        </div>
    </div>
</div>
    );
}