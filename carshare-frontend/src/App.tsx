import {
  BrowserRouter,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";

import UserDashboard from "./components/UserDashboard/user-dashboard";
import Home from "./components/Home/home";
import Navbar from "./components/Navbar/navbar";
import VehicleDashboard from "./components/VehicleDashboard/vehicle-dashboard";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/vehicle-dashboard" element={<VehicleDashboard />} />
        <Route path="/user-dashboard" element={<UserDashboard />} />
        <Route path="*" element={<Navigate to="/" replace />} /> 
      </Routes>
    </BrowserRouter>
  );
}

export default App;
