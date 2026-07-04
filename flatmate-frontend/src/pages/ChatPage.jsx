import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/axios";

export default function ChatPage() {

    const { interestId } = useParams();

    const [messages, setMessages] = useState([]);
    const [message, setMessage] = useState("");

    useEffect(() => {

        loadHistory();

        const interval = setInterval(loadHistory, 2000);

        return () => clearInterval(interval);

    }, []);

    async function loadHistory() {
        try {
            const res = await api.get(`/chat/${interestId}`);
            setMessages(res.data);
        } catch (err) {
            console.log(err);
            alert("Failed to load chat");
        }
    }

    async function sendMessage() {

        if (!message.trim()) return;

        try {

            const res = await api.post("/chat", {
                interestId,
                message
            });

            setMessages(prev => [...prev, res.data]);
            setMessage("");

        } catch (err) {
            console.log(err);
            alert("Failed to send message");
        }

    }

    return (

        <div
            style={{
                maxWidth: "700px",
                margin: "20px auto"
            }}
        >

            <h2>Chat</h2>

            <div
                style={{
                    border: "1px solid gray",
                    height: "400px",
                    overflowY: "auto",
                    padding: "10px"
                }}
            >

                {messages.map(m => (

                    <div
                        key={m.id}
                        style={{
                            marginBottom: "12px"
                        }}
                    >
                        <strong>{m.sender}</strong>

                        <br />

                        {m.message}

                        <br />

                        <small>{m.sentAt}</small>

                    </div>

                ))}

            </div>

            <div
                style={{
                    display: "flex",
                    marginTop: "15px"
                }}
            >

                <input
                    style={{
                        flex: 1,
                        padding: "10px"
                    }}
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    placeholder="Type message..."
                />

                <button
                    onClick={sendMessage}
                    style={{
                        marginLeft: "10px"
                    }}
                >
                    Send
                </button>

            </div>

        </div>

    );

}