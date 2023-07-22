# Transcript Management System

## Software Requirements

- Intellij
- Docker
- Node.js

## Running the application

from within the root folder of the project do the follow

1. run the bat file in bat/kill_processes.bat
2. run `npm install` from cmd
3. run `docker compose -f src/main/docker/app.yml up -d` from cmd
4. run the spring application from intellij
5. run `npm start` from cmd
6. go to localhost:9080 and enter admin/admin to login then go
   -> Select jhipster realm -> real settings -> action -> partial import -> project folder -> src/main/docker/realm-config/realm-export.json
7. go to identity provider -> microsoft -> add client secret:
   iy58Q~.ZRs4RMq5nzmnVtfErY1~HqAYuBuwuobKZ
8. go to localhost:9000 to test the application
