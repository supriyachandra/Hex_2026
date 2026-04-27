import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";

function SignUp() {

    
const [name, setName] = useState(undefined)
const [dob, setDob] = useState(undefined)
const [gender, setGender] = useState(undefined)
const [mobile, setMobile] = useState(undefined)
const [username, setUsername] = useState(undefined)
const [password, setPassword] = useState(undefined)
const [errMsg, setErrMsg] = useState(undefined)
const [succMsg, setSuccMsg] = useState(undefined)

const patientSignUpApi = "http://localhost:8080/api/patient/sign-up"

const processsSignUp = async (e) => {
    e.preventDefault()

    try {
        await axios.post(patientSignUpApi, {
            "name": name,
            "DOB": dob,
            "gender": gender,
            "phone": mobile,
            "username": username,
            "password": password
        })

        setSuccMsg("Account Created Successfully!")
    }
    catch (err) {
        setErrMsg(err)
    }

    processsSignUp()
}

    return (
        <div className="login-page">
            <div className="container">
                <div className="row justify-content-center">

                    <div className="col-md-7">

                        <div className="signup-header text-center">
                            <h2>Create your AmazeCare Account</h2>
                            <p className="subtitle">Join AmazeCare</p>
                            <p className="subtitle-light">
                                Start managing your healthcare the smarter way
                            </p>
                        </div>

                        {
                                succMsg == undefined ? "" :
                                    <div className="alert alert-primary mt-4">
                                        {succMsg}
                                    </div>
                            }

                        {/* CARD */}
                        <div className="card signup-card">
                            <div className="card-header">
                                Patient Sign Up
                            </div>


                            <div className="card-body">
                                <form onSubmit={(e)=> processsSignUp(e)}>

                                    <div className="mb-3">
                                        <label>Name</label>
                                        <input type="text" className="form-control" required 
                                        onChange={(e)=> setName(e.target.value)}/>
                                    </div>

                                    <div className="mb-3">
                                        <label>Date of Birth</label>
                                        <input type="date" className="form-control" required 
                                        onChange={(e)=> setDob(e.target.value)}/>
                                    </div>

                                    {/* GENDER */}
                                    <div className="mb-3">
                                        <label className="d-block mb-2">Gender</label>
                                        <div className="gender-group">
                                            <label>
                                                <input type="radio" name="gender" value="MALE" 
                                                onChange={(e)=> setGender(e.target.value)}/> Male
                                            </label>
                                            <label>
                                                <input type="radio" name="gender" value="FEMALE" 
                                                onChange={(e)=> setGender(e.target.value)}/> Female
                                            </label>
                                            <label>
                                                <input type="radio" name="gender" value="OTHERS" 
                                                onChange={(e)=> setGender(e.target.value)}/> Others
                                            </label>
                                        </div>
                                    </div>

                                    <div className="mb-3">
                                        <label>Mobile</label>
                                        <input type="number" className="form-control" required 
                                        onChange={(e)=> setMobile(e.target.value)}/>
                                    </div>

                                    <div className="mb-3">
                                        <label>Username</label>
                                        <input type="text" className="form-control" required 
                                        onChange={(e)=> setUsername(e.target.value)}/>
                                    </div>

                                    <div className="mb-3">
                                        <label>Password</label>
                                        <input type="password" className="form-control" required 
                                        onChange={(e)=> setPassword(e.target.value)}/>
                                    </div>

                                    <div className="mt-3">
                                        <button className="btn btn-primary signup-btn" type="submit">
                                            SIGN UP
                                        </button>
                                    </div>

                                    {/* LOGIN LINK */}
                                    <div className="auth-link mt-3 text-center">
                                        Already have an account?
                                        <Link to="/login">Login</Link>
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
    )
}

export default SignUp;