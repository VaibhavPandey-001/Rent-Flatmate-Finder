import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

let stompClient = null;

export function connectWebSocket(userId, onMessage) {

    stompClient = new Client({
        webSocketFactory: () => new SockJS("http://localhost:8081/chat"),
        reconnectDelay: 5000,

        onConnect: () => {

            stompClient.subscribe(`/topic/owner/${userId}`, message => {
                onMessage(message.body);
            });

            stompClient.subscribe(`/topic/tenant/${userId}`, message => {
                onMessage(message.body);
            });

        }
    });

    stompClient.activate();
}

export function disconnectWebSocket() {
    if (stompClient) {
        stompClient.deactivate();
    }
}