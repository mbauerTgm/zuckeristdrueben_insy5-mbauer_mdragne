import { erstelleDatum } from '../support/utils';

describe('Global Filtering & Settings Test:', () => {

  const uniqueComment = 'GlobalTest_' + Date.now()
  const sId = '777187777777'

  before(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')

    cy.createSample({
        s_id: sId,
        s_stamp: erstelleDatum(0),
        name: 'GlobalBase',
        weight_net: 10, weight_bru: 11, weight_tar: 1, quantity: 1, distance: 10,
        date_crumbled: erstelleDatum(0), s_flags: '-', lane: 1, 
        comment: uniqueComment
    })

    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
    cy.get('.btn-save').click().click()
    cy.wait(500)
    cy.get('#field-s_id').type(sId)
    cy.get('#field-date_in').type(erstelleDatum(0)) 
    cy.get('.form-actions > .btn-save').click()
    
    cy.get('[data-cy="log-out-btn"]').click()
  })

  after(() => {
    cy.visit('http://localhost:8082/auth')
    cy.get('#username', { timeout: 10000 }).should('be.visible')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    // Analysis löschen
    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
    
    cy.contains('button', 'Filter').click()
    cy.contains('.filter-group', 'Sample ID').within(() => {
        cy.get('input').eq(0).type(sId)
        cy.get('input').eq(1).type(sId)
    })
    cy.get('.filter-dropdown .btn-save').click()
    cy.wait(1000)

    cy.get('body').then($body => {
        if ($body.find('.btn-delete').length > 0) {
             cy.get('.btn-delete').first().click()
             cy.get('.modal-actions > .btn-delete').click()
             cy.wait(1000)
        }
    })
    
    cy.deleteSample(uniqueComment)
  })
  
  beforeEach(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    cy.intercept('GET', '/api/analysis/filter*').as('anyFilterRequest')
  })

  it('Global Date Filter: Set, Apply, Persist and Reset', () => {
    const startDate = '2024-01-01T12:00:35'
    const endDate = '2024-12-31T12:00:00'

    cy.intercept('GET', '**globalDateIn.to**').as('completeFilterRequest')

    // Einstellungen öffnen
    cy.get('button[title="Einstellungen"]').click()
    cy.get('.settings-dropdown').should('be.visible')

    // Datum setzen
    cy.get('.settings-dropdown input[type="datetime-local"]').eq(0).type(startDate)
    cy.get('.settings-dropdown input[type="datetime-local"]').eq(1).type(endDate)
    
    cy.wait(100) 

    // Anwenden klicken
    cy.get('.settings-dropdown .btn-save').click()

    // Prüfen, ob Dropdown zugeht
    cy.get('.settings-dropdown').should('not.exist')

    // Prüfen, ob der Request die globalen Parameter enthält
    cy.wait('@completeFilterRequest').then((interception) => {
      expect(interception.request.url).to.include('globalDateIn.from')
      expect(interception.request.url).to.include('globalDateIn.to')
    })

    // Reload testen (Persistenz Check)
    cy.reload()
    // Sicherstellen, dass die App geladen ist (Tabelle sichtbar)
    cy.get('table').should('be.visible')
    
    // Einstellungen öffnen -> Werte sollten noch da sein
    cy.get('button[title="Einstellungen"]').click()
    cy.get('.settings-dropdown input[type="datetime-local"]').eq(0).should('contain.value', startDate.slice(startDate.length-1, startDate.length-3))

    // Reset testen
    cy.contains('.btn-link', 'Reset').click()
    cy.get('button[title="Einstellungen"]').click()
    cy.get('.settings-dropdown input[type="datetime-local"]').eq(0).should('have.value', '')
    
    // Anwenden nach Reset
    cy.get('.settings-dropdown .btn-save').click()
    
    cy.wait('@anyFilterRequest').then((interception) => {
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