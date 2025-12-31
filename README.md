# Bank Account Service - Event Sourcing et CQRS

## Description

Ce projet implémente un service de gestion de comptes bancaires utilisant les patterns **Event Sourcing** et **CQRS** (Command Query Responsibility Segregation) avec **Axon Framework**.

L'architecture CQRS sépare les opérations de lecture (Query) et d'écriture (Command), permettant une meilleure scalabilité et performance. L'Event Sourcing permet de conserver l'historique complet de toutes les modifications sous forme d'événements.

## Technologies utilisées

- **Spring Boot 3.5.9** - Framework applicatif
- **Axon Framework 4.10.3** - Framework Event Sourcing et CQRS
- **PostgreSQL** - Base de données principale
- **H2 Database** - Base de données en mémoire pour le service analytics
- **Docker Compose** - Orchestration des conteneurs
- **Lombok** - Réduction du code boilerplate
- **SpringDoc OpenAPI** - Documentation API

## Architecture du projet

```
Bank-Account-Service/
├── src/main/java/ma/enset/bankaccountservice/
│   ├── commands/
│   │   ├── aggregate/          # Agrégat Account
│   │   └── controllers/        # Contrôleur des commandes
│   ├── commonapi/
│   │   ├── commands/           # Classes de commandes (Create, Credit, Debit)
│   │   ├── dto/                # Objets de transfert de données
│   │   ├── enums/              # Énumérations (AccountStatus)
│   │   └── events/             # Événements (AccountCreated, etc.)
│   └── query/
│       ├── controllers/        # Contrôleur des requêtes
│       ├── entities/           # Entités JPA (Account, Transaction)
│       ├── enums/              # Types de transactions
│       ├── queries/            # Classes de requêtes
│       ├── repositories/       # Repositories JPA
│       └── service/            # Gestionnaires d'événements et requêtes
├── analytics-service/          # Microservice d'analyse
└── docker-compose.yml          # Configuration Docker
```

## Prérequis

- Java 21 ou supérieur
- Maven 3.8+
- Docker et Docker Compose

## Démarrage rapide

### 1. Lancer l'infrastructure avec Docker

```bash
docker-compose up -d
```

Cela démarre:
- **Axon Server** (ports 8024, 8124)
- **PostgreSQL** (port 5432)
- **pgAdmin** (port 8088)

### 2. Compiler le projet principal

```bash
mvn clean install
```

### 3. Lancer le service de comptes

```bash
mvn spring-boot:run
```

Le service démarre sur le port **8085**.

### 4. Lancer le service d'analytics (optionnel)

```bash
cd analytics-service
mvn spring-boot:run
```

Le service analytics démarre sur le port **8084**.

## Endpoints API

### Commandes (Commands)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/commands/account/create` | Créer un compte |
| POST | `/commands/account/credit` | Créditer un compte |
| POST | `/commands/account/debit` | Débiter un compte |
| GET | `/commands/account/eventStore/{accountId}` | Consulter l'historique des événements |

### Requêtes (Queries)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/query/accounts/all` | Liste de tous les comptes |

### Analytics (Service Analytics)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/query/accountAnalytics` | Toutes les analyses |
| GET | `/query/accountAnalytics/{accountId}` | Analyse d'un compte |
| GET | `/query/accountAnalytics/{accountId}/watch` | Suivi en temps réel (SSE) |


- **Axon Server Dashboard**: http://localhost:8024
- **pgAdmin**: http://localhost:8088 (admin@bank.com / admin123)

## Concepts clés

### Event Sourcing
L'état de chaque compte est reconstruit à partir de la séquence d'événements. Cela permet:
- Audit complet de l'historique
- Possibilité de rejouer les événements
- Facilité de débogage

### CQRS
Séparation des modèles de lecture et d'écriture:
- **Command Side**: Gère les modifications (crédit, débit)
- **Query Side**: Gère les lectures (consultation des comptes)

