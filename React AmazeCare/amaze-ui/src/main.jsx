import './styles/base/variables.css';
import './styles/base/global.css';
import './styles/auth/login.css';
import './styles/patient.css';

import { createRoot } from 'react-dom/client'

import App from './App.jsx'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Login from './components/auth/login.jsx';
import PatientDashboard from './components/patient/patient-dashboard.jsx';
import AdminDashboard from './components/admin/admin-dashboard.jsx';
import DoctorDashboard from './components/doctor/doctor-dashboard.jsx';
import SignUp from './components/patient/signup.jsx';
import { PatientStats } from './components/patient/patient-stats.jsx';
import { Doctors } from './components/patient/doctors.jsx';
import { BookAppointment } from './components/patient/book-appointment.jsx';
import { MedicalHistory } from './components/patient/medical-history.jsx';
import { BookByDoctor } from './components/patient/book-by-doctor.jsx';
import { Appointments } from './components/patient/appointments.jsx';
import { BookByDate } from './components/patient/book-by-date.jsx';
import { CheckAppointments } from './components/doctor/check-appointments.jsx';
import { DoctorAdmissionsLayout } from './components/doctor/ipd-patients.jsx';
import { PastConsultations } from './components/doctor/past-consultations.jsx';
import { AdminPatients } from './components/admin/admin-patients.jsx';
import RegisterPatientModal from './components/admin/register-patient.jsx';
import AdmitPatientModal from './components/admin/admit-patient-modal.jsx';
import { AdminDoctors } from './components/admin/admin-doctor.jsx';
import { AdminAdmissions } from './components/admin/admin-admissions.jsx';
import { ActiveAdmissions } from './components/admin/active-ipd.jsx';
import { PastAdmissions } from './components/admin/past-ipd.jsx';
import { ActiveIpdPatients } from './components/doctor/doc-active-ipd.jsx';
import { DoctorPastAdmissions } from './components/doctor/doc-past-ipd.jsx';
import { OpdConsultations } from './components/doctor/opd-consultations.jsx';
import { IpdConsultations } from './components/doctor/ipd-consultations.jsx';
import { ManageDoctorSchedule } from './components/admin/admin-doctor-schedule.jsx';
import { AdminAppointments } from './components/admin/admin-appointments.jsx';
import { Provider } from 'react-redux'
import { store } from './components/store.js'

const routes = createBrowserRouter([
    {
        path: "/",
        element: <App />
    },
    {
        path: "/login",
        element: <Login />
    },
    {
        path: "/signup",
        element: <SignUp />
    },
    {
        path: "/patient-dashboard",
        element: <PatientDashboard />
    },
    {
        path: "/admin-dashboard",
        element: <AdminDashboard />
    },
    {
        path: "/doctor-dashboard",
        element: <DoctorDashboard />
    },
    {
        path: "/patient-stats",
        element: <PatientStats />
    }
    , {
        path: "/doctors",
        element: <Doctors />
    },
    {
        path: "/book-appointment/:doctorId",
        element: <BookAppointment />
    },
    {
        path: "/medical-history",
        element: <MedicalHistory />
    },
    {
        path: "/book-by-doctor",
        element: <BookByDoctor />
    },
    {
        path: "/appointments",
        element: <Appointments />
    }
    , {
        path: "/book-by-date",
        element: <BookByDate />
    },
    {
        path: "/check-appointments",
        element: <CheckAppointments />
    },
    {
        path: "/ipd-patients",
        element: <DoctorAdmissionsLayout />,
        children: [
            {
                index: "true",
                element: <ActiveIpdPatients />
            },
            {
                path: "/ipd-patients/doc-past-ipd",
                element: <DoctorPastAdmissions />
            }
        ]
    },
    {
        path: "/past-consultations",
        element: <PastConsultations />,
        children: [
            {
                index: true,
                element: <OpdConsultations />
            },
            {
                path: "/past-consultations/ipd-consultations",
                element: <IpdConsultations />
            }
        ]
    },
    {
        path: "/admin-patients",
        element: <AdminPatients />
    },
    {
        path: "/register-patient",
        element: <RegisterPatientModal />
    },
    {
        path: "/admit-patient-modal",
        element: <AdmitPatientModal />
    },
    {
        path: "/admin-doctors",
        element: <AdminDoctors />
    },
    {
        path: "/admin-admissions",
        element: <AdminAdmissions />,
        children: [
            {
                index: "true",
                element: <ActiveAdmissions />
            },
            {
                path: "/admin-admissions/past-ipd",
                element: <PastAdmissions />
            }
        ]
    },
    {
        path: "/admin-doctor-schedule",
        element: <ManageDoctorSchedule />
    },
    {
        path: "/admin-appointments",
        element: <AdminAppointments />
    }


])

createRoot(document.getElementById('root')).render(
    <Provider store={store}>
        <RouterProvider router={routes}>
            <App />
        </RouterProvider>
    </Provider>

)
