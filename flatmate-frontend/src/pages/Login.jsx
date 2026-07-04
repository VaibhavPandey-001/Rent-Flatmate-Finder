import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { login } from "../services/authService";
import { useAuth } from "../context/AuthProvider";

export default function Login() {

    const navigate = useNavigate();

    const { loginUser } = useAuth();

    const [form, setForm] = useState({
        email: "",
        password: ""
    });

    async function handleSubmit(e) {

        e.preventDefault();

        try {

            const response = await login(form);

            loginUser(response.data);

            if (response.data.role === "OWNER") {
                navigate("/owner");
            } else if (response.data.role === "TENANT") {
                navigate("/tenant");
            } else {
                navigate("/admin");
            }

        } catch {

            alert("Invalid email or password");

        }

    }

    return (
        <div>

            <h2>Login</h2>

            <form onSubmit={handleSubmit}>

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

                <button type="submit">
                    Login
                </button>

            </form>

            <br />

            <Link to="/register">
                Register
            </Link>

        </div>
    );

}