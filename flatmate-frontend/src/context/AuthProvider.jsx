import { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {

  const [user, setUser] = useState(() => {
      try {
          return JSON.parse(localStorage.getItem("user")) || null;
      } catch {
          return null;
      }
  });

    function loginUser(data) {

        localStorage.setItem("token", data.token);
        localStorage.setItem("user", JSON.stringify(data));

        setUser(data);

    }

    function logout() {

        localStorage.removeItem("token");
        localStorage.removeItem("user");

        setUser(null);

    }

    return (
        <AuthContext.Provider
            value={{
                user,
                loginUser,
                logout
            }}
        >
            {children}
        </AuthContext.Provider>
    );

}

export function useAuth() {

    return useContext(AuthContext);

}