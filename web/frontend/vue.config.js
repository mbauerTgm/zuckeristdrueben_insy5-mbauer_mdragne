module.exports = {
  pwa: {
    name: 'INSY Frontend',
    short_name: 'INSY',
    themeColor: '#0f172a',
    backgroundColor: '#0f172a',
    display: 'standalone',
    
    // Keine Icons generieren
    iconPaths: {
      faviconSVG: null,
      favicon32: null,
      favicon16: null,
      appleTouchIcon: null,
      maskIcon: null,
      msTileImage: null
    },
    
    // Minimales Manifest
    manifestOptions: {
      name: 'INSY Frontend',
      short_name:  'INSY',
      description: 'INSY Frontend Anwendung',
      theme_color: '#0f172a',
      background_color:  '#0f172a',
      display:  'standalone',
      start_url: '/',
      icons: []
    },
    
    // Workbox Optionen
    workboxPluginMode: 'GenerateSW',
    workboxOptions: {
      skipWaiting: true,
      clientsClaim: true
    }
  }
}