describe('Login spec', () => {
  it('Login successfull', () => {
    // Appelle la commande personnalisée
    cy.customLogin();
    cy.customSession();
    cy.url().should('include', '/sessions')
  })
});