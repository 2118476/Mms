import React, { useState, useEffect } from 'react';
import axios from 'axios';
import PhoneInput from 'react-phone-input-2';
import 'react-phone-input-2/lib/style.css';
import { useTranslation } from 'react-i18next';

const SMSForm = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [message, setMessage] = useState('');
  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);
  const { t } = useTranslation();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const phoneRegex = /^\+\d{7,15}$/;
    if (!phoneRegex.test('+' + phoneNumber)) {
      setStatus('❌ Invalid phone number format.');
      return;
    }

    setLoading(true);
    try {
      const res = await axios.post(`${process.env.REACT_APP_API_URL}/send-sms`, {
        phoneNumber: '+' + phoneNumber,
        message
      });
      setStatus('✅ ' + res.data);
    } catch (error) {
      setStatus('❌ Failed to send SMS. ' + error.message);
    } finally {
      setLoading(false);
    }
  };

 useEffect(() => {
  const stored = JSON.parse(localStorage.getItem("history")) || [];
  if (status && status.startsWith('✅')) {
    const newEntry = { phoneNumber: '+' + phoneNumber, message, time: new Date().toLocaleString() };
    const updated = [newEntry, ...stored];
    localStorage.setItem("history", JSON.stringify(updated));
  }
}, [status, phoneNumber, message]); // 👈 added dependencies


  const history = JSON.parse(localStorage.getItem("history")) || [];

  return (
    <div className="max-w-md mx-auto bg-white dark:bg-gray-800 p-6 rounded-2xl shadow-xl">
      {status && (
        <div className={`p-3 mb-4 rounded text-sm font-medium text-center ${status.startsWith('✅') ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
          {status}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-6">
        <div>
          <label htmlFor="phone" className="block mb-1 font-semibold text-sm">{t('phone_number')}</label>
          <PhoneInput
            inputProps={{ name: 'phone', required: true, 'aria-label': 'Phone number' }}
            country={'gb'}
            value={phoneNumber}
            onChange={setPhoneNumber}
            inputClass="!w-full !border !rounded-md !py-2 !px-3"
          />
        </div>

        <div>
          <label htmlFor="message" className="block mb-1 font-semibold text-sm">{t('message')}</label>
          <textarea
            id="message"
            maxLength={160}
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            className="w-full border rounded-lg p-3 text-sm resize-none dark:bg-gray-700 dark:text-white"
            rows={4}
            required
          ></textarea>
          <p className="text-xs text-gray-500 dark:text-gray-300 mt-1 text-right">{message.length}/160</p>
        </div>

        <button
          type="submit"
          disabled={loading}
          className="w-full py-2 px-4 bg-blue-600 hover:bg-blue-700 active:scale-95 transition text-white font-semibold rounded-lg shadow-md"
        >
          {loading ? 'Sending...' : t('submit')}
        </button>
      </form>

      {history.length > 0 && (
        <div className="mt-10">
          <h2 className="text-lg font-bold mb-2 flex items-center">
            📝 {t('history')}
          </h2>
          <ul className="space-y-2 text-sm max-h-60 overflow-y-auto pr-1">
            {history.map((item, index) => (
              <li key={index} className="border-b pb-2 text-gray-800 dark:text-gray-300">
                <strong>{item.time}</strong> — {item.phoneNumber}: {item.message}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default SMSForm;
