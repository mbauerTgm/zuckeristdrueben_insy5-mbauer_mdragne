<template>
  <div class="split-screen">
    <div class="visual-side">
      <div class="visual-content">
        <div class="logo-circle">
          <svg viewBox="0 0 24 24" width="40" height="40" fill="white">
            <path d="M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M12,4A8,8 0 0,1 20,12A8,8 0 0,1 12,20A8,8 0 0,1 4,12A8,8 0 0,1 12,4M12,6A6,6 0 0,0 6,12A6,6 0 0,0 12,18A6,6 0 0,0 18,12A6,6 0 0,0 12,6M12,8A4,4 0 0,1 16,12A4,4 0 0,1 12,16A4,4 0 0,1 8,12A4,4 0 0,1 12,8Z" />
          </svg>
        </div>
        <h1>Venlab.<br>Analysis.</h1>
        <p>Willkommen zurück im Labor-System.</p>
      </div>
      <div class="visual-bg"></div>
    </div>

    <div class="form-side">
      <div class="form-container">
        <div class="form-header">
          <h2>Anmelden</h2>
          <p>Bitte geben Sie Ihre Daten ein.</p>
        </div>

        <form @submit.prevent="login">
          <transition name="fade">
            <div v-if="checkLogin" class="error-banner">
              <svg viewBox="0 0 24 24" width="20" height="20" fill="currentColor"><path d="M13,13H11V7H13M13,17H11V15H13M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2Z" /></svg>
              <span>{{ checkLogin }}</span>
            </div>
          </transition>

          <div class="input-group">
            <label for="username">User ID</label>
            <input 
              id="username"
              type="text" 
              v-model="userName" 
              placeholder="z.B. user123"
              required 
            />
          </div>

          <div class="input-group">
            <label for="password">Passwort</label>
            <div class="password-wrapper">
              <input 
                id="password"
                :type="showPassword ? 'text' : 'password'" 
                v-model="password" 
                placeholder="••••••••"
                required 
              />
              <button type="button" class="eye-btn" @click="togglePasswordVisibility">
                <svg v-if="showPassword" viewBox="0 0 24 24" width="20" height="20" fill="currentColor"><path d="M12,9A3,3 0 0,0 9,12A3,3 0 0,0 12,15A3,3 0 0,0 15,12A3,3 0 0,0 12,9M12,17A5,5 0 0,1 7,12A5,5 0 0,1 12,7A5,5 0 0,1 17,12A5,5 0 0,1 12,17M12,4.5C7,4.5 2.73,7.61 1,12C2.73,16.39 7,19.5 12,19.5C17,19.5 21.27,16.39 23,12C21.27,7.61 17,4.5 12,4.5Z" /></svg>
                <svg v-else viewBox="0 0 24 24" width="20" height="20" fill="#94a3b8"><path d="M11.83,9L15,12.16C15,12.11 15,12.05 15,12A3,3 0 0,0 12,9C11.94,9 11.89,9 11.83,9M7.53,9.8L9.08,11.35C9.03,11.56 9,11.77 9,12A3,3 0 0,0 12,15C12.22,15 12.44,14.97 12.65,14.92L14.2,16.47C13.53,16.8 12.79,17 12,17A5,5 0 0,1 7,12C7,11.21 7.2,10.47 7.53,9.8M2,4.27L4.28,6.55L4.73,7C3.08,8.3 1.78,10 1,12C2.73,16.39 7,19.5 12,19.5C13.55,19.5 15.03,19.2 16.38,18.66L16.81,19.08L19.73,22L21,20.73L3.27,3M12,7A5,5 0 0,1 17,12C17,12.64 16.87,13.26 16.64,13.82L19.57,16.75C21.07,15.5 22.27,13.86 23,12C21.27,7.61 17,4.5 12,4.5C10.6,4.5 9.27,4.73 8.04,5.16L10.12,7.24C10.7,7.09 11.32,7 12,7Z" /></svg>
              </button>
            </div>
          </div>

          <button type="submit" class="submit-btn">
            Einloggen
            <svg viewBox="0 0 24 24" width="18" height="18" fill="currentColor"><path d="M4,11V13H16L10.5,18.5L11.92,19.92L19.84,12L11.92,4.08L10.5,5.5L16,11H4Z" /></svg>
          </button>
        </form>

        <p class="footer-text">© 2025/26 INSY Zuckerfabrik</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginComponent",
  data() {
    return {
      userName: "",
      password: "",
      showPassword: false,
      checkLogin: "",
    };
  },
  methods: {
    async login() {
      this.checkLogin = "";
      try {
        // withCredentials: true sorgt dafür, dass Cookies angenommen werden
        const response = await axios.post("/auth/login", {
          UsersID: this.userName, 
          password: this.password,
        }, { withCredentials: true });

        const { role, UsersID, UsersId } = response.data;
        
        localStorage.setItem("role", role);
        localStorage.setItem("UsersID", UsersID);
        if(UsersId) localStorage.setItem("UsersId", UsersId);
        
        // Globale Axios Config für zukünftige Requests setzen
        axios.defaults.withCredentials = true; 

        this.$emit('login-success');

      } catch (error) {
        console.error(error);
        this.checkLogin = "Zugangsdaten ungültig.";
      }
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
  },
};
</script>

<style scoped>
/* Google Font Import (Optional, falls nicht global vorhanden) */
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');

.split-screen {
  display: flex;
  height: 100vh;
  width: 100vw;
  font-family: 'Inter', sans-serif;
  background-color: white;
  overflow: hidden;
}

/* --- LINKER BEREICH (VISUAL) --- */
.visual-side {
  flex: 1;
  background-color: #0f172a;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 60px;
  color: white;
  overflow: hidden;
}

.visual-bg {
  position: absolute;
  top: 0; left: 0; right: 0; bottom: 0;
  background: radial-gradient(circle at 10% 20%, #1e293b 0%, #0f172a 90%);
  z-index: 1;
}

.visual-side::before {
  content: '';
  position: absolute;
  top: -100px; right: -100px;
  width: 400px; height: 400px;
  background: #198754;
  opacity: 0.1;
  filter: blur(80px);
  border-radius: 50%;
  z-index: 2;
}

.visual-content {
  position: relative;
  z-index: 10;
  max-width: 400px;
}

.logo-circle {
  width: 64px; height: 64px;
  background: rgba(255,255,255,0.1);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 30px;
  backdrop-filter: blur(10px);
}

.visual-content h1 {
  font-size: 3.5rem;
  font-weight: 700;
  line-height: 1.1;
  margin-bottom: 20px;
  background: -webkit-linear-gradient(#fff, #94a3b8);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.visual-content p {
  color: #94a3b8;
  font-size: 1.1rem;
  line-height: 1.6;
}

/* --- RECHTER BEREICH (FORM) --- */
.form-side {
  flex: 0 0 500px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ffffff;
  padding: 40px;
}

.form-container {
  width: 100%;
  max-width: 360px;
}

.form-header {
  margin-bottom: 40px;
}

.form-header h2 {
  font-size: 2rem;
  font-weight: 700;
  color: #0f172a;
  margin-bottom: 10px;
}

.form-header p {
  color: #64748b;
}

/* Inputs */
.input-group {
  margin-bottom: 24px;
}

.input-group label {
  display: block;
  font-size: 0.85rem;
  font-weight: 600;
  color: #334155;
  margin-bottom: 8px;
}

.input-group input {
  width: 100%;
  padding: 14px 16px;
  font-size: 1rem;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  background-color: #f8fafc;
  transition: all 0.2s ease;
  color: #0f172a;
  box-sizing: border-box;
}

.input-group input:focus {
  outline: none;
  border-color: #198754;
  background-color: #fff;
  box-shadow: 0 0 0 4px rgba(25, 135, 84, 0.1);
}

.password-wrapper {
  position: relative;
}

.eye-btn {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  color: #64748b;
  display: flex;
}

.eye-btn:hover { color: #334155; }

/* Submit Button */
.submit-btn {
  width: 100%;
  padding: 16px;
  background-color: #0f172a; /* Dunkler Button wirkt edel */
  color: white;
  font-weight: 600;
  font-size: 1rem;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  transition: all 0.2s;
  margin-top: 10px;
}

.submit-btn:hover {
  background-color: #198754;
  transform: translateY(-1px);
}

/* Error Banner */
.error-banner {
  background-color: #fef2f2;
  border: 1px solid #fecaca;
  color: #b91c1c;
  padding: 12px;
  border-radius: 8px;
  margin-bottom: 24px;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  gap: 10px;
}

.footer-text {
  margin-top: 40px;
  text-align: center;
  font-size: 0.8rem;
  color: #94a3b8;
}

/* Responsive: Auf Handy untereinander */
@media (max-width: 900px) {
  .split-screen { flex-direction: column; overflow-y: auto; }
  .visual-side { padding: 40px; min-height: 200px; flex: none; }
  .visual-content h1 { font-size: 2rem; }
  .logo-circle { width: 48px; height: 48px; margin-bottom: 15px; }
  .form-side { flex: 1; width: 100%; padding: 40px 20px; box-sizing: border-box; }
}
</style>