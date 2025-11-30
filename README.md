# 🏏 CricForge — Open Source Cricket Team Management & Live Scoring Engine

CricForge is an open-source Spring Boot–based cricket management system that supports:

- Team creation & multi-admin support  
- Player management  
- Match metadata configuration  
- Live scoreboard with real cricket rules  
- Session-based authentication  
- Role-based access control (RBAC)  
- Full ball-by-ball audit logging  

The backend is built under the package:
```com.cricforge.team_management```
It uses a clean architecture inspired by modern open-source projects.

---

## 🚀 Features

### 🔐 Authentication & Authorization
- Secure session cookie–based auth
- `USER`, `APP_ADMIN` global roles
- `TEAM_ADMIN` per-team scoped role via mapping table
- Users can be admin of multiple teams
- Teams can have multiple admins
- APP_ADMIN can promote any user to APP_ADMIN

---

### 🏏 Team Management
- Create teams (up to 15 players)
- Add/Edit/Delete players
- Promote team admins
- View team details with admin list & roster

---

### 🏆 Match Management
- Create match metadata:
  - Teams
  - Max overs
  - Playing XI (or custom list)
- Start match & initialize separate scoreboard

---

### 🎯 Live Ball-by-Ball Scoring Engine
Implements real cricket rules:

- Legal & illegal deliveries (wide/no-ball)
- Extras scoring
- Strike rotation on odd runs & end of over
- Wickets & batsman replacement
- Over progression logic
- Scoreboard updated atomically
- Ball events recorded immutably

---
## 📄 License
MIT License — open for public use, modification and distribution.

---
## 🗺 Roadmap
- Second innings + target chasing
- Bowler statistics
- Batsman scorecard
- Tournament module
- Live WebSocket stream
- Advance UI components
- Docker-compose setup

---
## 🏅 Author

Aravindh Vellaiyan  
Senior Backend Engineer  
Maintainer of CricForge  

---
