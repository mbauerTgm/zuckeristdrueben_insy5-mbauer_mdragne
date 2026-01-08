import { erstelleDatum } from '../support/utils';

describe('Sample Test:', () => {
  it('Create entry, check, edit and delete', () => {
    
    cy.visit('http://localhost:8082/')
    cy.login('TestAdmin','Sehr_Schwieriges_Test_Passwort!!_Sehr_Geheim_12253')

    //---------- Create
    cy.get(':nth-child(1) > select').select('Sample')

    //Daten Laden
    cy.get('.btn-load').click()

    cy.wait(5000)

    //neuer Eintrag
    cy.get('.btn-save').click()

    const s_id = 187187187187
    const s_stamp = erstelleDatum(0)
    const name = 'Zuckeristdrüben'
    const weight_net = 9.27
    const weight_bru = 10.0
    const weight_tar = 9.5
    const quantity = 3
    const distance = 2000
    const date_crumbled = erstelleDatum(2)
    const s_flags = '----------'
    const lane = 1
    const comment = 'Normal__//Sample_TEST_CASE_187_DONT_CHANGE_187_TIMESTAMP:' + erstelleDatum(0)

    //S-ID
    cy.get('#field-s_id').type(s_id)
    //s-stamp
    cy.get('#field-s_stamp').type(s_stamp)
    //Name
    cy.get('#field-name').type(name)
    //Weight_net
    cy.get('#field-weight_net').type(weight_net)
    //Weight_bru
    cy.get('#field-weight_bru').type(weight_bru)
    //Weight_tar
    cy.get('#field-weight_tar').type(weight_tar)
    //Quantity
    cy.get('#field-quantity').type(quantity)
    //Distance
    cy.get('#field-distance').type(distance)
    //Date Crumbled
    cy.get('#field-date_crumbled').type(date_crumbled)
    //S_flags
    cy.get('#field-s_flags').type(s_flags)
    //Lane
    cy.get('#field-lane').type(lane)
    //Comment
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

    // S-ID
    cy.get('#detail-s_id').should('contain', s_id)
    //s-stamp
    cy.get('#detail-s_stamp').should('not.be.empty')
    //Name
    cy.get('#detail-name').should('contain', name)
    //Weight_net
    cy.get('#detail-weight_net').should('contain', weight_net)
    //Weight_bru
    cy.get('#detail-weight_bru').should('contain', weight_bru)
    //Weight_tar
    cy.get('#detail-weight_tar').should('contain', weight_tar)
    //Quantity
    cy.get('#detail-quantity').should('contain', quantity)
    //Diastance
    cy.get('#detail-distance').should('contain', distance)
    //Date Crumbled
    cy.get('#detail-date_crumbled').should('not.be.empty')
    //S_flags
    cy.get('#detail-s_flags').should('contain', s_flags)
    //Lane
    cy.get('#detail-lane').should('contain', lane)
    //Comment
    cy.get('#detail-comment').should('contain', comment)


    //Edit----------------
    // click edit in deatail view
    cy.get('#btn-edit-from-detail').click()

    //check if we are in edit mode
    cy.get('#field-s_id').should('have.value', s_id)

    //NAME
    const name1 = 'Verrückter_Edititierter_Name'
    //WEIGHT_NET
    const weight_net1 = 88.25
    //WEIGHT_BRU
    const weight_bru1 = 90.0
    //WEIGHT_TAR
    const weight_tar1 = 89.5
    //Quantity
    const quantity1 = 5
    //Diastance
    const distance1 = 20
    //Date_crumbled
    const date_crumbled1 = erstelleDatum(5)
    //S_flags
    const s_flags1 = '--------M-'
    //LANE
    const lane1 = 2

    //Name
    cy.get('#field-name').clear().type(name1)
    //Weight_net
    cy.get('#field-weight_net').clear().type(weight_net1)
    //Weight_bru
    cy.get('#field-weight_bru').clear().type(weight_bru1)
    //Weight_tar
    cy.get('#field-weight_tar').clear().type(weight_tar1)
    //Quantity
    cy.get('#field-quantity').clear().type(quantity1)
    //Distance
    cy.get('#field-distance').clear().type(distance1)
    //Date Crumbled
    cy.get('#field-date_crumbled').clear().type(date_crumbled1)
    //S_flags
    cy.get('#field-s_flags').clear().type(s_flags1)
    //Lane
    cy.get('#field-lane').clear().type(lane1)

    //Save btn
    cy.get('.form-actions > .btn-save').click()

    // search creatted item
    cy.get('.search-input').clear().type(comment)
    cy.get('.search-input-group > .btn').click()

    //open detail
    cy.get(':nth-child(1) > .sticky-col > .action-buttons > .btn-view').click()

    // Sicherstellen, dass das Modal offen ist
    cy.get('#detail-modal').should('be.visible')

    //Name
    cy.get('#detail-name').should('contain', name1)
    //Weight_net
    cy.get('#detail-weight_net').should('contain', weight_net1)
    //Weight_bru
    cy.get('#detail-weight_bru').should('contain', weight_bru1)
    //Weight_tar
    cy.get('#detail-weight_tar').should('contain', weight_tar1)
    //Quantity
    cy.get('#detail-quantity').should('contain', quantity1)
    //Diastance
    cy.get('#detail-distance').should('contain', distance1)
    //Date Crumbled
    cy.get('#detail-date_crumbled').should('not.be.empty')
    //S_flags
    cy.get('#detail-s_flags').should('contain', s_flags1)
    //Lane
    cy.get('#detail-lane').should('contain', lane1)
    //Comment
    cy.get('#detail-comment').should('contain', comment)

    //close detail view
    cy.get('#btn-close-detail').click()

    //delete item
    cy.get('.btn-delete').click()
    cy.get('.modal-actions > .btn-delete').click()
    cy.get('body').should($body => {
      const infoText = $body.find('.info-text:contains("Zeige 0 von 0 Einträgen")').length > 0;
      const statusText = $body.find('.status-text:contains("Keine Daten vorhanden.")').length > 0;

      expect(infoText || statusText, 'Info- oder Status-Text vorhanden').to.be.true;
    });
  })

  it('Check create required error', () => {
    
    cy.get(':nth-child(1) > select').select('Sample')
    //Daten Laden
    cy.get('.btn-load').click()
    //neuer Eintrag
    cy.get('.btn-save').click()
    //Save btn
    cy.get('.form-actions > .btn-save').click()
    cy.get('.error-text').should('contain', 'Alle Pflichtfelder müssen ausgefüllt sein')
    cy.get('.error-text').should('contain', 'Fehlende Felder: - S id - S stamp')
  })
  
})