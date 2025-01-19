# Récapitulatif des Tests Cypress

| **Fichier**               | **Tests Réalisés**                                                                   |
| ------------------------- | ------------------------------------------------------------------------------------ |
| **login-error.cy.ts**     | - Test de connexion échouée avec des identifiants incorrects                         |
|                           | - Champ email devient rouge lorsqu'il est cliqué sans être rempli                    |
| **login.cy.ts**           | - Connexion réussie et redirection vers la page des sessions                         |
| **me.cy.ts**              | - Affichage des informations de l'utilisateur, y compris le nom et l'email           |
| **not-found.cy.ts**       | - Affichage du message "Page non trouvée !" lors de la visite d'une page inexistante |
| **register.cy.ts**        | - Inscription réussie d'un nouvel utilisateur                                        |
|                           | - Affichage d'une erreur lors de l'échec de l'inscription                            |
| **sessionsDetails.cy.ts** | - Affichage des détails de la session et actions basées sur le rôle de l'utilisateur |
| **sessionsForm.cy.ts**    | - Affichage du formulaire de création/édition de session                             |
| **sessionsList.cy.ts**    | - Affichage des sessions et du bouton "Créer" pour les admins                        |