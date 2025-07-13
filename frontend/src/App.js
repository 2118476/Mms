import React from 'react';
import SMSForm from './SMSForm';
import CallForm from './CallForm';
import './App.css';

function App() {
  return (
    <div className="App">
      <h1>📨 SMS & 📞 Call Sender</h1>
      <SMSForm />
      <CallForm />
    </div>
  );
}

export default App;
