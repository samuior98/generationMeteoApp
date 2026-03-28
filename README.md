# 🌤️ Weather App - Spring Boot & JavaScript

Una moderna Web Application per le previsioni meteo che combina un backend robusto in **Java/Spring Boot** con un frontend dinamico, minimale e reattivo. L'app fornisce previsioni dettagliate fino a 5 giorni recuperando dati in tempo reale.

## 🚀 Funzionalità principali

* **Ricerca Minimalista**: Interfaccia senza tasti superflui; basta digitare la città e premere `Invio`.
* **Gestione Automatica Input**: Il campo di ricerca si svuota automaticamente dopo ogni richiesta per una UX fluida.
* **Previsioni a 5 Giorni**: Visualizzazione a card con temperature (Max/Min), icone meteo e descrizioni localizzate.
* **Dark Mode Dinamica**: Switch intelligente tra tema chiaro e scuro. L'icona del tasto cambia dinamicamente (Mostra ☀️ in Dark Mode, 🌙 in Light Mode).
* **Dettagli Tecnici**: Passando il mouse sulle card (Tooltip), è possibile visualizzare la velocità del vento e l'umidità relativa.

## 🛠️ Tech Stack

**Backend:**
* **Java 17+** con **Spring Boot**.
* **Jackson**: Per la deserializzazione avanzata dei JSON tramite `@JsonProperty`.
* **Open-Meteo API**: Utilizzata per i dati previsionali (senza necessità di API Key).
* **Geocoding**: Integrazione per convertire i nomi delle città in coordinate geografiche.

**Frontend:**
* **HTML5 & CSS3**: Layout basato su CSS Grid e variabili per i temi.
* **Vanilla JavaScript**: Gestione asincrona (`fetch`, `async/await`) e gestione eventi da tastiera.

## 📦 Installazione

1.  **Clona il repository:**
    ```bash
    git clone [https://github.com/samuior98/generationMeteoApp.git](https://github.com/samuior98/generationMeteoApp.git)
    ```
2.  **Compila il progetto:**
    ```bash
    mvn clean install
    ```
3.  **Avvia l'applicazione:**
    ```bash
    mvn spring-boot:run
    ```
4.  **Visualizza l'app:**
    Apri il browser su [http://localhost:8080](http://localhost:8080)

## 📂 Struttura del Progetto

```text
src/
├── main/
│   ├── java/com/example/weather/
│   │   ├── model/         # Mapping WeatherResponse e classi interne Daily
│   │   ├── service/       # WeatherService (logica icone e descrizioni)
│   │   └── controller/    # Endpoint API REST
│   └── resources/
│       └── static/        # Frontend (index.html, CSS, JS)
```

## 📝 Note di Sviluppo
L'applicazione implementa una mappatura personalizzata tra i codici numerici di Open-Meteo e le icone grafiche di OpenWeatherMap, garantendo una coerenza visiva tra descrizione testuale e iconografia.

Realizzato con ❤️ come progetto di integrazione Full-Stack Java.


## ⚖️ Licenze e Crediti
I dati meteorologici sono forniti da [Open-Meteo](https://open-meteo.com/) (Licenza [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/)).
Le icone meteo sono fornite da [OpenWeatherMap](https://openweathermap.org/).