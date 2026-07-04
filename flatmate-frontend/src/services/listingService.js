import api from "../api/axios";

export const createListing = data =>
    api.post("/listings", data);

export const getMyListings = () =>
    api.get("/listings/me");

export const markFilled = id =>
    api.patch(`/listings/${id}/filled`);