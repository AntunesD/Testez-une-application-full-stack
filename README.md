# Application Yoga

Cette application permet de gérer des sessions de yoga, avec un système d'authentification et d'administration.

## Prérequis

- Java 8
- Node.js et npm
- MySQL
- Maven
- Angular CLI

## Installation

### Base de données

1. Créez une base de données MySQL
2. Exécutez le script SQL situé dans `ressources/sql/script.sql`

### Backend (Spring Boot)

1. Naviguez vers le dossier `back`

```
cd back
```

2. Installez les dépendances avec Maven

```
mvn clean install
```

3. Exécutez l'application avec Maven

```
mvn spring-boot:run
```

Le backend sera accessible sur `http://localhost:8080`

### Frontend (Angular)

1. Naviguez vers le dossier `front`

```
cd front
```

2. Installez les dépendances avec npm

```
npm install
```

3. Exécutez l'application avec Angular CLI

```
ng serve
```

L'application sera accessible sur `http://localhost:4200`

## Tests

### Résumés des Tests

- [Récapitulatif des Tests Backend](test_summary/back_summary.md)
- [Récapitulatif des Tests Cypress](test_summary/cypress_summary.md)
- [Récapitulatif des Tests de Fonctionnalités avec Jest](test_summary/jest_summary.md)

### Tests Backend

Pour lancer les tests unitaires et générer le rapport de couverture Jacoco :

```
mvn clean test
```

Le rapport de couverture sera généré dans `back/target/site/jacoco/index.html`

### Tests Frontend

Pour lancer les tests unitaires avec Jest et générer le rapport de couverture :

```
cd front
```

```
npm run test
```

Le rapport de couverture sera disponible dans `front/coverage/lcov-report/index.html`

### Tests End-to-End

Pour lancer les tests E2E avec Cypress :

```
cd front
```

```
npm run e2e
```

Pour générer le rapport de couverture E2E :

```
npm run e2e:coverage
```

Le rapport sera disponible dans `front/coverage/lcov-report/index.html`

## Utilisateur par défaut

L'application est livrée avec un utilisateur administrateur par défaut :

- Email : yoga@studio.com
- Mot de passe : test!1234

## Documentation API

Une collection Postman est disponible dans `ressources/postman/yoga.postman_collection.json`

## Fonctionnalités

- Authentification (login/register)
- Gestion des sessions de yoga
- Gestion des professeurs
- Gestion des utilisateurs
- Interface d'administration
