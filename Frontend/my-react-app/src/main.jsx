import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import "../assets/index.css";

import Page from './pages/Page.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Page />
  </StrictMode>,
)
