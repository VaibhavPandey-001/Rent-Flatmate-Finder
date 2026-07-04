import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { register } from "../services/authService";

export default function Register() {

    const navigate = useNavigate();

    const [form, setForm] = useState({
        name: "",
        email: "",
        password: "",
        role: "TENANT"
    });

    async function handleSubmit(e) {

        e.preventDefault();

        try {

            await register(form);

            alert("Registration successful");

            navigate("/login");

        } catch {

            alert("Registration failed");

        }

    }

    return (
        <div>

            <h2>Register</h2>

            <form onSubmit={handleSubmit}>

                <input
                    placeholder="Name"
                    value={form.name}
                    onChange={e =>
                        setForm({
                            ...form,
                            name: e.target.value
                        })
                    }
                />

                <br /><br />

                <input
                    placeholder="Email"
                    value={form.email}
                    onChange={e =>
                        setForm({
                            ...form,
                            email: e.target.value
                        })
                    }
                />

                <br /><br />

                <input
                    type="password"
                    placeholder="Password"
                    value={form.password}
                    onChange={e =>
                        setForm({
                            ...form,
                            password: e.target.value
                        })
                    }
                />

                <br /><br />

                <select
                    value={form.role}
                    onChange={e =>
                        setForm({
                            ...form,
                            role: e.target.value
                        })
                    }
                >
                    <option value="TENANT">Tenant</option>
                    <option value="OWNER">Owner</option>
                </select>

                <br /><br />

                <button type="submit">
                    Register
                </button>

            </form>

            <br />

            <Link to="/login">
                Login
            </Link>

        </div>
    );

}