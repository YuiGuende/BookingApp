import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux"; // Import Provider từ react-redux
import { store } from "./store/store.js";
import "./assets/index.css";

import Page from "./pages/Page.jsx";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <Provider store={store}> {/* Bọc toàn bộ ứng dụng với Provider */}
      <BrowserRouter>
        <Page />
      </BrowserRouter>
    </Provider>
  </StrictMode>
);
