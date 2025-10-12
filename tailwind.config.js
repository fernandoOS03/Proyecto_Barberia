/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/templates/**/*.html",
    "./src/main/resources/templates/**/*.js",
    "./src/main/java/**/*.java"
  ],
  theme: {
    extend: {
      colors: {
        'background-primary': '#ffffff',
        'background-secondary': '#f8fafc',
        'text-primary': '#1f2937',
        'brand-accent': '#6a42c9',
      },
      fontFamily: {
        'serif': ['Georgia', 'Cambria', 'Times New Roman', 'Times', 'serif'],
        'sans': ['Inter', 'system-ui', 'sans-serif'],
      }
    },
  },
  plugins: [],
}