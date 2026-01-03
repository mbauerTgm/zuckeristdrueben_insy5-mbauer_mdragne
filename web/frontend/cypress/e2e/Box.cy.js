import { erstelleDatum } from '../support/utils';

describe('Box Test:', () => {
  it('Create entry, check, edit and delete', () => {

    cy.visit('http://localhost:8082/')

    //---------- Create
    cy.get(':nth-child(1) > select').select('Box')

    //Daten Laden
    cy.get('.btn-load').click()

    //cy.wait(5000)

    //neuer Eintrag
    cy.get('.btn-save').click()

    const b_id = 'TEST'
    const name = 'sehrSinnvollerName'
    const num_max = 187
    const typ = 'Typ 7'
    const comment = 'Normal__//Box_TEST_CASE_187_DONT_CHANGE_187_TIMESTAMP:' + erstelleDatum(0)

    //B-Id
    cy.get('#field-b_id').type(b_id)
    //name
    cy.get('#field-name').type(name)
    //num max
    cy.get('#field-num_max').clear().type(num_max)
    //typ
    cy.get(':nth-child(4) > select').select(typ)
    //kommentar
    cy.get('#field-comment').type(comment)
    

    //Save btn
    cy.get('.form-actions > .btn-save').click()

    //check if everything was saved correctly
    // search creatted item
    cy.get('.search-input').clear().type(comment)
    cy.get('.search-input-group > .btn').click()

    //open detail
    cy.get(':nth-child(1) > .sticky-col > .action-buttons > .btn-view').click()

    // Sicherstellen, dass das Modal offen ist
    cy.get('#detail-modal').should('be.visible')

    //B-Id
    cy.get('#detail-b_id').should('contain', b_id)
    //name
    cy.get('#detail-name').should('contain', name)
    //num max
    cy.get('#detail-num_max').should('contain', num_max)
    //typ
    cy.get('#detail-type').should('contain', typ.replace(/\D/g, ""))
    //kommentar
    cy.get('#detail-comment').should('contain', comment)

    //Edit----------------
    // click edit in deatail view
    cy.get('#btn-edit-from-detail').click()

    //check if we are in edit mode

    const name1 = 'VeränderterAberImmerNochSehrSinnvollerName'
    const num_max1 = 787
    const typ1 = 'Typ 8'

    //name
    cy.get('#field-name').clear().type(name1)
    //num max
    cy.get('#field-num_max').clear().type(num_max1)
    //typ
    cy.get(':nth-child(4) > select').select(typ1)

    //Save btn
    cy.get('.form-actions > .btn-save').click()

    // search creatted item
    cy.get('.search-input').clear().type(comment)
    cy.get('.search-input-group > .btn').click()

    //open detail
    cy.get(':nth-child(1) > .sticky-col > .action-buttons > .btn-view').click()

    // Sicherstellen, dass das Modal offen ist
    cy.get('#detail-modal').should('be.visible')

    //B-Id
    cy.get('#detail-b_id').should('contain', b_id)
    //name
    cy.get('#detail-name').should('contain', name1)
    //num max
    cy.get('#detail-num_max').should('contain', num_max1)
    //typ
    cy.get('#detail-type').should('contain', typ1.replace(/\D/g, ""))
    //kommentar
    cy.get('#detail-comment').should('contain', comment)

    //close detail view
    cy.get('#btn-close-detail').click()

    //delete item
    cy.get('.btn-delete').click()
    cy.get('.modal-actions > .btn-delete').click()
    cy.get('body').then(($body) => {
      if ($body.find('.info-text:contains("Zeige 0 von 0 Einträgen")').length > 0) {
        cy.log('Info-Text ist da')
      } else if ($body.find('.status-text:contains("Keine Daten vorhanden.")').length > 0) {
        cy.log('Status-Text ist da')
      } else {
        throw new Error('Keiner der Texte ist vorhanden')
      }
    })

  })
})