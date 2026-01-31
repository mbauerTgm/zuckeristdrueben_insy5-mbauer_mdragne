import { erstelleDatum } from '../support/utils';

describe('Global Filtering & Settings Test:', () => {
  
  beforeEach(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    cy.intercept('GET', '/api/analysis/filter*').as('filterRequest')
  })

  it('Global Date Filter: Set, Apply, Persist and Reset', () => {
    const startDate = '2024-01-01T12:00:00'
    const endDate = '2024-12-31T12:00:00'

    // Einstellungen öffnen
    cy.get('button[title="Einstellungen"]').click()
    cy.get('.settings-dropdown').should('be.visible')

    // Datum setzen
    cy.get('.settings-dropdown input[type="datetime-local"]').eq(0).type(startDate)
    cy.get('.settings-dropdown input[type="datetime-local"]').eq(1).type(endDate)
    cy.wait(500)

    // Anwenden klicken
    cy.get('.settings-dropdown .btn-save').click()

    // Prüfen, ob Dropdown zugeht
    cy.get('.settings-dropdown').should('not.exist')

    // Prüfen, ob der Request die globalen Parameter enthält
    cy.wait('@filterRequest').then((interception) => {
      expect(interception.request.url).to.include('globalDateIn.from')
      expect(interception.request.url).to.include('globalDateIn.to')
    })

    // Reload testen (Persistenz Check)
    cy.reload()
    // Warten bis App geladen
    cy.wait(1000)
    
    // Einstellungen öffnen -> Werte sollten noch da sein
    cy.get('button[title="Einstellungen"]').click()
    cy.get('.settings-dropdown input[type="datetime-local"]').eq(0).should('have.value', startDate)

    // Reset testen
    cy.contains('.btn-link', 'Reset').click()
    cy.get('.settings-dropdown input[type="datetime-local"]').eq(0).should('have.value', '')
    
    // Anwenden nach Reset
    cy.get('.settings-dropdown .btn-save').click()
    
    // Request sollte nun keine Parameter mehr haben
    cy.wait('@filterRequest').then((interception) => {
      expect(interception.response.statusCode).to.eq(200)
    })
  })

  it('Dark Mode Toggle', () => {
    cy.get('button[title="Einstellungen"]').click()
    
    // Check Initial State (Hintergrund hell)
    cy.get('body').should('not.have.class', 'dark-theme')

    // Toggle Dark Mode
    cy.get('.btn-dark-toggle').click()
    
    // Check Dark Mode Active
    cy.get('body').should('have.class', 'dark-theme')
    
    // Toggle Back
    cy.get('.btn-dark-toggle').click()
    cy.get('body').should('not.have.class', 'dark-theme')
  })
})