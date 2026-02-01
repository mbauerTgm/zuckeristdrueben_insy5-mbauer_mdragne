import { erstelleDatum } from '../support/utils';

describe('CSV Export Test:', () => {
  const uniqueComment = 'ExportTest_' + Date.now()
  const sId = '555555555555'

  before(() => {
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')

    const now = new Date().toISOString();
    
    // Sample erstellen
    cy.request({
        method: 'POST',
        url: 'http://localhost:8082/api/samples',
        body: {
            s_id: sId,
            s_stamp: now,
            name: 'ExportBase',
            weight_net: 10, weight_bru: 11, weight_tar: 1, quantity: 1, distance: 10,
            date_crumbled: now, s_flags: '-', lane: 1, 
            comment: uniqueComment
        }
    })

    // Analysis erstellen
    cy.request({
        method: 'POST',
        url: 'http://localhost:8082/api/analysis',
        body: {
            s_id: sId,
            s_stamp: now,
            pol: 10.5,
            date_in: now, a_flags: '-', 
            comment: uniqueComment
        }
    })
    
    cy.get('[data-cy="log-out-btn"]').click()
  })

  after(() => {
    cy.request({ method: 'POST', url: 'http://localhost:8082/api/auth/logout', failOnStatusCode: false })

    cy.visit('http://localhost:8082/auth')
    cy.get('#username', { timeout: 10000 }).should('be.visible')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    // Cleanup
    cy.request('GET', `http://localhost:8082/api/analysis/filter?page=0&size=10&sId.from=${sId}&sId.to=${sId}`).then(res => {
        const items = res.body.content || []
        items.forEach(a => {
            cy.request({
                method: 'DELETE',
                url: `http://localhost:8082/api/analysis/${a.a_id}`,
                failOnStatusCode: false
            })
        })
    })
    
    cy.request('GET', `http://localhost:8082/api/samples/filter?page=0&size=10&sId.from=${sId}&sId.to=${sId}`).then(res => {
        const items = res.body.content || []
        items.forEach(s => {
            const stamp = encodeURIComponent(s.s_stamp)
            cy.request({
                method: 'DELETE',
                url: `http://localhost:8082/api/samples/${s.s_id}/${stamp}`,
                failOnStatusCode: false
            })
        })
    })
  })

  beforeEach(() => {
    cy.request({ method: 'POST', url: 'http://localhost:8082/api/auth/logout', failOnStatusCode: false })
    cy.visit('http://localhost:8082/auth')
    cy.get('#username').should('be.visible')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')
    
    cy.get(':nth-child(1) > select').select('Analysis')
    cy.get('.btn-load').click()
  })

  it('Check CSV Export Functionality (Basic)', () => {
    cy.intercept('GET', '/api/analysis/export*', {
        statusCode: 200,
        body: 'Header1,Header2\nValue1,Value2',
        headers: { 'content-type': 'text/csv' }
    }).as('exportRequest')

    cy.get('.search-input').type(sId)
    cy.get('.search-input-group > .btn').click()
    cy.wait(500)

    cy.get('button').contains('Export CSV').click()

    cy.wait('@exportRequest').then((interception) => {
        const url = interception.request.url
        expect(url).to.include('columns=') 
    })

    cy.get('.error-text').should('not.exist')
  })

  it('Check Export with Backend Filters applied', () => {
    // FIX: Intercept definieren BEVOR wir Aktionen auslösen!
    // Wenn wir das erst nach dem Klick machen, ist der Request schon längst weg.
    cy.intercept('GET', '/api/analysis/filter*').as('filterApplied')
    
    cy.intercept('GET', '/api/analysis/export*', {
        statusCode: 200,
        body: 'Header1,Header2\nFilteredValue1,FilteredValue2',
        headers: { 'content-type': 'text/csv' }
    }).as('exportRequestFiltered')

    // 1. Backend Filter öffnen
    cy.contains('button', 'Filter').click()
    cy.get('.filter-dropdown').should('be.visible')

    // 2. Filter setzen
    cy.contains('.filter-group', 'Sample ID').within(() => {
        cy.get('input').eq(0).type(sId)
        cy.get('input').eq(1).type(sId)
    })

    // 3. Filter anwenden (löst den Request aus, den wir oben abfangen)
    cy.get('.filter-dropdown .btn-save').click()
    cy.get('.filter-dropdown').should('not.exist')
    
    // Warten auf den Request, den wir VORHER definiert haben
    cy.wait('@filterApplied')

    // 4. Export Button klicken
    cy.get('button').contains('Export CSV').click()

    // 5. Assert
    cy.wait('@exportRequestFiltered').then((interception) => {
        const url = interception.request.url
        expect(url).to.include(`sId.from=${sId}`)
        expect(url).to.include(`sId.to=${sId}`)
        expect(url).to.include('columns=')
    })
    
    cy.get('.error-text').should('not.exist')
  })
})