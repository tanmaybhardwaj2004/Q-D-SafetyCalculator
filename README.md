## QD Safety Calculator (Android)

An Android app to compute Quantity-Distance (QD) safety separations for explosives storage and operations. Users select Hazard Division (HD), Potential Explosion Site (PES) type and orientation, Exposed Site (ES) type and orientation, traverse conditions, and Net Explosive Quantity (NEQ) to obtain Safe/Personnel/Public/Other QD results depending on HD and context.

### Features
- **Inputs**: HD (1.1/1.2/1.3), PES + orientation, ES + orientation, traverse, NEQ (kg), optional crater check
- **Outputs**: Resolved D-code(s), computed distances, and method notes
- **Built-in tables and formulas** (see `app/src/main/java/com/example/q_dsafetycalculator/QDTables.java`):
  - Table I-A: D-code mapping between PES and ES faces
  - Table I-B: HD 1.1 SIQD distances (D1..D6) with interpolation
  - Table I-C: HD 1.1 PIQD/OQD families (D7..D12, with D8/D10 formulas)
  - Table II (HD 1.2): D1..D4
  - HD 1.3: D1..D5
  - Extras: crater check, barricade factor, underground params, blast wall thickness

### App structure
- `app/src/main/java/com/example/q_dsafetycalculator/`
  - `MainActivity.java`: Launch screen — navigates to input form
  - `InputActivity.java`: Collects inputs, validates NEQ, computes report string
  - `ResultActivity.java`: Displays formatted result
  - `QDTables.java`: QD tables, formulas, interpolation helpers
- `app/src/main/res/layout/`: Activity layouts
- `app/src/main/AndroidManifest.xml`: Declares activities

## Build and run
### Android Studio (recommended)
1. Open `QDSafetyCalculator` in Android Studio.
2. Allow Gradle sync to complete.
3. Select a device/emulator and Run.

### Command line (Gradle)
- Windows:
```bash
.\gradlew.bat assembleDebug
```
- macOS/Linux:
```bash
./gradlew assembleDebug
```
APK output: `app/build/outputs/apk/debug/`

## Usage
1. Launch the app and tap Start.
2. Select HD, PES (with orientation), ES (with orientation), traverse option, and enter NEQ (kg).
3. Optionally enable crater check (HD 1.1, Igloo case).
4. Tap Calculate to see D-code(s), distance(s), and method notes.
   - HD 1.2 and 1.3 show multiple columns (e.g., SIQD, PIQD, OQD/PTRD).

## Calculation notes
- Rounding where specified: e.g., D8 = rint(8·Q^(1/3)), D10 = rint(22.2·Q^(1/3)).
- If NEQ not exactly tabulated, linear interpolation between bounds is used.
- HD 1.1 storage-to-storage: resolve D-code via Table I-A, then compute SIQD via Table I-B.
- HD 1.1 public exposures map to D7/D8/D9/D10/D11/D12 as applicable.
- HD 1.2 reports D1..D4; HD 1.3 reports D1..D5.
- Crater check enforces min distance of `0.5 * Q^(1/3)` for Igloo PES when enabled.

## Requirements
- Android Studio (Hedgehog or newer recommended) or JDK + Gradle (wrappers included).

## Disclaimers
- Embedded values/formulas are in `QDTables.java`. Validate against governing standards before operational use.
- For informational/educational purposes; ensure compliance with local regulations and safety policies.
