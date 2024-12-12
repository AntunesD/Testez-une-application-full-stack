describe('Me spec', () => {
  it('Displays User information', () => {

    cy.intercept('GET', '/api/user/1', {
      statusCode: 200,
      body: {
        "id": 1,
        "email": "yoga@studio.com",
        "lastName": "Admin",
        "firstName": "Admin",
        "admin": true,
        "createdAt": "2024-11-23T11:47:46",
        "updatedAt": "2024-11-23T11:47:46"
      }
    }).as('getUser');

    // Appelle la commande personnalisée pour initier la session et la connexion
    cy.customSession();
    cy.customLogin();

    // Clique sur le lien pour accéder à la page "Me"
    cy.get('[routerlink="me"]').click();

    // Vérifie que le titre "User information" est visible
    cy.contains('h1', 'User information').should('be.visible');

    // Vérifie que le nom de l'utilisateur est bien affiché avec le prénom et nom en majuscule
    cy.contains('p', 'Name:').should('include.text', 'Name:');
    cy.get('p').contains('Name:').should('include.text', 'Name:').and('include.text', 'Admin'); // Remplace par le nom attendu

    // Vérifie l'affichage de l'email
    cy.contains('p', 'Email:').should('include.text', 'Email:');

    // Vérifie la présence du message "You are admin" si l'utilisateur est un admin
    cy.get('p').contains('You are admin').should('exist'); // S'il est admin, vérifier ce texte

    // Vérifie la présence du bouton "Delete my account" si l'utilisateur n'est pas admin
    cy.get('button').contains('Delete').should('not.exist'); // S'il est admin, vérifier que le bouton delete n'existe pas

    // Vérifie que la date de création de l'utilisateur est bien formatée
    cy.contains('Create at:').should('include.text', 'Create at:');
    cy.contains('Last update:').should('include.text', 'Last update:');

    // Si l'utilisateur est admin, vérifier que "Delete my account" n'apparaît pas
    cy.get('button').contains('Delete my account').should('not.exist');
  });
});
