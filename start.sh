#!/usr/bin/env bash
# Starts the portfolio on this computer:
#   API      -> http://localhost:8080
#   Website  -> http://localhost:5173   (Admin login: ekta / admin123)
# Needs: Java 21+, Node.js 20+, and MySQL running.
set -euo pipefail
cd "$(dirname "$0")"

command -v java >/dev/null || { echo "[!] Java not found. Install Java 21+ from https://adoptium.net"; exit 1; }
command -v npm  >/dev/null || { echo "[!] Node.js not found. Install it from https://nodejs.org"; exit 1; }

LOCALCFG=backend/src/main/resources/application-local.properties
if [ ! -f "$LOCALCFG" ]; then
  echo
  echo "First-time setup: enter your MySQL login. The database is created automatically."
  read -rp "MySQL username [root]: " DBUSER
  read -rsp "MySQL password: " DBPASS; echo
  {
    echo "# Local settings - this file is NOT committed to git."
    echo "spring.datasource.username=${DBUSER:-root}"
    echo "spring.datasource.password=${DBPASS}"
  } > "$LOCALCFG"
  echo "Saved to $LOCALCFG (edit or delete it to change)"
fi

echo "Starting API (log: backend/api.log)..."
(cd backend && chmod +x mvnw && ./mvnw -q spring-boot:run > api.log 2>&1) &
API_PID=$!
trap 'echo; echo "Stopping..."; kill $API_PID 2>/dev/null; pkill -P $API_PID 2>/dev/null || true' EXIT

cd frontend
[ -d node_modules ] || { echo "Installing website dependencies (first run only)..."; npm install; }

echo
echo "Website: http://localhost:5173   |   Admin login: ekta / admin123"
echo "The API takes ~30s to start the first time. Press Ctrl+C to stop everything."
echo
npm run dev
