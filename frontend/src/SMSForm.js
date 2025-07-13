import React, { useState } from 'react';
import axios from 'axios';

const SMSForm = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [message, setMessage] = useState('');
  const [status, setStatus] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await axios.post(`${process.env.REACT_APP_API_URL}/send-sms`, {
        phoneNumber,
        message
      });
      setStatus(res.data);
    } catch (error) {
      setStatus('❌ Failed to send SMS: ' + error.message);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Send SMS</h2>
      <input
        type="text"
        placeholder="Phone number"
        value={phoneNumber}
        onChange={(e) => setPhoneNumber(e.target.value)}
        required
      />
      <textarea
        placeholder="Message"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        required
      />
      <button type="submit">Send SMS</button>
      <p>{status}</p>
    </form>
  );
};

export default SMSForm;
