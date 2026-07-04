import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

export default function CreateListing() {

    const navigate = useNavigate();

    const [form, setForm] = useState({
        title: "",
        description: "",
        location: "",
        rent: "",
        availableFrom: "",
        roomType: "SINGLE",
        furnishing: "FURNISHED"
    });

    function handleChange(e) {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    }

    async function handleSubmit(e) {

        e.preventDefault();

        try {

            await api.post("/listings", {
                ...form,
                rent: Number(form.rent),
                photos: []
            });

            alert("Listing created successfully");

            navigate("/owner/my-listings");

        } catch (err) {

            console.log(err);

            alert("Failed to create listing");

        }

    }

    return (

        <div
            style={{
                width: "600px",
                margin: "30px auto"
            }}
        >

            <h2>Create Listing</h2>

            <form onSubmit={handleSubmit}>

                <input
                    name="title"
                    placeholder="Title"
                    value={form.title}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <textarea
                    name="description"
                    placeholder="Description"
                    value={form.description}
                    onChange={handleChange}
                    rows={4}
                    required
                />

                <br /><br />

                <input
                    name="location"
                    placeholder="Location"
                    value={form.location}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <input
                    type="number"
                    name="rent"
                    placeholder="Rent"
                    value={form.rent}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <label>Available From</label>

                <br />

                <input
                    type="date"
                    name="availableFrom"
                    value={form.availableFrom}
                    onChange={handleChange}
                    required
                />

                <br /><br />

                <label>Room Type</label>

                <br />

                <select
                    name="roomType"
                    value={form.roomType}
                    onChange={handleChange}
                >
                    <option value="SINGLE">Single</option>
                    <option value="SHARED">Shared</option>
                    <option value="STUDIO">Studio</option>
                </select>

                <br /><br />

                <label>Furnishing</label>

                <br />

                <select
                    name="furnishing"
                    value={form.furnishing}
                    onChange={handleChange}
                >
                    <option value="FURNISHED">Furnished</option>
                    <option value="SEMI_FURNISHED">Semi Furnished</option>
                    <option value="UNFURNISHED">Unfurnished</option>
                </select>

                <br /><br />

                <button type="submit">

                    Create Listing

                </button>

            </form>

        </div>

    );

}