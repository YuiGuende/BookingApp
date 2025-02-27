import React from "react";

const AmenityDetails = () => {
    return (
        <aside className="amenities-sidebar">
            <h2>Tiện ích</h2>
            <ul>
                <li><input type="checkbox" /> WiFi miễn phí</li>
                <li><input type="checkbox" /> Hồ bơi</li>
                <li><input type="checkbox" /> Bãi đỗ xe</li>
                <li><input type="checkbox" /> Dịch vụ phòng</li>
            </ul>
        </aside>
    );
};

export default AmenityDetails;