import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import { UserList } from './components/user-list.jsx'
import { AddUser } from './components/add-user.jsx'
import { PaginationList } from './components/pagination-list.jsx'

const routes = createBrowserRouter([
  {
    path: "/",
    element: <App />
  },
  {
    path: "/user-list",
    element: <UserList/>
  },
  {
    path: "/add-user",
    element: <AddUser/>
  },
  {
    path: "/pagination-list",
    element: <PaginationList/>
  }
])

createRoot(document.getElementById('root')).render(
  <RouterProvider router={routes}>
    <App />
  </RouterProvider>

)
