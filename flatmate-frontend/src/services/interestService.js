import api from "../api/axios";

export const sendInterest = (listingId) =>
    api.post(`/interests/${listingId}`);

export const getOwnerInterests = () =>
    api.get("/interests/owner");

export const acceptInterest = (id) =>
    api.patch(`/interests/${id}/accept`);

export const declineInterest = (id) =>
    api.patch(`/interests/${id}/decline`);