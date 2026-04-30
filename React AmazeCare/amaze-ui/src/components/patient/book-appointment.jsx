import axios from "axios"
import { useEffect, useState } from "react"
import { Link, useLocation, useParams } from "react-router-dom"
import PatientSidebar from "./patient-sidebar";
import PageHeader from "../all/page-header";

export function BookAppointment() {

    const { doctorId } = useParams()

    // for reading the state (selectedState) use location
    const location = useLocation()
    const preselectedDate = location.state?.selectedDate

    const [appointmentDate, setAppointmentDate] = useState(preselectedDate || "");
    const [timeSlot, setTimeSlot] = useState();
    const [symptoms, setSymptoms] = useState();
    const [visitType, setVisitType] = useState();
    const [smsg, setSmsg] = useState(undefined)

    const [dates, setDates] = useState([])
    const [times, setTimes] = useState([])


    const bookApi = `http://localhost:8080/api/appointment/book/${doctorId}`

    const datesApi = `http://localhost:8080/api/schedule/get-by/${doctorId}`

    const timesApi = `http://localhost:8080/api/schedule/slots/${doctorId}/${appointmentDate}`

    useEffect(() => {

        const fetchDates = async () => {
            try {
                const dateResponse = await axios.get(datesApi, {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                })

                const uniqueDates = [...new Set(dateResponse.data.map(d => d.date))]

                setDates(uniqueDates)
            }
            catch (err) {
                console.log(err)
            }
        }

        fetchDates()

    }, [])


    useEffect(() => {
        if (!appointmentDate) return    //return if date not selected

        const fetchTimes = async () => {
            try {
                const res = await axios.get(timesApi,
                    {
                        headers: {
                            "Authorization": "Bearer " + localStorage.getItem("token")
                        }
                    }
                )

                setTimes(res.data)
            } catch (err) {
                console.log(err)
            }
        }

        fetchTimes()

    }, [appointmentDate])


    const bookApp = async (e) => {
        e.preventDefault()

        try {
            await axios.post(bookApi, {
                "appointmentDate": appointmentDate,
                "timeSlot": timeSlot,
                "symptoms": symptoms,
                "visitType": visitType
            },
                {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                }
            )
            setSmsg("Appointment Booked!")
        }
        catch (err) {
            console.log(err)
        }
    }


    return (

        <div className="container-fluid">
            <div className="row">
                <div className="col-sm-2 p-4">
                    <PatientSidebar />
                </div>

                <div className="col-md-10 p-4">
                    <PageHeader
                        title="Book Appointment"
                        subtitle="Enter the details and book now"
                    />

                    {/* main section */}
                    <div className="row justify-content-center mt-4">

                        <div className="col-md-7">

                            {/* success message and redirect to appointments choice*/}

                            {
                                smsg == undefined ? "" :
                                    <div className="alert alert-primary mt-4">
                                        {smsg}
                                        <Link to="/appointments"> View Your Appointments</Link>
                                    </div>
                            }


                            <div className="card signup-card">

                                {/**Dates Dropdown */}
                                <div className="card-body">
                                    <form onSubmit={(e) => bookApp(e)}>

                                        {
                                        preselectedDate ? (
                                            <div className="mb-3">
                                                <label>Select Date</label>
                                                <div className="date-display">
                                                    {new Date(appointmentDate).toDateString()}
                                                </div>
                                            </div>
                                        ) : (
                                            <div className="mb-3">
                                                <label>Select Date</label>
                                                <select className="form-control"
                                                    value={appointmentDate}
                                                    onChange={(e) => {
                                                        setAppointmentDate(e.target.value)
                                                        setTimeSlot("")
                                                    }}
                                                >
                                                    <option value="">Select</option>
                                                    {dates.map((d, index) => (
                                                        <option key={index} value={d}>{d}</option>
                                                    ))}
                                                </select>
                                            </div>
                                        )}

                                        {/* time slots buttons */}
                                        <div className="mb-3">
                                            <label>Select Time</label>

                                            <div className="d-flex flex-wrap gap-2 mt-2">

                                                {times.map((t, index) => (
                                                    <button
                                                        key={index}
                                                        type="button"
                                                        className={`btn ${timeSlot === t.time ? "btn-primary" : "btn-outline-primary"
                                                            }`}
                                                        onClick={() => setTimeSlot(t.time)}
                                                    >
                                                        {t.time}
                                                    </button>
                                                ))}

                                            </div>
                                        </div>


                                        {/* Visit Type */}
                                        <div className="mb-3">
                                            <label className="d-block mb-2">Visit Type</label>
                                            <div className="gender-group">
                                                <label>
                                                    <input type="radio" name="visit" value="GENERAL_CHECKUP"
                                                        onChange={(e) => setVisitType(e.target.value)} /> General Checkup
                                                </label>
                                                <label>
                                                    <input type="radio" name="visit" value="FOLLOW_UP"
                                                        onChange={(e) => setVisitType(e.target.value)} /> Follow-Up
                                                </label>
                                                <label>
                                                    <input type="radio" name="visit" value="EMERGENCY"
                                                        onChange={(e) => setVisitType(e.target.value)} /> Emercency
                                                </label>
                                                <label>
                                                    <input type="radio" name="visit" value="CONSULTATION"
                                                        onChange={(e) => setVisitType(e.target.value)} /> Consultation
                                                </label>
                                            </div>
                                        </div>

                                        <div className="mb-3">
                                            <label>Symptoms</label>
                                            <textarea className="form-control"
                                                onChange={(e) => setSymptoms(e.target.value)}
                                                required>

                                            </textarea>
                                        </div>



                                        <div className="mt-3">
                                            <button className="btn btn-primary signup-btn" type="submit">
                                                BOOK APPOINTMENT
                                            </button>
                                        </div>


                                    </form>
                                </div>

                            </div>

                            <div className="footer text-center">
                                © Amazecare Hospital System
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>


    )
}