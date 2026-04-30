import { useEffect, useState } from "react";
import axios from "axios";

export default function DoctorSearchDropdown({ onSelect }) {

    const [doctors, setDoctors] = useState([]);
    const [search, setSearch] = useState("");
    const [showDropdown, setShowDropdown] = useState(false);

    useEffect(() => {
        const fecthDoctors = async() => {
            try {
                const res = await axios.get("http://localhost:8080/api/doctor/get-all/v2", {
                    headers: {
                        Authorization: "Bearer "+ localStorage.getItem("token")
                    }
                })
                setDoctors(res.data)
            }
            catch (err) { err }
        }
        fecthDoctors()
    }, []);

    // filtered doctors list -- whenever u use filtered, search item is filtered on doctor
    const filtered = doctors.filter(d =>
        d.name.toLowerCase().includes(search.toLowerCase()) ||
        d.specialization.toLowerCase().includes(search.toLowerCase())
    );

    return (
        <div className="position-relative mb-3">

            <input
                className="form-control"
                placeholder="Search by doctor or specialization..."
                value={search}
                onFocus={() => setShowDropdown(true)}
                onChange={(e) => setSearch(e.target.value)}
            />

            {showDropdown && (
                <div className="list-group position-absolute w-100 shadow"
                    style={{ maxHeight: "200px", overflowY: "auto", zIndex: 1000 }}>

                    {filtered.map(d => (
                        <button
                            key={d.id}
                            className="list-group-item list-group-item-action"
                            onClick={() => {
                                setSearch(d.name);
                                setShowDropdown(false);
                                onSelect(d); //prop, function data is sent to parent (doctor is sent)
                            }}
                        >
                            {d.name} • {d.specialization}
                        </button>
                    ))}

                </div>
            )}
        </div>
    );
}