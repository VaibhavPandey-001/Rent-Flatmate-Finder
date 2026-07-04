import { useEffect, useState } from "react";
import api from "../api/axios";

export default function MyListings() {

    const [listings, setListings] = useState([]);

    useEffect(() => {
        loadListings();
    }, []);

    async function loadListings() {
        try {
            const res = await api.get("/listings/me");
            setListings(res.data);
        } catch (err) {
            console.log(err);
            alert("Failed to load listings");
        }
    }

    async function markFilled(id) {
        try {
            await api.patch(`/listings/${id}/filled`);
            alert("Listing marked as filled");
            loadListings();
        } catch (err) {
            console.log(err);
            alert("Failed to update listing");
        }
    }

    return (
        <div style={{ width: "900px", margin: "20px auto" }}>

            <h2>My Listings</h2>

            {listings.length === 0 ? (
                <p>No listings found.</p>
            ) : (
                listings.map(listing => (

                    <div
                        key={listing.id}
                        style={{
                            border: "1px solid gray",
                            padding: "15px",
                            marginBottom: "15px"
                        }}
                    >

                        <h3>{listing.title}</h3>

                        <p>{listing.description}</p>

                        <p>
                            <b>Location:</b> {listing.location}
                        </p>

                        <p>
                            <b>Rent:</b> ₹ {listing.rent}
                        </p>

                        <p>
                            <b>Available:</b> {listing.availableFrom}
                        </p>

                        <p>
                            <b>Room:</b> {listing.roomType}
                        </p>

                        <p>
                            <b>Furnishing:</b> {listing.furnishing}
                        </p>

                        <p>
                            <b>Status:</b> {listing.status}
                        </p>

                        {listing.status === "AVAILABLE" && (
                            <button onClick={() => markFilled(listing.id)}>
                                Mark Filled
                            </button>
                        )}

                    </div>

                ))
            )}

        </div>
    );
}