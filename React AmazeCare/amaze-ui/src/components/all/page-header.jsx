import { useNavigate } from "react-router-dom";

function PageHeader({ title, subtitle }) {

    const navigate = useNavigate();

    const logout = () => {
        localStorage.clear();
        navigate("/login");
    };

    return (
        <div className="page-header d-flex justify-content-between align-items-center">

            {/* left */}
            <div>
                <h4 className="page-title">{title}</h4>
                {subtitle && (
                    <p className="page-subtitle">{subtitle}</p>
                )}
            </div>

            {/* right */}
            <div className="header-actions">


                <button
                    className="btn btn-outline-danger"
                    onClick={logout}
                >
                    Logout
                </button>

            </div>

        </div>
    );
}

export default PageHeader;