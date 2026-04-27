import axios from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

function Login() {
    const [username, setUsername] = useState(undefined)
    const [password, setPassword] = useState(undefined)
    const [errMsg, setErrMsg] = useState(undefined)

    const loginApi = "http://localhost:8080/api/auth/login"
    const userDetailsApi= "http://localhost:8080/api/auth/user-details"

    const navigate= useNavigate()

    const processLogin = async (e) => {
        e.preventDefault()
        console.log(username + " " + password)

        try {

            // generate encoded string to pass to authentication header. This basically is the Principle to give to the login API
            let encodedString = window.btoa(username + ':' + password)

            const loginHeader = {
                headers: {
                    "Authorization": 'Basic ' + encodedString
                }
            }

            const response = await axios.get(loginApi, loginHeader)

            //console.log(response.data.token)

            const userDetailsHeader = {
                headers: {
                    "Authorization": 'Bearer ' + response.data.token
                }
            }

            const userDetailsResponse= await axios.get(userDetailsApi, userDetailsHeader)
            
            //console.log(response.data.token)

            localStorage.setItem("token", response.data.token)

            const role= userDetailsResponse.data.role

            console.log(role)

            switch(role){
                case "PATIENT":
                    navigate("/patient-dashboard")
                    break;
                case "ADMIN":
                    navigate("/admin-dashboard")
                    break;
                case "DOCTOR":
                    navigate("/doctor-dashboard")
                    break;
            }
        }
        catch (err) {
            setErrMsg(err.message)
            setErrMsg("Invalid Credentials! Please check Username or Password")
        }
    }

    return (
        <div className="login-page">
            <div className="container">
                <div className="row align-items-center">

                    {/* LEFT SIDE */}
                    <div className="col-md-6 login-left">
                        <h1>AmazeCare</h1>
                        <p>Simplifying healthcare management</p>
                        <p>for patients, doctors, and administrators</p>
                        <p><strong>Fast. Secure. Efficient.</strong></p>
                    </div>

                    {/* RIGHT SIDE */}
                    <div className="col-md-6">
                        {
                            errMsg== undefined ? "":
                                <div className="login-error">
                                    {errMsg}
                                </div>
                            
                        }
                        <div className="card login-card">
                            <div className="card-header">
                                Login
                            </div>

                            <div className="card-body">
                                <form onSubmit={(e) => processLogin(e)}>
                                    <div className="mb-3">
                                        <label>Username</label>
                                        <input type="text" className="form-control" required
                                            onChange={(e) => setUsername(e.target.value)} />
                                    </div>

                                    <div className="mb-3">
                                        <label>Password</label>
                                        <input type="password" className="form-control" required
                                            onChange={(e) => setPassword(e.target.value)} />
                                    </div>

                                    <div className="mt-3">
                                        <button className="btn btn-primary login-btn">
                                            LOGIN
                                        </button>
                                    </div>

                                    <div className="auth-link mt-3 text-center">
                                        Don't have an account?
                                        <Link to="/signup"> Sign Up</Link>
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

export default Login;