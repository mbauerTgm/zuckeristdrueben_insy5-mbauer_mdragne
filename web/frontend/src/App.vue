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
        <button @click="toggleDarkMode" class="header-btn" :title="darkMode ? 'Lichtmode' : 'Darkmode'">
          <svg v-if="darkMode" viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
            <path d="M12,7A5,5 0 0,1 17,12A5,5 0 0,1 12,17A5,5 0 0,1 7,12A5,5 0 0,1 12,7M12,9A3,3 0 0,0 9,12A3,3 0 0,0 12,15A3,3 0 0,0 15,12A3,3 0 0,0 12,9M12,2L14.39,4.39L12,6.78L9.61,4.39L12,2M5.61,5.61L8,8L5.61,10.39L3.22,8L5.61,5.61M2,12L4.39,9.61L6.78,12L4.39,14.39L2,12M5.61,18.39L8,16L10.39,18.39L8,20.78L5.61,18.39M12,22L9.61,19.61L12,17.22L14.39,19.61L12,22M18.39,18.39L16,16L18.39,13.61L20.78,16L18.39,18.39M22,12L19.61,14.39L17.22,12L19.61,9.61L22,12M18.39,5.61L16,8L13.61,5.61L16,3.22L18.39,5.61Z" />
          </svg>
          <svg v-else viewBox="0 0 24 24" width="20" height="20" fill="currentColor">
            <path d="M17.75,4.09L15.22,6.03L16.13,9.09L13.5,7.28L10.87,9.09L11.78,6.03L9.25,4.09L12.44,4L13.5,1L14.56,4L17.75,4.09M21.25,11L19.61,12.25L20.2,14.23L18.5,13.06L16.8,14.23L17.39,12.25L15.75,11L17.81,10.95L18.5,9L19.19,10.95L21.25,11M18.97,15.95C19.8,15.87 20.69,15.89 21.44,16.05C19.38,18.2 16.3,19.5 13,19.5C6.92,19.5 2,14.58 2,8.5C2,5.2 3.3,2.12 5.45,0.06C5.61,0.81 5.63,1.7 5.55,2.53C5.55,7.58 9.63,11.66 14.68,11.66C15.8,11.66 16.89,11.46 17.9,11.09C18.27,13.11 18.63,14.79 18.97,15.95Z" />
          </svg>
        </button>

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
    />
    <Report_Component 
      v-else-if="currentView === 'report'" 
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
      darkMode: localStorage.getItem('darkMode') === 'true'
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
    }
  },
  mounted() {
    // Initialer Dark Mode Check
    this.applyDarkMode();

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
  }
}
</script>

<style>
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

#app.with-header {
  position: absolute;
  top: 60px;
  bottom: 0;
  left: 0;
  right: 0;
  overflow-y: auto;
  margin-top: 0;
  padding-top: 0;
}

@media (max-width: 600px) {
  .brand-name { display: none; }
  .main-nav a { padding: 8px 10px; font-size: 0.85rem; }
}
</style>