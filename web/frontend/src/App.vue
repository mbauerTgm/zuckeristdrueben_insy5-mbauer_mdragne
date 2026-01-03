<template>
  <div id="app">
    <LoginComponent v-if="currentView === 'login'" @login-success="onLoginSuccess" />
    <Comp_Frontend v-else-if="currentView === 'home'" @logout="onLogout" />
  </div>
</template>

<script>
import axios from "axios";
import LoginComponent from './components/Login_Component.vue'
import Comp_Frontend from './components/Frontend.vue'

export default {
  name: 'App',
  components: {
    LoginComponent,
    Comp_Frontend
  },
  data() {
    return {
      currentView: 'login'
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
      this.currentView = 'home';
      window.history.pushState({}, '', '/');
    },

    async onLogout() {
      try {
        // Backend den HttpOnly Cookie löschen lassen
        await axios.post("/auth/logout", {}, { withCredentials: true });
      } catch (e) {
        console.log("Logout Warning:", e);
      }
      localStorage.clear(); 

      this.currentView = 'login';
      window.history.pushState({}, '', '/auth');
    }
  },
  mounted() {
    // Browser Zurück-Button Logik
    window.addEventListener('popstate', () => {
      // Check ob das JWT Cookie noch da ist
      this.currentView = this.getCookie('jwt') ? 'home' : 'login';
    });
    
    // Initialer Check beim Laden der Seite
    if (this.getCookie('jwt')) {
      this.currentView = 'home';
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
body { 
  margin: 0; 
  font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
  background-color: #f8f9fa;
}
</style>