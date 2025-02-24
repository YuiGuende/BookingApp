import { ReduxProvider } from "../store/ReduxProvider";
import "../assets/index.css";

export const metadata = {
  title: "BookingApp",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <ReduxProvider>{children}</ReduxProvider>
      </body>
    </html>
  );
}
