import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

const resources = {
  en: {
    translation: {
      send_sms: "Send SMS",
      phone_number: "Phone Number",
      message: "Message",
      submit: "Send",
      history: "Message History"
    }
  },
  am: {
    translation: {
      send_sms: "መልእክት ላክ",
      phone_number: "ስልክ ቁጥር",
      message: "መልእክት",
      submit: "ላክ",
      history: "የተላኩ መልእክቶች"
    }
  }
};

i18n.use(initReactI18next).init({
  resources,
  lng: 'en',
  fallbackLng: 'en',
  interpolation: {
    escapeValue: false
  }
});

export default i18n;
