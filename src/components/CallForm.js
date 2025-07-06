import React, { useState } from 'react';
import axios from 'axios';
import PhoneInput from 'react-phone-input-2';
import 'react-phone-input-2/lib/style.css';
import { useTranslation } from 'react-i18next';

const CallForm = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [message, setMessage] = useState('');
  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);
  const { t } = useTranslation();

  const handleCall = async (e) => {
    e.preventDefault();
    const fullNumber = '+' + phoneNumber;
    const phoneRegex = /^\+\d{7,15}$/;

    if (!phoneRegex.test(fullNumber)) {
      setStatus('❌ Invalid phone number format.');
      return;
    }

    setLoading(true);
    try {
      const res = await axios.post(`${process.env.REACT_APP_API_URL}/make-call`, {
        phoneNumber: fullNumber,
        message
      });
      setStatus('📞 ' + res.data);
    } catch (error) {
      setStatus('❌ Call failed. ' + error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto bg-white dark:bg-gray-800 p-6 mt-10 rounded-2xl shadow-xl">
      {status && (
        <div className={`p-3 mb-4 rounded text-sm font-medium text-center ${status.startsWith('📞') ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
          {status}
        </div>
      )}

      <form onSubmit={handleCall} className="space-y-6">
        <div>
          <label className="block mb-1 font-semibold text-sm">{t('phone_number')}</label>
          <PhoneInput
            inputProps={{ name: 'phone', required: true }}
            country={'gb'}
            value={phoneNumber}
            onChange={setPhoneNumber}
            inputClass="!w-full !border !rounded-md !py-2 !px-3"
          />
        </div>

        <div>
          <label className="block mb-1 font-semibold text-sm">{t('message')}</label>
          <textarea
            maxLength={160}
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            className="w-full border rounded-lg p-3 text-sm resize-none dark:bg-gray-700 dark:text-white"
            rows={3}
            required
          ></textarea>
        </div>

        <button
          type="submit"
          disabled={loading}
          className="w-full py-2 px-4 bg-green-600 hover:bg-green-700 active:scale-95 transition text-white font-semibold rounded-lg shadow-md"
        >
          {loading ? 'Calling...' : 'Make Call'}
        </button>
      </form>
    </div>
  );
};

export default CallForm;
