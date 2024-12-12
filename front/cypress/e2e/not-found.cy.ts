describe('404 Error Page spec', () => {
  it('Displays the "Page not found!" message', () => {
    // Visiter une page inexistante
    cy.visit('/404');

    // Vérifier que le message "not found!" est affiché
    cy.contains('not found')
  });
});
