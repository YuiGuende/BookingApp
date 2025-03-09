import { useState, useEffect} from "react";
import "./ReceptionStyles.css";

export default function RoomStatus() {
    const [activeTab, setActiveTab] = useState("today-checkin");
    return (
        <>
            <div className="mini-tab">
                <button className={activeTab === "today-checkin" ? "active" : ""} onClick={() => setActiveTab("today-checkin")}>Today Check-in</button>
                <button className={activeTab === "occupied" ? "active" : ""} onClick={() => setActiveTab("occupied")}>Occupied</button>
                <button className={activeTab === "available" ? "active" : ""} onClick={() => setActiveTab("available")}>Available</button>
            </div>
        </>
    );
}