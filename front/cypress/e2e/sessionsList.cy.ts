describe('Sessions Page spec', () => {
  it('Displays sessions and the "Create" button for admins', () => {
    const sessions = [
      {
        id: 2,
        name: 'session 1',
        date: '2012-01-01T00:00:00.000+00:00',
        teacher_id: null,
        description: 'my description',
        users: [],
        createdAt: '2024-12-12T15:39:30',
        updatedAt: '2024-12-12T15:39:30',
      },
      {
        id: 3,
        name: 'session 2',
        date: '2013-02-01T00:00:00.000+00:00',
        teacher_id: 1,
        description: 'another description',
        users: [{ id: 1, name: 'John Doe' }],
        createdAt: '2024-12-12T15:39:36',
        updatedAt: '2024-12-12T15:39:36',
      },
    ];
    // Appelle la commande personnalisée
    cy.customSession(sessions);
    cy.customLogin();

    // Vérifier que le titre "Rentals available" est présent
    cy.contains('Rentals available');

    // Vérifier que des sessions sont affichées (on suppose qu'il y a des sessions disponibles dans l'interface)
    cy.get('mat-card').should('have.length.greaterThan', 0);

    // Vérifier que l'image pour les sessions est présente
    cy.get('img.picture').should('be.visible');

    // Si l'utilisateur est admin, vérifier que le bouton "Create" est visible
    // (on va supposer ici que l'utilisateur est admin, mais vous pouvez mocker l'admin si nécessaire)
    cy.get('button[routerLink="create"]').should('be.visible');

    // Vérifier que les boutons "Detail" et "Edit" sont présents pour chaque session
    cy.get('mat-card-actions').each(($el) => {
      cy.wrap($el).find('button').should('have.length', 2); // Un bouton "Detail" et un bouton "Edit"
    });
  });
});
