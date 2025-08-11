# 💱 Conversor de Moneda - Java

Un sencillo conversor de divisas en Java que utiliza la API [ExchangeRate-API](https://www.exchangerate-api.com/) para obtener tasas de conversión en tiempo real.  
El usuario ingresa los países de origen y destino, y el programa devuelve el tipo de cambio actual.

---

## 🚀 Características

- Conversión de divisas usando **ExchangeRate API**.
- Mapeo de países a sus códigos ISO de moneda.
- Manejo de errores para entradas inválidas.
- Arquitectura organizada en **controladores**, **servicios** y **utilidades**.
- Implementado en **Java 17** con **Gson** para manejo de JSON.

---

## 📂 Estructura del Proyecto

src/
├── contoler/
│ ├── CurrencyControler.java
│ └── Constants.java
├── service/
│ ├── Interface.java
│ ├── CurrencyService.java
│ └── util/
│ └── CountryToCurrencyMap.java
└── CurrencyConverter.java


---

## 🛠 Requisitos

- **Java 17** o superior.
- **Maven** para la gestión de dependencias.
- Conexión a internet (para llamadas a la API).
- Una cuenta gratuita en [ExchangeRate API](https://www.exchangerate-api.com/) para obtener tu **API Key**.

---

## 📦 Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/conversor-moneda.git
   cd conversor-moneda

Agregar tu API Key en Constants.java
public static final String API_KEY = "TU_API_KEY_AQUI";
