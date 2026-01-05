module.exports = {
  pwa: {
    name: 'INSY Frontend',
    themeColor: '#0f172a',
    msTileColor: '#0f172a',
    appleMobileWebAppCapable: 'yes',
    appleMobileWebAppStatusBarStyle: 'default',

    // Pfade relativ zu /public
    iconPaths: {
      favicon32: 'img/icons/favicon-32x32.png',
      favicon16: 'img/icons/favicon-16x16.png',
      appleTouchIcon: 'img/icons/apple-touch-icon.png',
      msTileImage: 'img/icons/msapplication-icon-144x144.png'
    },

    // Manifest-Inhalt (WICHTIG: icons NICHT leer!)
    manifestOptions: {
      name: 'INSY Frontend',
      short_name: 'INSY',
      description: 'INSY Frontend Anwendung',
      theme_color: '#0f172a',
      background_color: '#0f172a',
      display: 'standalone',
      start_url: '/',
      icons: [
        {
          src: '/img/icons/android-chrome-192x192.png',
          sizes: '192x192',
          type: 'image/png',
          purpose: 'any'
        },
        {
          src: '/img/icons/android-chrome-512x512.png',
          sizes: '512x512',
          type: 'image/png',
          purpose: 'any'
        }
      ]
    },

    // Service Worker (Workbox)
    workboxPluginMode: 'GenerateSW',
    workboxOptions: {
      skipWaiting: true,
      clientsClaim: true
    }
  }
}
