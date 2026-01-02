import { register } from 'register-service-worker'

if (process.env.NODE_ENV === 'production') {
  register(`${process.env. BASE_URL}service-worker.js`, {
    ready() {
      console. log('App läuft aus dem Cache.')
    },
    updated() {
      console. log('Neue Version verfügbar.')
      window.location.reload()
    }
  })
}