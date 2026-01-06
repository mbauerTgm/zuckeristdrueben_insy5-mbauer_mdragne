# Prompt_Backend_Filter_Pagination
## Gemini Pro 3

Wie schwer wäre es bzw. was müsste ich ändern, um Axios klassen zuverwenden?

Es ist nicht schwer, auf Axios umzusteigen, besonders da du axios in Login_Component.vue und App.vue bereits verwendest. Der Aufwand ist eher eine Fleißarbeit (Suchen & Ersetzen), bietet aber große Vorteile:

Weniger Code: Du musst params nicht mehr manuell zusammenbauen (params.append(...)).

Kein manuelles response.json(): Axios macht das automatisch.

Bessere Fehlerbehandlung: Axios wirft bei 4xx/5xx Fehlern automatisch eine Exception (bei fetch musst du response.ok prüfen).

Interceptors: Du kannst den 401-Logout-Check an einer zentralen Stelle lösen, statt in jedem Fetch-Aufruf.

Hier ist der Schritt-für-Schritt-Plan, um "Axios Klassen" (Service-Pattern) einzuführen.

Schritt 1: Zentrale Axios-Instanz erstellen (Best Practice)
Erstelle eine neue Datei, z. B. src/services/api.js. Das ist deine "Basis-Klasse" für alle HTTP-Anfragen. Hier konfigurieren wir die URL und Cookies global.

Datei: src/services/api.js

JavaScript
import axios from "axios";

// Instanz erstellen
const api = axios.create({
  baseURL: "/api", // Basis-URL, spart Tipparbeit
  withCredentials: true, // Sendet Cookies automatisch mit
  headers: {
    "Content-Type": "application/json",
  },
});

// Interceptor: Fängt JEDEN Fehler ab (z.B. 401 Unauthorized)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && (error.response.status === 401 || error.response.status === 403)) {
      // Optional: Hier könntest du direkt ein Event feuern oder localStorage leeren
      // window.location.href = "/auth"; // Harter Redirect falls gewünscht
      console.warn("Sitzung abgelaufen (Interceptor)");
    }
    return Promise.reject(error);
  }
);

export default api;
Schritt 2: Refactoring von Frontend.vue
Hier sind die konkreten Änderungen, die du machen müsstest.

A. Import ändern
JavaScript
// ALT
// const API_BASE_URL = '/api'; (wird gelöscht)

// NEU
import api from "@/services/api"; // Importiere deine neue Instanz
B. fetchTableData umbauen (GET mit Params)
Axios nimmt ein Objekt für Query-Parameter entgegen. Du musst URLSearchParams nicht mehr manuell füllen!

Vorher (fetch):

JavaScript
const params = new URLSearchParams();
params.append('page', this.currentPage);
// ... mühsames if/append ...
const response = await fetch(`${API_BASE_URL}/...?${params.toString()}`, ...);
const data = await response.json();
Nachher (axios):

JavaScript
// Baue einfach ein normales JavaScript Objekt
const queryParams = {
  page: this.currentPage,
  size: this.displayLimit,
};

// Deine Logik zum Füllen des Objekts
if (this.selectedTable === 'analysis') {
  if (this.searchParams.idFrom) queryParams['aId.from'] = this.searchParams.idFrom;
  // ... usw ...
}

try {
  // Axios kümmert sich um ?key=value&...
  const response = await api.get(`/${this.currentSchema.endpoint}/filter`, { 
    params: queryParams 
  });

  // Daten sind direkt in response.data
  this.tableData = response.data.content;
  this.totalPages = response.data.totalPages;
  // ...
} catch (error) {
  if (error.response && error.response.status === 401) {
     this.$emit('logout');
     return;
  }
  this.error = error;
}
C. saveItem umbauen (POST/PUT)
Axios stringified das JSON automatisch.

Vorher (fetch):

JavaScript
await fetch(url, {
  method: method, // 'POST' oder 'PUT'
  body: JSON.stringify(dataToSend),
  // headers...
});
if (!response.ok) throw ...
Nachher (axios):

JavaScript
// method ist hier entweder 'post' oder 'put' (kleingeschrieben)
// api[method] ruft dynamisch api.post(...) oder api.put(...) auf
await api[method.toLowerCase()](url, dataToSend);

// Kein response.ok Check nötig, catch block fängt Fehler
D. exportFilteredCSV umbauen (Blob Download)
Das ist wichtig: Du musst Axios sagen, dass die Antwort ein Blob ist.

Vorher (fetch):

JavaScript
const response = await fetch(url, ...);
const blob = await response.blob();
Nachher (axios):

JavaScript
const response = await api.get(`/${this.currentSchema.endpoint}/export`, {
  params: queryParams,
  responseType: 'blob' // WICHTIG!
});

const blob = new Blob([response.data]); // Daten in Blob verpacken
// ... Rest ist gleich (Link erstellen, klicken) ...
Schritt 3: Refactoring von Report_Component.vue
Auch hier wird es viel sauberer.

Vorher:

JavaScript
let url = `/api/reports/${this.selectedReport}`;
// ... String concatenation für params ...
const response = await fetch(url + queryParams);
const data = await response.json();
Nachher:

JavaScript
import api from "@/services/api";

// ...

const params = {
  page: this.currentPage,
  size: this.displayLimit
};

if (this.selectedReport === 'samples-suspicious-timerange') {
   // ... Validierung ...
   params.start = startFmt;
   params.end = endFmt;
}

const response = await api.get(`/reports/${this.selectedReport}`, { params });
this.reportData = response.data.content;
Zusammenfassung
Du musst keine komplexe Logik ändern, sondern nur die "Transport-Schicht" austauschen.

Erstelle api.js (einmalig).

Ersetze in Frontend.vue:

fetch(url, options) → api.get/post(url, options)

params.append(...) → einfaches Objekt { key: value }

await response.json() → response.data

if (!response.ok) → (fällt weg, Fehler landen im catch)

Das macht deinen Code lesbarer und robuster. Möchtest du, dass ich das für die Frontend.vue komplett umschreibe?

Ja bitte schreibe das Frontend vollständig auf Axios Klassen um

Sehr gerne! Hier ist der vollständige Umbau auf das Axios Service Pattern.

Wir erstellen zuerst die zentrale Konfiguration und passen dann die beiden Komponenten an.

1. Neue Datei: src/services/api.js
Erstelle diese Datei. Sie ist dein zentraler Hub für alle Backend-Anfragen.

JavaScript
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
2. Update: src/components/Frontend.vue
Hier wurde fetch komplett durch api ersetzt. Der Code ist dadurch deutlich kürzer geworden, da das manuelle Parameter-Zusammenbauen (URLSearchParams) und das Fehler-Handling (response.ok) wegfallen.

3. Update: src/components/Report_Component.vue
Auch hier wird api.get verwendet. Das credentials: 'include' ist bei Axios standardmäßig durch withCredentials: true im Interceptor abgedeckt.

Ich bin mir nicht sicher ob du die neueste Version der Report Component hattest, also mache es bitte für diese

Hier ist die Finalisierung deiner App.

Ich habe das Menü in App.vue repariert und fertiggestellt (der Copy-Paste-Fehler wurde entfernt). Es enthält jetzt die globalen Zeiteinstellungen und den Dark-Mode. Diese Werte werden gespeichert und sauber an Frontend.vue und die neue Report_Component.vue übergeben.

Hier sind die 3 überarbeiteten Dateien:

1. App.vue
Das Herzstück: Hier wird das Menü verwaltet und die Daten (appliedStart, appliedEnd) an die Kinder-Komponenten weitergereicht.

2. Frontend.vue
Hier werden die globalen Props empfangen, überwacht und direkt in die searchParams geschrieben, damit der fetchTableData sie nutzt.

3. Report_Component.vue
Auch hier werden die Props empfangen.