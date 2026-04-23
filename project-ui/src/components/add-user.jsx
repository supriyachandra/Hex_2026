import axios from "axios"
import { useState } from "react"
import { Link } from "react-router-dom"

export function AddUser() {

    const [name, setName] = useState(undefined)
    const [email, setEmail] = useState(undefined)
    const [phone, setPhone] = useState(undefined)
    const [company, setCompany] = useState(undefined)

    const [success, setSuccess] = useState(undefined)

    const api = "https://jsonplaceholder.typicode.com/users"

    const submitForm = async (e) => {
        e.preventDefault()

        try {
            await axios.post(api, {
                "name": name,
                "email": email,
                "phone": phone,
                "company": {
                    "name": company
                }
            })
            setSuccess("User Added Successfully!")
        }
        catch (err) {
            console.log(err)
        }
    }
    return (
        <div className="container-fluid p-4">
            <div className="row mt-4 mb-4">
                <div className="col-sm-2"></div>
                <div className="col-md-8">
                    {
                        success === undefined ? "" :
                            <div className="alert alert-success mt-4">
                                {success}
                            </div>
                    }
                    <div className="card mb-2">
                        <div className="card-header">
                            Add New User
                        </div>
                        <div className="card-body">
                            <form onSubmit={(e) => submitForm(e)}>
                                {/* name */}
                                <div className="row mt-2">
                                    <div className="col-md-3">
                                        <label>Enter Name</label>
                                    </div>
                                    <div className="col-md-9">
                                        <input className="form-control"
                                            type="text"
                                            placeholder="eg. Harry Potter"
                                            required= "required"
                                            onChange={(e) => setName(e.target.value)} />
                                    </div>
                                </div>

                                {/* email */}
                                <div className="row mt-2">
                                    <div className="col-md-3">
                                        <label>Enter Email</label>
                                    </div>
                                    <div className="col-md-9">
                                        <input className="form-control"
                                            type="email"
                                            placeholder="eg. harry@gmail.com"
                                            required= "required"
                                            onChange={(e) => setEmail(e.target.value)} />
                                    </div>
                                </div>

                                {/* phone number */}
                                <div className="row mt-2">
                                    <div className="col-md-3">
                                        <label>Enter Phone Number</label>
                                    </div>
                                    <div className="col-md-9">
                                        <input className="form-control"
                                            type="number"
                                            placeholder="eg. 9999000099"
                                            required= "required"
                                            onChange={(e) => setPhone(e.target.value)} />
                                    </div>
                                </div>

                                {/* company name */}
                                <div className="row mt-2 mb-2">
                                    <div className="col-md-3">
                                        <label>Enter Company Name</label>
                                    </div>
                                    <div className="col-md-9">
                                        <input className="form-control"
                                            type="text"
                                            placeholder="eg. Hexaware Technologies"
                                            required= "required"
                                            onChange={(e) => setCompany(e.target.value)} />
                                    </div>
                                </div>

                                <button className="btn btn-primary" type="submit">
                                    SUBMIT
                                </button>

                            </form>

                            
                        </div>
                    </div>
                    <Link to={'/'}> Back to Dashboard</Link>
                </div>
            </div>
        </div>
    )
}