import { useEffect, useState } from "react";
import api from "../api/axios";
import { sendInterest } from "../services/interestService";
import { useAuth } from "../context/AuthProvider";

export default function TenantDashboard() {

    const [matches, setMatches] = useState([]);
    const { user } = useAuth();

    useEffect(() => {
        loadMatches();
    }, []);

    async function loadMatches() {
        try {
            const res = await api.get("/matches");
            setMatches(res.data);
        } catch (err) {
            console.log(err);
            alert("Failed to load matches");
        }
    }

    async function handleInterest(listingId) {
        try {
            await sendInterest(listingId);
            alert("Interest sent");
        } catch {
            alert("Failed to send interest");
        }
    }

    return (
        <div>
            {!user ? (
                <p>Loading dashboard...</p>
            ) : (
                <>
                    <h2>Tenant Dashboard (AI Matches)</h2>

                    {matches.length === 0 ? (
                        <p>No matches found</p>
                    ) : (
                        matches.map(m => (
                            <div
                                key={m.listingId}
                                style={{
                                    border: "1px solid gray",
                                    margin: "10px",
                                    padding: "10px"
                                }}
                            >
                                <h3>{m.title}</h3>
                                <p>{m.location}</p>
                                <p>₹ {m.rent}</p>

                                <h4>
                                    Match Score: {m.score}
                                </h4>

                                <p>{m.explanation}</p>

                                <button onClick={() => handleInterest(m.listingId)}>
                                    I'm Interested
                                </button>
                            </div>
                        ))
                    )}
                </>
            )}
        </div>
    );
}