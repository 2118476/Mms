import React, { useState } from 'react';
import axios from 'axios';

const CallForm = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [status, setStatus] = useState('');

  const handleCall = async (e) => {
    e.preventDefault();

    try {
      const res = await axios.post(`${process.env.REACT_APP_API_URL}/make-call`, {
        phoneNumber
      });
      setStatus(res.data);
    } catch (error) {
      setStatus('❌ Call failed: ' + error.message);
    }
  };

  return (
    <form onSubmit={handleCall}>
      <h2>Make Call</h2>
      <input
        type="text"
        placeholder="Phone number"
        value={phoneNumber}
        onChange={(e) => setPhoneNumber(e.target.value)}
        required
      />
      <button type="submit">Call</button>
      <p>{status}</p>
    </form>
  );
};

export default CallForm;
