<template>
  <header v-if="currentView !== 'login'" class="app-header">
    <div class="header-content">
      
      <div class="brand">
        <div class="logo-circle-small">
          <svg viewBox="0 0 24 24" width="24" height="24" fill="white">
            <path d="M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M12,4A8,8 0 0,1 20,12A8,8 0 0,1 12,20A8,8 0 0,1 4,12A8,8 0 0,1 12,4M12,6A6,6 0 0,0 6,12A6,6 0 0,0 12,18A6,6 0 0,0 18,12A6,6 0 0,0 12,6M12,8A4,4 0 0,1 16,12A4,4 0 0,1 12,16A4,4 0 0,1 8,12A4,4 0 0,1 12,8Z" />
          </svg>
        </div>
        <span class="brand-name">ZuckerIstDrueben</span>
      </div>

      <nav class="main-nav">
        <a 
          href="#" 
          @click.prevent="currentView = 'table'" 
          :class="{ active: currentView === 'table' }"
        >
          Frontend
        </a>
        <a 
          href="#" 
          @click.prevent="currentView = 'report'" 
          :class="{ active: currentView === 'report' }"
        >
          Report
        </a>
      </nav>

      <div class="header-right">
        <div class="column-selector">
          <button 
            type="button" 
            class="header-btn" 
            @click="toggleSettings"
            title="Einstellungen & Filter"
          >
            <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
              <path d="M12,15.5A3.5,3.5 0 0,1 8.5,12A3.5,3.5 0 0,1 12,8.5A3.5,3.5 0 0,1 15.5,12A3.5,3.5 0 0,1 12,15.5M19.43,12.97C19.47,12.65 19.5,12.33 19.5,12C19.5,11.67 19.47,11.34 19.43,11L21.54,9.37C21.73,9.22 21.78,8.95 21.66,8.73L19.66,5.27C19.54,5.05 19.27,4.96 19.05,5.05L16.56,6.05C16.04,5.66 15.5,5.32 14.87,5.07L14.5,2.42C14.46,2.18 14.25,2 14,2H10C9.75,2 9.54,2.18 9.5,2.42L9.13,5.07C8.5,5.32 7.96,5.66 7.44,6.05L4.95,5.05C4.73,4.96 4.46,5.05 4.34,5.27L2.34,8.73C2.21,8.95 2.27,9.22 2.46,9.37L4.57,11C4.53,11.34 4.5,11.67 4.5,12C4.5,12.33 4.53,12.65 4.57,12.97L2.46,14.63C2.27,14.78 2.21,15.05 2.34,15.27L4.34,18.73C4.46,18.95 4.73,19.03 4.95,18.95L7.44,17.94C7.96,18.34 8.5,18.68 9.13,18.93L9.5,21.58C9.54,21.82 9.75,22 10,22H14C14.25,22 14.46,21.82 14.5,21.58L14.87,18.93C15.5,18.67 16.04,18.34 16.56,17.94L19.05,18.95C19.27,19.03 19.54,18.95 19.66,18.73L21.66,15.27C21.78,15.05 21.73,14.78 21.54,14.63L19.43,12.97Z" />
            </svg>
          </button>
            
          <div v-if="showSettings" class="column-dropdown settings-dropdown">
            <div class="column-dropdown-header">
              <span>Einstellungen</span>
              <button @click="resetSettings" class="btn-link">Reset</button>
            </div>
            <div class="filter-content">
              
              <div class="filter-section">
                <label class="section-label">Globaler Zeitfilter</label>
                <div class="filter-group">
                    <label>Von</label>
                    <input type="datetime-local" step="1" v-model="settingsStart" class="full-width" />
                </div>
                <div class="filter-group">
                    <label>Bis</label>
                    <input type="datetime-local" step="1" v-model="settingsEnd" class="full-width" />
                </div>
                <button class="btn btn-save full-width" style="margin-top: 8px;" @click="applySettings">
                  Anwenden
                </button>
              </div>

              <hr class="separator">

              <div class="filter-section">
                <label class="section-label">Darstellung</label>
                <button @click="toggleDarkMode" class="btn btn-dark-toggle full-width">
                  <span v-if="darkMode">Light-Modus aktivieren</span>
                  <span v-else>Dark-Modus aktivieren</span>
                  <svg v-if="darkMode" viewBox="0 0 24 24" width="18" height="18" fill="currentColor"><path d="M12,7A5,5 0 0,1 17,12A5,5 0 0,1 12,17A5,5 0 0,1 7,12A5,5 0 0,1 12,7M12,9A3,3 0 0,0 9,12A3,3 0 0,0 12,15A3,3 0 0,0 15,12A3,3 0 0,0 12,9M12,2L14.39,4.39L12,6.78L9.61,4.39L12,2M5.61,5.61L8,8L5.61,10.39L3.22,8L5.61,5.61M2,12L4.39,9.61L6.78,12L4.39,14.39L2,12M5.61,18.39L8,16L10.39,18.39L8,20.78L5.61,18.39M12,22L9.61,19.61L12,17.22L14.39,19.61L12,22M18.39,18.39L16,16L18.39,13.61L20.78,16L18.39,18.39M22,12L19.61,14.39L17.22,12L19.61,9.61L22,12M18.39,5.61L16,8L13.61,5.61L16,3.22L18.39,5.61Z" /></svg>
                  <svg v-else viewBox="0 0 24 24" width="18" height="18" fill="currentColor"><path d="M17.75,4.09L15.22,6.03L16.13,9.09L13.5,7.28L10.87,9.09L11.78,6.03L9.25,4.09L12.44,4L13.5,1L14.56,4L17.75,4.09M21.25,11L19.61,12.25L20.2,14.23L18.5,13.06L16.8,14.23L17.39,12.25L15.75,11L17.81,10.95L18.5,9L19.19,10.95L21.25,11M18.97,15.95C19.8,15.87 20.69,15.89 21.44,16.05C19.38,18.2 16.3,19.5 13,19.5C6.92,19.5 2,14.58 2,8.5C2,5.2 3.3,2.12 5.45,0.06C5.61,0.81 5.63,1.7 5.55,2.53C5.55,7.58 9.63,11.66 14.68,11.66C15.8,11.66 16.89,11.46 17.9,11.09C18.27,13.11 18.63,14.79 18.97,15.95Z" /></svg>
                </button>
              </div>

            </div>
          </div>
        </div>

        <button class="header-btn logout-btn" @click="onLogout" title="Abmelden">
          <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
            <path d="M17,17.25V14H10V10H17V6.75L22.25,12L17,17.25M13,2A2,2 0 0,1 15,4V8H13V4H4V20H13V16H15V20A2,2 0 0,1 13,22H4A2,2 0 0,1 2,20V4A2,2 0 0,1 4,2Z" />
          </svg>
        </button>
      </div>

    </div>
  </header>

  <div id="app" :class="{ 'with-header': currentView !== 'login' }">
    <LoginComponent 
      v-if="currentView === 'login'" 
      @login-success="onLoginSuccess" 
    />
    <Comp_Frontend 
      v-else-if="currentView === 'table'" 
      @logout="onLogout" 
      :global-date-start="appliedStart"
      :global-date-end="appliedEnd"
    />
    <Report_Component 
      v-else-if="currentView === 'report'" 
      :global-date-start="appliedStart"
      :global-date-end="appliedEnd"
    />
  </div>
</template>

<script>
import axios from "axios";
import LoginComponent from './components/Login_Component.vue'
import Comp_Frontend from './components/Frontend.vue'
import Report_Component from "./components/Report_Component.vue";

export default {
  name: 'App',
  components: {
    LoginComponent,
    Comp_Frontend,
    Report_Component: Report_Component,
  },
  data() {
    return {
      currentView: 'login',
      darkMode: localStorage.getItem('darkMode') === 'true',

      showSettings: false,
      settingsStart: '',
      settingsEnd: '',
      
      appliedStart: '',
      appliedEnd: ''
    }
  },
  methods: {
    getCookie(name) {
      const value = `; ${document.cookie}`;
      const parts = value.split(`; ${name}=`);
      if (parts.length === 2) return parts.pop().split(';').shift();
      return null;
    },

    onLoginSuccess() {
      this.currentView = 'table';
      window.history.pushState({}, '', '/');
    },

    async onLogout() {
      this.darkMode = false;
      localStorage.setItem('darkMode', this.darkMode);
      this.applyDarkMode();
      try {
        // Backend den HttpOnly Cookie löschen lassen
        await axios.post("/api/auth/logout", {}, { withCredentials: true });
      } catch (e) {
        console.log("Logout Warning:", e);
      }
      localStorage.clear(); 

      this.currentView = 'login';
      window.history.pushState({}, '', '/auth');
    },

    toggleDarkMode() {
      this.darkMode = !this.darkMode;
      localStorage.setItem('darkMode', this.darkMode);
      this.applyDarkMode();
    },

    applyDarkMode() {
      if (this.darkMode) {
        document.body.classList.add('dark-theme');
      } else {
        document.body.classList.remove('dark-theme');
      }
    },

    // --- Settings Menu Methods ---
    toggleSettings() {
      this.showSettings = !this.showSettings;
    },
    
    applySettings() {
      this.appliedStart = this.settingsStart;
      this.appliedEnd = this.settingsEnd;
      
      localStorage.setItem('globalStart', this.settingsStart);
      localStorage.setItem('globalEnd', this.settingsEnd);
      
      this.showSettings = false;
    },

    resetSettings() {
      this.settingsStart = '';
      this.settingsEnd = '';
      this.applySettings();
    },
    
    closeSelectors(event) {
      // Schließt Menü bei Klick außerhalb
      if (!event.target.closest('.column-selector')) {
        this.showSettings = false;
      }
    }
  },
  mounted() {
    // Initialer Dark Mode Check
    this.applyDarkMode();
    document.addEventListener('click', this.closeSelectors);

    // Gespeicherte Einstellungen laden
    const savedStart = localStorage.getItem('globalStart');
    const savedEnd = localStorage.getItem('globalEnd');
    if (savedStart) {
        this.settingsStart = savedStart;
        this.appliedStart = savedStart;
    }
    if (savedEnd) {
        this.settingsEnd = savedEnd;
        this.appliedEnd = savedEnd;
    }

    // Browser Zurück-Button Logik
    window.addEventListener('popstate', () => {
      // Check ob das JWT Cookie noch da ist
      const isLoggedIn = !!this.getCookie('jwt');
      if (!isLoggedIn) {
        this.currentView = 'login';
      } else if (this.currentView === 'login') {
        this.currentView = 'table';
      }
    });
    
    // Initialer Check beim Laden der Seite
    if (this.getCookie('jwt')) {
      this.currentView = 'table';
    } else {
      this.currentView = 'login';
      if (window.location.pathname !== '/auth') {
         window.history.replaceState({}, '', '/auth');
      }
    }
  },
  beforeUnmount() {
    document.removeEventListener('click', this.closeSelectors);
  },
}
</script>

<style>
/* Global Styles */
html, body { 
  margin: 0; 
  padding: 0;
  width: 100%;
  height: 100%;
  font-family: 'Inter', 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
  background-color: #f8f9fa;
  color: #333;
  overflow: hidden; 
}

body.dark-theme {
  background-color: #0f172a;
  color: #f8fafc;
}

/* Header Styles */
.app-header {
  background-color: #0f172a;
  color: white;
  padding: 0 20px;
  height: 60px;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
  box-shadow: 0 2px 10px rgba(0,0,0,0.2);
  box-sizing: border-box; 
}

.header-content {
  width: 100%; 
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between; 
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
  font-weight: 700;
  font-size: 1.2rem;
  letter-spacing: 0.5px;
  flex: 1; 
}

.logo-circle-small {
  width: 32px;
  height: 32px;
  background: rgba(255,255,255,0.1);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(5px);
}

.main-nav {
  display: flex;
  gap: 5px;
  background: rgba(255,255,255,0.05);
  padding: 4px;
  border-radius: 8px;
  justify-content: center; 
}

.main-nav a {
  color: #94a3b8;
  text-decoration: none;
  padding: 8px 16px;
  border-radius: 6px;
  font-size: 0.95rem;
  font-weight: 500;
  transition: all 0.2s ease;
  white-space: nowrap; 
}

.main-nav a:hover {
  color: white;
  background-color: rgba(255,255,255,0.1);
}

.main-nav a.active {
  background-color: #198754;
  color: white;
  box-shadow: 0 1px 3px rgba(0,0,0,0.2);
}

.header-right {
  flex: 1; 
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  align-items: center;
}

.header-btn {
  background: transparent;
  border: 1px solid rgba(255,255,255,0.2);
  color: #94a3b8;
  width: 36px; 
  height: 36px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
}

.header-btn:hover {
  background: rgba(255,255,255,0.1);
  color: white;
}

.logout-btn:hover {
  border-color: #ef4444;
  color: #ef4444;
  background: rgba(239, 68, 68, 0.1);
}

.column-selector {
  position: relative;
}

.settings-dropdown {
  position: absolute;
  top: 100%;
  right: 0; 
  left: auto;
  margin-top: 8px;
  background: white;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  min-width: 280px;
  z-index: 1001;
  color: #333;
}

.column-dropdown-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 12px;
  border-bottom: 1px solid #dee2e6;
  background: #f8f9fa;
  border-radius: 8px 8px 0 0;
  font-weight: 600;
  font-size: 0.9rem;
}

.btn-link {
  background: none; border: none; color: #007bff; cursor: pointer; font-size: 0.8rem;
}
.btn-link:hover { text-decoration: underline; }

.filter-content { padding: 15px; }
.filter-section { margin-bottom: 10px; }
.separator { margin: 15px 0; border: 0; border-top: 1px solid #e9ecef; }

.section-label {
  display: block;
  font-size: 0.8rem;
  text-transform: uppercase;
  color: #6c757d;
  font-weight: 700;
  margin-bottom: 8px;
}

.filter-group { margin-bottom: 10px; }
.filter-group label { display: block; font-size: 0.85rem; margin-bottom: 4px; color: #495057; }

.full-width { width: 100%; box-sizing: border-box; }

.btn-save {
  background: #198754; color: white; border: none; padding: 8px; border-radius: 4px; cursor: pointer; font-weight: 600;
}
.btn-save:hover { background: #157347; }

.btn-dark-toggle {
  background: #343a40; color: white; border: none; padding: 10px; border-radius: 4px; cursor: pointer;
  display: flex; justify-content: space-between; align-items: center;
}
.btn-dark-toggle:hover { background: #23272b; }

body.dark-theme .settings-dropdown { background: #1e293b; border-color: #334155; color: white; }
body.dark-theme .column-dropdown-header { background: #0f172a; border-color: #334155; color: white; }
body.dark-theme .separator { border-color: #334155; }
body.dark-theme .filter-group label { color: #cbd5e1; }
body.dark-theme input { background: #0f172a; border: 1px solid #334155; color: white; }
body.dark-theme .btn-dark-toggle { background: #475569; }
body.dark-theme .btn-dark-toggle:hover { background: #334155; }

#app.with-header {
  position: absolute;
  top: 60px;
  bottom: 0;
  left: 0;
  right: 0;
  overflow-y: auto;
}

@media (max-width: 600px) {
  .brand-name { display: none; }
  .main-nav a { padding: 8px 10px; font-size: 0.85rem; }
}
</style>