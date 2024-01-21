import Root from './routes/root.jsx';
import Dashboard from './routes/dashboard.jsx'
import Tickets from './routes/tickets.jsx'
import Administration from './routes/administration.jsx'
import Login from './routes/login.jsx';
import Register from './routes/register.jsx';
import { createBrowserRouter } from 'react-router-dom';

const ProtectedRoute = ({ children }) => {
  const isAuthenticated = localStorage.getItem('jwtToken');
  return isAuthenticated ? children : <Navigate to="/login" />;
};


const router = createBrowserRouter([
  {
    path: "/",
    element: <ProtectedRoute> <Root /> </ProtectedRoute>,
    children: [
      {
        path: "/",
        element: <Dashboard /> 
      },
      {
        path: "tickets",
        element: <Tickets />
      },
      {
        path: "administration",
        element: <Administration />
      },
    ]
  },
  {
    path: '/login',
    element: <Login />,
  },
  {
    path: 'register',
    element: <Register />
  }
])

export default router;