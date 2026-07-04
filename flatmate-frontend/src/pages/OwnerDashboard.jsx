import { useEffect, useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";


export default function OwnerDashboard() {

    const [requests, setRequests] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        loadRequests();
    }, []);

    async function loadRequests() {
        try {
            const res = await api.get("/interests/owner");
            setRequests(res.data);
        } catch (err) {
            console.log(err);
            alert("Failed to load requests");
        }
    }

    async function accept(id) {
        try {
            await api.patch(`/interests/${id}/accept`);
            loadRequests();
        } catch {
            alert("Failed to accept");
        }
    }

    async function decline(id) {
        try {
            await api.patch(`/interests/${id}/decline`);
            loadRequests();
        } catch {
            alert("Failed to decline");
        }
    }

    return (
        <div>
            <h2>Owner Dashboard - Interest Requests</h2>
            <button onClick={() => navigate("/owner/create-listing")}>
                        Create Listing
                    </button>

                    <button
                        style={{ marginLeft: "10px" }}
                        onClick={() => navigate("/owner/my-listings")}
                    >
                        My Listings
                    </button>

            {requests.map(r => (
                <div
                    key={r.id}
                    style={{
                        border: "1px solid gray",
                        margin: "10px",
                        padding: "10px"
                    }}
                >
                    <h3>{r.listingTitle}</h3>

                    <p><b>Tenant:</b> {r.tenantName}</p>
                    <p><b>Email:</b> {r.tenantEmail}</p>

                    <p>
                        <b>Status:</b> {r.status}
                    </p>

                    <button onClick={() => accept(r.id)}>
                        Accept

                    </button>

                    <button onClick={() => decline(r.id)}>
                        Decline
                    </button>
                    {r.status === "ACCEPTED" && (
                        <button onClick={() => navigate(`/chat/${r.id}`)}>
                            Open Chat
                        </button>
                    )}
                </div>
            ))}
        </div>
    );
}