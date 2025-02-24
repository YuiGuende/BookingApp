"use client";
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

// Thunk để fetch danh sách khách sạn
export const fetchHotels = createAsyncThunk(
  "hotel/fetchHotels",
  async (searchParams, { rejectWithValue }) => {
    try {
      // Tạo requestBody dựa trên searchParams
      const requestBody = {
        fullAddress: searchParams.fullAddress,
        checkInDate: searchParams.checkInDate ? searchParams.checkInDate : null,
        checkOutDate: searchParams.checkOutDate ? searchParams.checkOutDate : null,
        adultQuantity: searchParams.adultQuantity,
        childrenQuantity: searchParams.childrenQuantity,
        roomQuantity: searchParams.roomQuantity
      }
      console.log(requestBody);

      const response = await fetch("http://localhost:8080/api/customer/hotel/search", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(requestBody),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();

      if (data.status === "success") {
        return data.data; // Trả về mảng hotels
      } else {
        throw new Error(data.message || "An error occurred while fetching hotels");
      }
    } catch (error) {
      return rejectWithValue(error.message);
    }
  }
);

const hotelSlice = createSlice({
  name: "hotel",
  initialState: {
    hotels: [],
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchHotels.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchHotels.fulfilled, (state, action) => {
        state.loading = false;
        state.hotels = action.payload;
      })
      .addCase(fetchHotels.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload || "Something went wrong";
      });
  },
});

export default hotelSlice.reducer;
