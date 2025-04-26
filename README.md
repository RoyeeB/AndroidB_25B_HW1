# 🚀 SmokeApp & WaterApp - Android Project

A dual-application Android project for tracking cigarette consumption and water intake, using a shared core module.

---

## 🏛️ Common Module (`common/`)

The shared `common` module includes:

- **ActivityPanelBase.java** – A reusable base activity managing UI counter logic.
- **AppPrefsHelper.java** – Helper class for saving/retrieving data via `SharedPreferences`.
- **PrefsConstants.java** – Constants for preference file names and keys.
- **Shared Layouts** – `activity_register.xml` and `activity_panel_base.xml` for unified UI across apps.

This module reduces code duplication and maintains a consistent experience.

---

## 🚬 SmokeApp

**Purpose:**  
Track daily cigarette consumption, monitor goals, and reduce smoking over time.

### Features:
- User registration: name, starting cigarette count, maximum daily goal.
- Save daily cigarette usage.
- Calculate weekly and monthly averages.
- Detect daily goal violations.
- Clean and intuitive UI with plus/minus buttons.

---

## 💧 WaterApp

**Purpose:**  
Track and encourage daily water intake based on weight and daily steps.

### Features:
- User registration: name, weight (kg), and average daily steps.
- Calculate recommended daily cups of water.
- Save daily water intake.
- Weekly and monthly average tracking.
- Detect days under the recommended water intake.

---

## 📹 Demo Videos

| App        | Video Link |
|------------|------------|
| SmokeApp   | https://drive.google.com/file/d/1IK5I_8e5Ot9FP72_7ZQ26gC_rgsNzzl1/view?usp=drive_link |
| WaterApp   | https://drive.google.com/file/d/1PnUkNjFnZ_aN8D1WxLTgvImh-5wacysI/view?usp=drive_link |

---

## ⚙️ Technologies Used

- Android Studio (Latest)
- Java (Android SDK)
- Material Components
- Jetpack Libraries
- SharedPreferences

---

## 🔒 Data Storage

All user data is stored **locally** on the device using Android's **SharedPreferences**.  
There is **no cloud storage** or server communication.  
Data is completely private and offline.  
_**Note:** Deleting the app will erase all stored data._

---

## 🛠️ How to Run

1. Clone or download the repository.
2. Open the project in **Android Studio**.
3. Choose either `smokeapp` or `waterapp` to run.
4. Build and run on an emulator or real device.

---

## ✨ Future Enhancements

- Adding visual statistics (charts/graphs).
- Push notifications/reminders for daily tracking.
- Syncing data to a cloud service (e.g., Firebase).
- Multi-language support.

---

# 🎯 Summary

This project demonstrates a clean modular Android architecture, shared components, and strong offline capabilities.  
It serves as a foundation ready to scale with more advanced features.

---


