import { erstelleDatum } from '../support/utils';

describe('Report System Test:', () => {
  
  beforeEach(() => {
    // Login und Navigation zur Report-Ansicht
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    // Klick auf den "Report" Link in der Navigation (siehe App.vue)
    cy.get('.main-nav').contains('Report').click()
    
    // Sicherstellen, dass die Report-Komponente geladen ist
    cy.get('.report-header h2').should('contain', 'System Reports')
  });

  it('Check UI elements and default state', () => {
    // Prüfen ob die Dropdowns vorhanden sind
    cy.get('.controls-card').should('be.visible')
    
    // Report Auswahl (erstes Select)
    cy.get('.controls-card select').eq(0).should('contain', '-- Bitte wählen --')
    
    // Limit Auswahl (zweites Select) sollte standardmäßig 25 sein (siehe Vue data)
    cy.get('.controls-card select').eq(1).should('have.value', '25')
    
    // Button sollte deaktiviert sein, solange kein Report gewählt ist
    cy.get('.btn-load').should('be.disabled')
  })

  it('Load Report without parameters (e.g. BoxPos without Sample)', () => {
    // 1. Netzwerk-Request abfangen (bevor wir klicken!)
    // Wir hören auf alle GET-Anfragen an /api/reports/...
    cy.intercept('GET', '/api/reports/**').as('loadReport')

    const reportName = 'Box-Positionen ohne Sample'
    cy.get('.controls-card select').eq(0).select(reportName)
    
    // 2. Klicken
    cy.get('.btn-load').click()
    
    // 3. WICHTIG: Statt auf "Lade..." zu warten, warten wir, bis die API antwortet.
    // Das ist viel stabiler als UI-Texte, die nur millisekundenlang sichtbar sind.
    cy.wait('@loadReport')

    // 4. Ergebnis prüfen: Entweder Tabelle ODER "Keine Ergebnisse"
    // Wir nutzen .then(), um den aktuellen Body-Status synchron zu prüfen
    cy.get('body').then(($body) => {
      // Wir suchen im Body nach dem Element mit der Klasse .empty-state
      if ($body.find('.empty-state').length > 0) {
        // Fall A: Keine Ergebnisse
        cy.get('.empty-state').should('contain', 'Keine Ergebnisse für diesen Report gefunden.')
        cy.get('.empty-state').should('be.visible')
      } else {
        // Fall B: Tabelle vorhanden
        cy.get('table').should('be.visible')
        // Optional: Header prüfen
        cy.get('thead').should('exist')
      }
    })
  })

  it('Check validation for Reports with Date parameters', () => {
    // Wähle einen Report MIT Zeitangaben (z.B. Daily Report)
    const reportName = 'Daily Report'
    
    cy.get('.controls-card select').eq(0).select(reportName)
    
    // Zeit-Inputs MÜSSEN sichtbar sein
    cy.get('.time-inputs').should('be.visible')
    
    // Versuch ohne Datum zu laden
    cy.get('.btn-load').click()
    
    // Fehlermeldung erwarten
    cy.get('.error-box').should('be.visible')
    cy.get('.error-box').should('contain', 'Bitte Start- und Endzeitpunkt wählen')
  })

  it('Load Report with Date parameters successfully', () => {
    const reportName = 'Analysis ohne BoxPos'
    
    // 1. Report wählen
    cy.get('.controls-card select').eq(0).select(reportName)
    
    // 2. Datum setzen (Wir nehmen aktuelle Zeiträume)
    const today = new Date().toISOString().split('T')[0]
    // Ein Monat zurück für Startdatum
    const lastMonth = new Date()
    lastMonth.setMonth(lastMonth.getMonth() - 1)
    const startDate = lastMonth.toISOString().split('T')[0]

    // Inputs füllen (Vue v-model binds)
    cy.get('.time-inputs input[type="date"]').eq(0).type(startDate)
    cy.get('.time-inputs input[type="date"]').eq(1).type(today)
    
    // 3. Limit ändern (Testet auch das zweite Select)
    cy.get('.controls-card select').eq(1).select('10')
    
    // 4. Laden
    cy.get('.btn-load').click()
    
    // 5. Fehlerbox darf nicht mehr da sein
    cy.get('.error-box').should('not.exist')
    
    // 6. Tabelle oder Leermeldung prüfen
    cy.get('body').then(($body) => {
        // Prüfen ob Ladezustand beendet ist
        cy.get('.btn-load').should('contain', 'Report laden')

        if ($body.find('.table-scroll').length > 0) {
            // Wenn Daten da sind, prüfen ob Pagination Info sichtbar ist (außer bei daily-report)
            cy.get('.info-text').should('contain', 'Zeige')
        } else {
            cy.get('.empty-state').should('be.visible')
        }
    })
  })

  it('Check switching between reports resets state', () => {
    // 1. Wähle Daily Report (hat Datumsfelder)
    cy.get('.controls-card select').eq(0).select('Daily Report')
    cy.get('.time-inputs').should('be.visible')
    
    // Datum eingeben um State "schmutzig" zu machen
    cy.get('.time-inputs input[type="date"]').eq(0).type('2023-01-01')
    
    // 2. Wechsle zu Report ohne Datum (z.B. Ungültige EAN)
    cy.get('.controls-card select').eq(0).select('Samples mit ungültigem EAN-Code')
    
    // 3. Prüfen: Inputs weg? Fehler weg? Tabelle leer (bevor geladen wird)?
    cy.get('.time-inputs').should('not.exist')
    cy.get('.error-box').should('not.exist')
    cy.get('table').should('not.exist') // Tabelle sollte resettet sein
    
    // 4. Laden des neuen Reports
    cy.get('.btn-load').click()
    cy.get('body').should('not.contain', 'Bitte Start- und Endzeitpunkt wählen')
  })

})