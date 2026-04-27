import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { getTodayAppointments } from "../redux/actions/appointmentAction";
import AdminSidebar from "./admin-sidebar";
import PageHeader from "../all/page-header";

function AdminDashboard() {

    const getAdminApi = "http://localhost:8080/api/admin/get-admin";

    const [admin, setAdmin] = useState(undefined);
    const [counts, setCounts] = useState({ doctors: 0, patients: 0, admitted: 0 });

    const navigate = useNavigate();
    const dispatch = useDispatch();

    // Redux state (ONLY appointments)
    const { appointments } = useSelector(
        state => state.appointmentReducer
    );

    useEffect(() => {

        const fetchAdmin = async () => {

            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            };

            try {
                const response = await axios.get(getAdminApi, config);
                setAdmin(response.data.name);

                const [docRes, patRes, admRes] = await Promise.all([
                    axios.get("http://localhost:8080/api/doctor/count-doc", config),
                    axios.get("http://localhost:8080/api/patient/count-patient", config),
                    axios.get("http://localhost:8080/api/admission/currently-admitted", config)
                ]);

                setCounts({
                    doctors: docRes.data,
                    patients: patRes.data,
                    admitted: admRes.data
                });

                //  ONLY Redux call here
                dispatch(getTodayAppointments());

            } catch (err) {
                navigate("/login");
                console.log(err);
            }
        };

        fetchAdmin();

    }, [dispatch]);

    return (
        <div className="container-fluid p-4">

            <div className="row">
                <div className="col-sm-2">
                    <AdminSidebar/>
                </div>

                <div className="col-lg-10">

                    <PageHeader
                        title="Hospital Overview"
                        subtitle="Realtime pulse of the hospital"
                    />

                    {/* Dashboard Cards */}
                    <div className="row mt-4">

                        <div className="col-md-4">
                            <div className="card shadow-sm border-0 bg-primary text-white">
                                <div className="card-body">
                                    <h5>Total Doctors</h5>
                                    <h2>{counts.doctors}</h2>
                                </div>
                            </div>
                        </div>

                        <div className="col-md-4">
                            <div className="card shadow-sm border-0 bg-success text-white">
                                <div className="card-body">
                                    <h5>Registered Patients</h5>
                                    <h2>{counts.patients}</h2>
                                </div>
                            </div>
                        </div>

                        <div className="col-md-4">
                            <div className="card shadow-sm border-0 bg-warning text-dark">
                                <div className="card-body">
                                    <h5>Currently Admitted</h5>
                                    <h2>{counts.admitted}</h2>
                                </div>
                            </div>
                        </div>

                    </div>

                    {/* Today's Appointments */}
                    <div className="row mt-5">
                        <div className="col-12">
                            <div className="card shadow-sm">
                                <div className="card-header bg-white">
                                    <h5 className="mb-0">Today's Appointments</h5>
                                </div>

                                <div className="card-body">
                                    <table className="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>Patient Name</th>
                                                <th>Doctor</th>
                                                <th>Time</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            {appointments.length > 0 ? (
                                                appointments.map((app, index) => (
                                                    <tr key={index}>
                                                        <td>{app.patient_name}</td>
                                                        <td>{app.doctor_name}</td>
                                                        <td>{app.app_time_slot}</td>
                                                        <td>
                                                            <span className="badge bg-info">
                                                                {app.status}
                                                            </span>
                                                        </td>
                                                    </tr>
                                                ))
                                            ) : (
                                                <tr>
                                                    <td colSpan="4" className="text-center text-muted">
                                                        No appointments scheduled for today.
                                                    </td>
                                                </tr>
                                            )}
                                        </tbody>

                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    );
}

export default AdminDashboard;