import { useEffect, useState } from "react";
import PageHeader from "../all/page-header";
import { Doctors } from "./doctors";
import PatientSidebar from "./patient-sidebar";
import axios from "axios";

export function BookByDoctor() {
    const SpecializationApi = "http://localhost:8080/api/specialization/get-all"

    const filteredDoctorsApi = "http://localhost:8080/api/doctor/filter/name/spec"

    const [specialization, setSpecialization] = useState([])
    const [searchTerm, setSearchTerm] = useState("")
    const [selectedSpec, setSelectedSpec] = useState("")

    const [filterDoctor, setFilterDoctor] = useState([])


    useEffect(() => {
        const fetchSpecializations = async () => {
            try {
                const response = await axios.get(SpecializationApi)
                setSpecialization(response.data)
            }
            catch (err) { err }
        }
        fetchSpecializations()

    }, [])


    useEffect(() => {

        const fetchFilteredDoctors = async () => {
            const config = {

                params: {},
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            };

            const params = {};
            if (searchTerm) config.params.name = searchTerm;
            if (selectedSpec) config.params.spec_id = selectedSpec;

            try {
                const response = await axios.get(filteredDoctorsApi, config);
                setFilterDoctor(response.data)
            }
            catch (err) { err }

        }
        fetchFilteredDoctors()

    }, [searchTerm, selectedSpec])



    return (
        <div className="container-fluid">
            <div className="row">
                <div className="col-sm-2 p-4">

                    <PatientSidebar />
                </div>


                {/* MAIN CONTENT */}
                <div className="col-md-10 p-4">

                    <PageHeader
                        title="Book by Doctor"
                        subtitle="Browse specialists and book appointments easily"
                    />

                    {/* SEARCH + FILTER */}
                    <div className="card p-3 mb-4">
                        <div className="row">
                            <div className="col-md-8">
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Search doctors..."
                                    onChange={(e) => setSearchTerm(e.target.value)}
                                />
                            </div>

                            <div className="col-md-4">
                                <select className="form-control"
                                    onChange={(e) => setSelectedSpec(e.target.value)}>
                                    <option value="">All specializations</option>

                                    {
                                        specialization.map((s, index) => (
                                            <option key={index}
                                                value={s.id}>
                                                {s.name}
                                            </option>
                                        ))
                                    }
                                </select>
                            </div>
                        </div>
                    </div>

                    <Doctors doctors={filterDoctor} />

                </div>
            </div>

        </div>
    )
}