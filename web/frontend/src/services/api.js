import axios from "axios";

// Zentrale Instanz erstellen
const api = axios.create({
  baseURL: "/api", // Basis-Pfad für alle Anfragen
  withCredentials: true, // Sendet Cookies (JWT) automatisch mit
  headers: {
    "Content-Type": "application/json",
  },
});

// Response Interceptor: Fängt globale Fehler ab (z.B. Session abgelaufen)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Wenn 401 (Unauthorized) oder 403 (Forbidden) zurückkommt
    if (error.response && (error.response.status === 401 || error.response.status === 403)) {
      console.warn("Sitzung abgelaufen oder Zugriff verweigert (Auto-Logout via Interceptor möglich)");
      // Optional: Du könntest hier ein globales Event feuern oder redirecten
      // window.location.href = "/auth"; 
    }
    return Promise.reject(error);
  }
);

export default api;