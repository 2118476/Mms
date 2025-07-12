import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
import React, { useState } from 'react';
import SMSForm from './components/SMSForm';
import CallForm from './components/CallForm';
import { useTranslation } from 'react-i18next';

function App() {
  const [darkMode, setDarkMode] = useState(false);
  const { i18n } = useTranslation();

  return (
    <div className={darkMode ? 'dark' : ''}>
      <div className="flex items-center justify-center min-h-screen bg-gray-100 dark:bg-gray-900 text-black dark:text-white">
        <div className="w-full max-w-md px-4">
          <div className="flex justify-between items-center mb-6">
            <button
              onClick={() => setDarkMode(!darkMode)}
              className="px-4 py-2 border rounded shadow"
            >
              {darkMode ? '☀ Light Mode' : '🌙 Dark Mode'}
            </button>
            <div className="space-x-2">
              <button onClick={() => i18n.changeLanguage('en')} className="underline">EN</button>
              <button onClick={() => i18n.changeLanguage('am')} className="underline">አማርኛ</button>
            </div>
          </div>

          <h1 className="text-2xl font-bold mb-4 text-center">📨 SMS & 📞 Call Sender</h1>
          <SMSForm />
          <CallForm />
        </div>
      </div>
    </div>
  );
}

export default App;
