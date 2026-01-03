import { erstelleDatum } from '../support/utils';

describe('Box_Pos', () => {

const sampleData = {
  s_id: 187187187187,
  s_stamp: erstelleDatum(0),
  name: 'Zuckeristdrüben',
  weight_net: 9.27,
  weight_bru: 10.0,
  weight_tar: 9.5,
  quantity: 3,
  distance: 2000,
  date_crumbled: erstelleDatum(2),
  s_flags: '----------',
  lane: 1,
  comment: 'Normal__//Sample_TEST_CASE_187_DONT_CHANGE_187_TIMESTAMP:' + erstelleDatum(0)
};

const boxData = {
  b_id: 'TEST',
  name: 'sehrSinnvollerName',
  num_max: 187,
  typ: 'Typ 7',
  comment: 'Normal__//Box_TEST_CASE_187_DONT_CHANGE_187_TIMESTAMP:' + erstelleDatum(0)
};

  beforeEach(() => {
    cy.visit('http://localhost:8082/')

    cy.createSample(sampleData);
    cy.wait(500)
    cy.createBox(boxData);
  });

  afterEach(() => {
    cy.deleteSample(sampleData.comment)
    cy.deleteBox(boxData.comment)
  });

  it('Create entry and check', () => {
    const b_id = boxData.b_id
    const bpos_id= 1877
    const s_id = sampleData.s_id
    const date_exported = erstelleDatum(1)
    const name = 'sehrSinnvollerName'
    const num_max = 187
    const typ = 'Typ 7'
    const comment = 'Normal__//Box_Pos_TEST_CASE_187_DONT_CHANGE_187_TIMESTAMP:' + erstelleDatum(0)

    //---------- Create

    cy.get(':nth-child(1) > select').select('Box Pos')
    //Daten Laden
    cy.get('.btn-load').click()
    //neuer Eintrag
    cy.get('.btn-save').click()
    // B_ID
    cy.get('#field-b_id').type(b_id)
    // Bpos_id
    cy.get('#field-bpos_id').type(bpos_id)
    // S_Id
    cy.get('#field-s_id').type(s_id)
    // Date_exported
    cy.get('#field-date_exported').type(date_exported)
    //Save btn
    cy.get('.form-actions > .btn-save').click()
    
    // search creatted item
    cy.get('.search-input').clear().type(b_id)
    cy.get('.search-input-group > .btn').click()
    
    //open detail
    cy.get(':nth-child(1) > .sticky-col > .action-buttons > .btn-view').click()

    // Sicherstellen, dass das Modal offen ist
    cy.get('#detail-modal').should('be.visible')

    // B_ID
    cy.get('#detail-b_id').should('contain', b_id)
    // Bpos_id
    cy.get('#detail-bpos_id').should('contain', bpos_id)
    // S_Id
    cy.get('#detail-s_id').should('contain', s_id)
    // Date_exported
    cy.get('#detail-date_exported').should('not.be.empty')


    // -----------------------------------------------------
    // Edit
    // -----------------------------------------------------
    const date_exported1 = erstelleDatum(100)
    
    // click edit in deatail view
    cy.get('#btn-edit-from-detail').click()

    // Date_exported
    cy.get('#field-date_exported').clear().type(date_exported1)
    //Save btn
    cy.get('.form-actions > .btn-save').click()

    // search creatted item
    cy.get('.search-input').clear().type(b_id)
    cy.get('.search-input-group > .btn').click()
    
    //open detail
    cy.get(':nth-child(1) > .sticky-col > .action-buttons > .btn-view').click()

    // Sicherstellen, dass das Modal offen ist
    cy.get('#detail-modal').should('be.visible')

    // B_ID
    cy.get('#detail-b_id').should('contain', b_id)
    // Bpos_id
    cy.get('#detail-bpos_id').should('contain', bpos_id)
    // S_Id
    cy.get('#detail-s_id').should('contain', s_id)
    // Date_exported
    cy.get('#detail-date_exported').should('not.be.empty')


    //close detail view
    cy.get('#btn-close-detail').click()

    //delete Box pos
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