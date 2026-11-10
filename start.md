1. Menyalakan Emulator Tablet (Jalankan sekali saja di tab terminal terpisah)

emulator -avd TabletDev

2. Build, Install, dan Langsung Buka Aplikasi (Gunakan ini setiap habis Save kode di Cursor/VS Code)

./gradlew installDebug && adb shell am start -n com.bjoey.app/.MainActivity

3. Melihat Console Log / Error Crash Secara Live (Setara terminal console.log di Expo)
adb logcat | grep -i "com.bjoey.app"

4. Melihat Daftar Device / Emulator yang Sedang Terhubung
adb devices
