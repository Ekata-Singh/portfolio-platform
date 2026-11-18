# Portfolio Platform

A personal portfolio site with a built-in admin editor. Visitors see the portfolio; the owner signs in (the **Admin** button) and edits every section in place: profile, about, education, experience, skills, projects, achievements, publications, certificates, blog posts and contact details.

| Part | Stack |
| --- | --- |
| `frontend/` | React 19, TypeScript, Vite, React Router |
| `backend/` | Spring Boot 3.5 (Java 21), Spring Security + JWT, JPA, Flyway |
| Database | MySQL 8 |
| File uploads | Cloudinary |

---

## Run locally

There's no database to create and no config file to write. Everything, including all your content, is entered through the website's admin editor.

**Option A: one double-click (recommended)**

Requires Java 21+ ([adoptium.net](https://adoptium.net)), Node.js 20+ ([nodejs.org](https://nodejs.org)) and MySQL 8 running.

- **Windows:** double-click `start.bat`
- **macOS / Linux:** run `./start.sh`

The first run asks for your MySQL username and password once and saves them to a git-ignored file. It then creates the `portfolio_platform` database automatically, starts the API and opens the website.

**Option B: Docker (nothing else to install)**

```bash
docker compose up --build
```

**Then:** open http://localhost:5173, click **Admin** (top right), and sign in with **`ekta` / `admin123`**. Edit any section with its ✎ / + buttons.

| | URL |
| --- | --- |
| Website | http://localhost:5173 |
| API | http://localhost:8080 |
| API docs (Swagger) | http://localhost:8080/swagger-ui.html |

Uploaded photos, resumes and certificates are saved in `backend/uploads/`. You don't need a Cloudinary account locally.

> The `admin123` default only applies on your own computer. A production deploy refuses to use defaults and requires `ADMIN_PASSWORD` and `JWT_SECRET`.

---

## Deploy for free

| What | Where | Free tier |
| --- | --- | --- |
| Frontend | [Vercel](https://vercel.com) | Free hobby plan, HTTPS, custom domain |
| API | [Render](https://render.com) | Free Docker web service (sleeps after 15 min idle) |
| MySQL | [Aiven](https://aiven.io/free-mysql-database) | Free MySQL (1 GB) |
| Uploads | [Cloudinary](https://cloudinary.com) | Free 25 credits/month |

### 1. Database (Aiven)
1. Sign up, then create a service: **MySQL → Free plan**.
2. From the service overview, copy the **Host**, **Port**, **User** (`avnadmin`) and **Password**.
3. Your JDBC URL looks like this:
   `jdbc:mysql://<host>:<port>/defaultdb?sslMode=REQUIRED`

### 2. Cloudinary
Sign up. Then copy the **Cloud name**, **API key** and **API secret** from the dashboard.

### 3. API (Render)
1. Push this repo to GitHub.
2. In Render, go to **New → Blueprint**, select the repo, and Render reads `render.yaml`.
3. Fill in the prompted values:
   - `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` come from step 1.
   - The three `CLOUDINARY_*` values come from step 2.
   - `ADMIN_PASSWORD` is the password you will log in with. Choose a strong one. `ADMIN_EMAIL` is your email.
   - For `CORS_ALLOWED_ORIGINS`, enter `https://*.vercel.app` for now. You'll tighten it in step 5.
4. Deploy. When `https://<your-service>.onrender.com/actuator/health` returns `{"status":"UP"}`, the API is live. Flyway creates all the tables, and the admin account is created on that first start.

### 4. Frontend (Vercel)
1. In Vercel, go to **Add New → Project** and import the repo.
2. Set **Root Directory** to `frontend`. Vercel detects Vite automatically.
3. Add the environment variable `VITE_API_ORIGIN` = `https://<your-service>.onrender.com`.
4. Deploy. `vercel.json` already makes deep links like `/blogs/my-post` work.

### 5. Finish
- In Render, set `CORS_ALLOWED_ORIGINS` to your real site URL, e.g. `https://ekta-singh.vercel.app`. You can also add `,https://*.vercel.app` to keep preview deploys working.
- Open the site, click **Admin**, sign in, and fill in your content.
- *Optional:* to stop the ~1 minute cold start after the API has been idle, add the repository variable `BACKEND_URL` (Settings → Secrets and variables → Actions → Variables). The `Keep API warm` workflow then pings the API every 10 minutes.

### Moving existing local data to production (optional)
```bash
mysqldump -uroot -p portfolio_platform > dump.sql
```
```bash
mysql -h <aiven-host> -P <port> -u avnadmin -p --ssl-mode=REQUIRED defaultdb < dump.sql
```
Files uploaded locally are stored as paths such as `profile/123_photo.jpg` in `backend/uploads/`. Those don't exist on the server, so re-upload them from the admin editor once the site is live.

### Forgot the admin password?
Set `ADMIN_RESET_PASSWORD=true` and a new `ADMIN_PASSWORD` in Render and redeploy once. Then remove `ADMIN_RESET_PASSWORD`.

---

## Environment variables (backend)

| Variable | Purpose | Default |
| --- | --- | --- |
| `DB_URL` / `DB_USERNAME` / `DB_PASSWORD` | MySQL connection | local MySQL (database auto-created), `root` |
| `JWT_SECRET` | Signs login tokens (≥ 32 chars; required in prod) | dev-only value |
| `JWT_EXPIRATION` | Token lifetime in ms | `86400000` (24 h) |
| `CLOUDINARY_CLOUD_NAME` / `_API_KEY` / `_API_SECRET` | File uploads (recommended in production) | empty → files saved to `UPLOAD_DIR` |
| `UPLOAD_DIR` | Local upload folder | `uploads` |
| `CORS_ALLOWED_ORIGINS` | Comma-separated origins, wildcards allowed | `http://localhost:5173` |
| `ADMIN_USERNAME` / `ADMIN_EMAIL` / `ADMIN_PASSWORD` | Admin account created on first start | `ekta` / — / `admin123` locally only |
| `ADMIN_RESET_PASSWORD` | `true` = overwrite the stored admin password | `false` |
| `SPRING_PROFILES_ACTIVE` | `local` on your computer, `prod` when hosted (set by the Dockerfile) | `local` |
| `JPA_SHOW_SQL` | Log SQL statements | `false` |
