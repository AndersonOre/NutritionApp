# NutritionApp

> App de nutrición desarrollada en una semana, publicada como demostración técnica y exploración de arquitectura y tecnologias modernas.

---

## 🚀 Características principales

- **Onboarding** guiado en tres pantallas (pantalla 1, 2, 3)
- **Inicio (Home)**: listas de recetas y alimentos
- **Favoritos**
- **Registro / Login** con **Firebase Authentication**
- **Lista de alimentos** con calory tracking
- **Detalles del alimento**: información nutricional (macros, micronutrientes)
- **Lista de recetas** y **detalles de receta**

---

## 🧱 Arquitectura & Tecnologías

- **MVVM + Clean Architecture**: separación clara en capas (presentación, dominio, datos).
- **Jetpack Compose**: UI declarativa moderna para pantallas y listas.
- **Room**: base de datos local para favoritos y almacenamiento offline.
- **Firebase Authentication**: registro e inicio de sesión.
- **Firebase Analytics**: La app registra eventos personalizados para identificar las recetas más buscadas, los alimentos más consultados y las pantallas más visitadas.
- **Hilt**: inyección de dependencias para ViewModels, repositorios y data sources.
- **Kotlin Coroutines + Flow**: para operaciones asíncronas y reactividad.
- **Retrofit**: para obtener información de una API externa nutricional y recetas.

---

## 📸 Capturas de pantalla

| Pantalla | Descripción |
|----------|-------------|
| Onboarding 1 | Introducción a la app |
| Onboarding 2 | Beneficios principales |
| Onboarding 3 | Empecé a usar |
| Home | Vista principal con recetas y alimentos |
| Favorites | Lista de favoritos guardados |
| Login / Register | Pantallas de autenticación |
| Food List | Buscar alimentos para calorías |
| Food Details | Información detallada del alimento |
| Recipe List / Details | Recetas disponibles y su información |

```markdown
![Onboarding 1](images/onboarding_screen_1.png)  
![Onboarding 2](images/onboarding_screen_2.png)  
![Onboarding 3](images/onboarding_screen_3.png)  
![Home](images/home_screen.png)  
![Favorites](images/favorites_screen.png)  
![Login](images/login_screen.png)  
![Register](images/register_screen.png)  
![Food List](images/food_list_screen.png)  
![Food Details](images/food_details_screen.png)  
![Recipe List](images/recipe_list_screen.png)  
![Recipe Details](images/recipe_details_screen.png)  
