// export default function App() {
//     return <h1>APP IS WORKING</h1>;
// }

import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import OwnerDashboard from "./pages/OwnerDashboard";
import TenantDashboard from "./pages/TenantDashboard";
import AdminDashboard from "./pages/AdminDashboard";
import PrivateRoute from "./components/PrivateRoute";
import ChatPage from "./pages/ChatPage";
import CreateListing from "./pages/CreateListing";
import MyListings from "./pages/MyListings";

export default function App() {

    return (
        <Routes>

            <Route path="/" element={<Navigate to="/login" />} />

            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />

            <Route
                path="/owner"
                element={
                    <PrivateRoute>
                        <OwnerDashboard />
                    </PrivateRoute>
                }
            />

            <Route
                path="/tenant"
                element={
                    <PrivateRoute>
                        <TenantDashboard />
                    </PrivateRoute>
                }
            />

            <Route
                path="/admin"
                element={
                    <PrivateRoute>
                        <AdminDashboard />
                    </PrivateRoute>
                }
            />
            <Route
                path="/chat/:interestId"
                element={
                    <PrivateRoute>
                        <ChatPage />
                    </PrivateRoute>
                }
            />
            <Route
                path="/owner/create-listing"
                element={
                    <PrivateRoute>
                        <CreateListing />
                    </PrivateRoute>
                }
            />

            <Route
                path="/owner/my-listings"
                element={
                    <PrivateRoute>
                        <MyListings />
                    </PrivateRoute>
                }
            />

        </Routes>
    );
}