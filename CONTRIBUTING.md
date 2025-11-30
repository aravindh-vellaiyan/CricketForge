# Contributing to CricForge

Thank you for considering contributing to **CricForge**, an open-source–style cricket team management and live scoring engine built with Spring Boot and PostgreSQL.

This project follows a clean architecture with clear layering, DTO–Mapper–Service separation, and strict domain boundaries.  
To maintain consistency and quality, please follow the guidelines below.

---

## 🧱 Project Structure Overview
```
src/main/java/com/cricforge/team_management
├── auth/ → signup, login, session validation
├── controller/ → REST APIs (Team, Admin, Match, Scoreboard)
├── domain/ → Entities
├── dto/ → Request/Response models
├── mapper/ → Entity ↔ DTO converters
├── repository/ → JPA repositories
├── security/ → Session + RBAC filter
└── service/ → Business logic
```

If you're adding new logic, make sure it belongs to the **correct layer**.

---

## 🔧 Development Setup

### 1. Fork & Clone

```
git clone https://github.com/<your-username>/cricforge.git
cd cricforge
```

## 2. Database Setup (PostgreSQL)

Create a database:
```
CREATE DATABASE cricketdb;
```

Configure credentials in:
```
src/main/resources/application.yaml
```

## 3. Run the application
```
mvn spring-boot:run
```

---
## 🧪 Tests

- Unit tests should follow the AAA pattern (Arrange–Act–Assert).
- Service methods involving transactions must have test coverage.
- Avoid mocking repositories excessively; use slice tests where appropriate.

---
## 📦 Coding Standards

To keep the codebase clean and maintainable:

### ✔ Package-Level Discipline

- Controller → MUST NOT contain business logic
- Service → MUST contain business logic
- Mapper → MUST convert DTO ↔ Entity
- Domain → Pure entities, no service logic

### ✔ Naming Conventions

- DTO: `CreateTeamRequest`, `MatchResponse`
- Mapper: `TeamMapper`, `MatchMapper`
- Services: `TeamService`, `ScoreBoardService`
- Repositories: `TeamRepository`

### ✔ Java Style Rules

- Use constructor injection over field injection wherever possible.
- Avoid static state except inside pure mappers.
- Never return JPA entities directly from controllers.
- Keep transaction boundaries in the service layer only.

---
## 🔐 Auth & RBAC Contributions

If modifying authentication or authorization:
- Ensure session validation remains stateless and filter-based.
- RBAC logic must remain centralized in AuthorizationService.
- Never hardcode roles in controllers.

---
## 🌱 Adding New Features

If you're introducing a new feature:

1. Discuss the feature via GitHub Issue.
2. Keep PRs focused — one feature per PR.
3. Add DTO → Mapper → Service → Controller flow.
4. Update README.md if applicable.
5. Add tests for new service logic.

---
## 🔀 Branching Strategy

Use the following branch naming:
```
feature__<name>
bugfix__<name>
refactor__<name>
docs__<name>
```
Submit PRs from your fork.

---
## 📝 Commit Message Format

Use meaningful commit messages:
```
feat: add scoreboard initialization
fix: correct strike rotation on odd runs
refactor: extract RBAC logic to AuthorizationService
docs: update API documentation
```

---
## 🔍 Pull Request Checklist

- Before submitting a PR:
- Code compiles successfully
- All existing tests pass
- Added new tests (if applicable)
- No business logic inside controllers
- No entity exposure in API
- DTOs validated
- Mapper updated where necessary
- README updated (if feature changes API)
- RBAC rules enforced when needed

---
## 🧑‍💻 Code of Conduct

This project follows a simple rule:

! “Be respectful, keep discussions technical, and produce maintainable code.”

---
## 📞 Support

For questions, open an Issue.

---
Thank you for contributing to CricForge!  
Your improvements make the project stronger, cleaner, and more useful for the community.  