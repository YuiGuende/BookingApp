function Capacity() {
    return(
                <div className="adult-controls">
                    <button type="button" onClick={() => handleCapacityChange(-1)} className="adult-button">
                    -
                    </button>
                    <input
                        type="number"
                        id="adult"
                        value={searchParams.capacity}
                        onChange={(e) => handleChange("adult", Number.parseInt(e.target.value) || 1)}
                        className="adult-value"
                        min="1"
                    />
                    <button type="button" onClick={() => handleCapacityChange(1)} className="adult-button">
                    +
                    </button>
                </div>
    );
}
export default Capacity;