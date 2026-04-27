import { useState } from "react";
import axios from "axios";

export default function RegisterPatientModal({ show, onClose, onSuccess }) {

    const registerPatientApi = "http://localhost:8080/api/patient/create";

    const [name, setName] = useState("");
    const [mobile, setMobile] = useState("");
    const [dob, setDob] = useState("");
    const [gender, setGender] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await axios.post(registerPatientApi, {
                name: name,
                DOB: dob,
                gender: gender,
                phone: mobile
            }, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            });


            onClose();

            // refresh parent
            if (onSuccess) onSuccess();

            //reset form
            setName("");
            setMobile("");
            setDob("");
            setGender("");

        } catch (err) {
            console.log(err);
        }
    };

    if (!show) return null;

    return (
        <div className="modal show d-block">

            <div className="modal-dialog">
                <div className="modal-content">

                    {/* HEADER */}
                    <div className="modal-header">
                        <h5>Register Patient</h5>
                        <button className="btn-close" onClick={onClose}></button>
                    </div>

                    {/* BODY */}
                    <form onSubmit={handleSubmit}>
                        <div className="modal-body">

                            <label>Full Name</label>
                            <input
                                className="form-control mb-2"
                                placeholder="Name"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                required
                            />

                            <label>Mobile Number</label>
                            <input
                                className="form-control mb-2"
                                placeholder="Mobile"
                                value={mobile}
                                onChange={(e) => setMobile(e.target.value)}
                                required
                            />

                            <label>Date Of Birth</label>
                            <input
                                type="date"
                                className="form-control mb-2"
                                value={dob}
                                onChange={(e) => setDob(e.target.value)}
                                required
                            />

                            <label>Gender</label>
                            <select
                                className="form-control"
                                value={gender}
                                onChange={(e) => setGender(e.target.value)}
                                required
                            >
                                <option value="">Select Gender</option>
                                <option value="MALE">Male</option>
                                <option value="FEMALE">Female</option>
                                <option value="OTHER">Other</option>
                            </select>

                        </div>

                        {/* FOOTER */}
                        <div className="modal-footer">
                            <button
                                type="button"
                                className="btn btn-secondary"
                                onClick={onClose}
                            >
                                Cancel
                            </button>

                            <button type="submit" className="btn btn-primary">
                                Register
                            </button>
                        </div>
                    </form>

                </div>
            </div>

        </div>
    );
}