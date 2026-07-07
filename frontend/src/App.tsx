import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import { AuthProvider } from './context/AuthProvider';
import { ThemeProvider } from './context/ThemeProvider';
import { PortfolioProvider } from './context/PortfolioProvider';
import { PublicLayout } from './layouts/PublicLayout';
import { Login } from './pages/Login';
import { Home } from './pages/Home';
import { Blogs } from './pages/Blogs';
import { BlogDetail } from './pages/BlogDetail';
import { Projects } from './pages/Projects';
import { ProjectDetail } from './pages/ProjectDetail';
import { Experience } from './pages/Experience';

function App() {
  return (
    <ThemeProvider>
      <AuthProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/login" element={<Login />} />

            <Route
              element={
                <PortfolioProvider>
                  <PublicLayout />
                </PortfolioProvider>
              }
            >
              <Route path="/" element={<Home />} />
              <Route path="/experience" element={<Experience />} />
              <Route path="/projects" element={<Projects />} />
              <Route path="/projects/:id" element={<ProjectDetail />} />
              <Route path="/blogs" element={<Blogs />} />
              <Route path="/blogs/:slug" element={<BlogDetail />} />
            </Route>
          </Routes>
        </BrowserRouter>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;
