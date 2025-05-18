# API Automation with Rest-Assured and TestNG

## Setup Instructions

1. Clone Repository
git clone https://github.com/novanadhagara/api-automation.git
cd api-automation

2. Install Dependencies
mvn clean install

3. Jalankan Test
mvn test

## Struktur Test
- Semua test ditulis di `src/test/java/tests/ApiTests.java`
- Login dilakukan otomatis di awal dan token digunakan untuk seluruh request
- Konfigurasi test diatur dalam file `testng.xml`
