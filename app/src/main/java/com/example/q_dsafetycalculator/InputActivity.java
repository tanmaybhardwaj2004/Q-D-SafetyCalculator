package com.example.q_dsafetycalculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {

    private Spinner spinnerHazard;
    private Spinner spinnerPes;
    private Spinner spinnerPesOrientation;
    private Spinner spinnerEsType;
    private Spinner spinnerEsOrientation;
    private Spinner spinnerTraverse;
    private EditText editNeq;
    private CheckBox checkboxCrater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        spinnerHazard = findViewById(R.id.spinner_hazard_division);
        spinnerPes = findViewById(R.id.spinner_pes_type);
        spinnerPesOrientation = findViewById(R.id.spinner_pes_orientation);
        spinnerEsType = findViewById(R.id.spinner_es_type);
        spinnerEsOrientation = findViewById(R.id.spinner_es_orientation);
        spinnerTraverse = findViewById(R.id.spinner_traverse);
        editNeq = findViewById(R.id.edittext_neq);
        checkboxCrater = findViewById(R.id.checkbox_crater_check);

        Button calcBtn = findViewById(R.id.button_calculate_siqd);
        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCalculate();
            }
        });
    }

    private void onCalculate() {
        String hazard = spinnerHazard.getSelectedItem().toString();
        String pes = spinnerPes.getSelectedItem().toString();
        String pesOrientation = spinnerPesOrientation.getSelectedItem().toString();
        String esType = spinnerEsType.getSelectedItem().toString();
        String esOrientation = spinnerEsOrientation.getSelectedItem().toString();
        String traverseSel = spinnerTraverse.getSelectedItem().toString();
        String neqStr = editNeq.getText().toString();
        boolean craterCheck = checkboxCrater.isChecked();

        if (TextUtils.isEmpty(neqStr)) {
            editNeq.setError("Enter NEQ");
            return;
        }
        double neq;
        try {
            neq = Double.parseDouble(neqStr);
        } catch (NumberFormatException e) {
            editNeq.setError("Invalid number");
            return;
        }

        String details = computeDetailedReport(hazard, pes, pesOrientation, esType, esOrientation, traverseSel, neq, craterCheck);

        Intent intent = new Intent(InputActivity.this, ResultActivity.class);
        intent.putExtra("result_text", details);
        startActivity(intent);
    }

    private String computeDetailedReport(String hazard, String pes, String pesOrientation, String esType, String esOrientation, String traverseSel, double neq, boolean craterCheck) {
        StringBuilder sb = new StringBuilder();
        sb.append("HD: ").append(hazard).append("\n");
        sb.append("PES: ").append(pes).append(" (").append(pesOrientation).append(")\n");
        sb.append("ES: ").append(esType).append(" (").append(esOrientation).append(")\n");
        sb.append("Traverse: ").append(traverseSel).append("\n");
        sb.append("NEQ: ").append(neq).append(" kg\n\n");

        if ("1.1".equals(hazard)) {
            if (isStorageToStorage(esType)) {
                String esFaceKey = mapESKey(esType, esOrientation);
                java.util.Map<String, String> row = QDTables.TABLE_IA.get(pes);
                String dCode = row != null ? row.get(esFaceKey) : null;
                if (dCode == null && "Above Ground Magazine".equals(esType) && row != null) {
                    dCode = row.get(esOrientation.contains("Traversed") ? "Above Ground Magazine T" : "Above Ground Magazine UT");
                }
                if (dCode == null) {
                    sb.append("Could not resolve D-code for Table I-A.\n");
                } else {
                    double value = QDTables.getHD11_SIQD(dCode, neq);
                    String method = methodNote(QDTables.HD11_SIQD, neq);
                    if (craterCheck && "Igloo".equals(pes)) {
                        double minCrater = 0.5 * QDTables.q13(neq);
                        if (value < minCrater) {
                            value = minCrater;
                            method += "; crater check applied (max with 0.5*Q^(1/3))";
                        }
                    }
                    sb.append("Resolved D-code(s): ").append(dCode).append("\n");
                    sb.append("SIQD: ").append(round(value)).append(" m\n");
                    sb.append("Method: ").append(method).append("\n");
                }
            } else {
                String dCode = mapHD11PublicDCode(esType, traverseSel);
                double value;
                String method;
                if ("D8".equals(dCode)) {
                    value = QDTables.T1C_D8_formula(neq);
                    method = "Formula 8*Q^(1/3) with rint()";
                } else if ("D10".equals(dCode)) {
                    value = QDTables.T1C_D10_formula(neq);
                    method = "Formula 22.2*Q^(1/3) with rint()";
                } else if ("D7".equals(dCode)) {
                    value = lookupOrInterpolate(QDTables.T1C_D7, neq);
                    method = methodNote(QDTables.T1C_D7, neq);
                } else if ("D9".equals(dCode)) {
                    value = lookupOrInterpolate(QDTables.T1C_D9, neq);
                    method = methodNote(QDTables.T1C_D9, neq);
                } else if ("D11".equals(dCode)) {
                    value = lookupOrInterpolate(QDTables.T1C_D11, neq);
                    method = methodNote(QDTables.T1C_D11, neq);
                } else {
                    value = lookupOrInterpolate(QDTables.T1C_D12, neq);
                    method = methodNote(QDTables.T1C_D12, neq);
                }
                sb.append("Resolved D-code(s): ").append(dCode).append("\n");
                String label = ("D11".equals(dCode) || "D12".equals(dCode)) ? "OQD" : "PIQD";
                sb.append(label).append(": ").append(round(value)).append(" m\n");
                sb.append("Method: ").append(method).append("\n");
            }
        } else if ("1.2".equals(hazard)) {
            // Report all four columns for audit clarity
            sb.append("Resolved D-code(s): D1 (SIQD), D2 (PIQD), D3 (OQD PTRD), D4 (OQD Dwell)\n");
            String method = methodNote(QDTables.HD12, neq);
            double d1 = QDTables.getHD12("D1", neq);
            double d2 = QDTables.getHD12("D2", neq);
            double d3 = QDTables.getHD12("D3", neq);
            double d4 = QDTables.getHD12("D4", neq);
            sb.append("SIQD (D1): ").append(round(d1)).append(" m\n");
            sb.append("PIQD (D2): ").append(round(d2)).append(" m\n");
            sb.append("OQD PTRD (D3): ").append(round(d3)).append(" m\n");
            sb.append("OQD Dwell (D4): ").append(round(d4)).append(" m\n");
            sb.append("Method: ").append(method).append("\n");
        } else if ("1.3".equals(hazard)) {
            // Report all five columns for audit clarity
            sb.append("Resolved D-code(s): D1 (SIQD TU), D2 (SIQD Bunker), D3 (PIQD), D4 (PTRD), D5 (OQD)\n");
            String method = methodNote(QDTables.HD13, neq);
            double d1 = QDTables.getHD13("D1", neq);
            double d2 = QDTables.getHD13("D2", neq);
            double d3 = QDTables.getHD13("D3", neq);
            double d4 = QDTables.getHD13("D4", neq);
            double d5 = QDTables.getHD13("D5", neq);
            sb.append("SIQD TU (D1): ").append(round(d1)).append(" m\n");
            sb.append("SIQD Bunker (D2): ").append(round(d2)).append(" m\n");
            sb.append("PIQD (D3): ").append(round(d3)).append(" m\n");
            sb.append("PTRD (D4): ").append(round(d4)).append(" m\n");
            sb.append("OQD (D5): ").append(round(d5)).append(" m\n");
            sb.append("Method: ").append(method).append("\n");
        } else {
            sb.append("No tables available for selected HD.");
        }

        return sb.toString();
    }

    private boolean isStorageToStorage(String esType) {
        return "Igloo".equals(esType) || "Bunker".equals(esType) || "Above Ground Magazine".equals(esType);
    }

    private String mapFaceKey(String pes, String pesOrientation) {
        if ("Igloo".equals(pes)) {
            if (pesOrientation.startsWith("Side")) return "Igloo a.Side";
            if (pesOrientation.startsWith("Rear")) return "Igloo b.Rear";
            if (pesOrientation.startsWith("Front") && pesOrientation.contains("Untraversed")) return "Igloo c.Front UT";
            if (pesOrientation.startsWith("Front") && pesOrientation.contains("Traversed")) return "Igloo d.Front T";
        } else if ("Bunker".equals(pes)) {
            if (pesOrientation.startsWith("Side")) return "Bunker a.Side";
            if (pesOrientation.startsWith("Rear")) return "Bunker b.Rear";
            if (pesOrientation.startsWith("Front") && pesOrientation.contains("Untraversed")) return "Bunker c.Front UT";
            if (pesOrientation.startsWith("Front") && pesOrientation.contains("Traversed")) return "Bunker d.Front T";
        } else if ("Above Ground Magazine".equals(pes)) {
            return pesOrientation.contains("Traversed") ? "Above Ground Magazine T" : "Above Ground Magazine UT";
        }
        return "";
    }

    private String mapESKey(String esType, String esOrientation) {
        if ("Igloo".equals(esType)) {
            if (esOrientation.startsWith("Side")) return "Igloo a.Side";
            if (esOrientation.startsWith("Rear")) return "Igloo b.Rear";
            if (esOrientation.startsWith("Front") && esOrientation.contains("Untraversed")) return "Igloo c.Front UT";
            if (esOrientation.startsWith("Front") && esOrientation.contains("Traversed")) return "Igloo d.Front T";
        } else if ("Bunker".equals(esType)) {
            if (esOrientation.startsWith("Side")) return "Bunker a.Side";
            if (esOrientation.startsWith("Rear")) return "Bunker b.Rear";
            if (esOrientation.startsWith("Front") && esOrientation.contains("Untraversed")) return "Bunker c.Front UT";
            if (esOrientation.startsWith("Front") && esOrientation.contains("Traversed")) return "Bunker d.Front T";
        } else if ("Above Ground Magazine".equals(esType)) {
            return esOrientation.contains("Traversed") ? "Above Ground Magazine T" : "Above Ground Magazine UT";
        }
        return "";
    }

    private String mapHD11PublicDCode(String esType, String traverseSel) {
        boolean traversed = traverseSel.startsWith("Traversed");
        if ("Traffic Route".equals(esType)) {
            return traversed ? "D8" : "D10";
        }
        if ("Dwelling / Public Building".equals(esType)) {
            return traversed ? "D11" : "D12";
        }
        return "D8";
    }

    private String mapHD12DCode(String esType) {
        if (isStorageToStorage(esType)) return "D1";
        if ("Traffic Route".equals(esType)) return "D2";
        if ("Dwelling / Public Building".equals(esType)) return "D4";
        return "D3";
    }

    private String mapHD13DCode(String esType) {
        if (isStorageToStorage(esType)) return "D1";
        if ("Traffic Route".equals(esType)) return "D3";
        if ("Dwelling / Public Building".equals(esType)) return "D5";
        return "D4";
    }

    private double lookupOrInterpolate(java.util.TreeMap<Double, Double> map, double neq) {
        if (map.containsKey(neq)) return map.get(neq);
        java.util.Map.Entry<Double, Double> lower = map.floorEntry(neq);
        java.util.Map.Entry<Double, Double> upper = map.ceilingEntry(neq);
        if (lower == null) return map.firstEntry().getValue();
        if (upper == null) return map.lastEntry().getValue();
        if (lower.getKey().equals(upper.getKey())) return lower.getValue();
        double x0 = lower.getKey();
        double x1 = upper.getKey();
        double t = (neq - x0) / (x1 - x0);
        double y0 = lower.getValue();
        double y1 = upper.getValue();
        return y0 + t * (y1 - y0);
    }

    private String methodNote(java.util.TreeMap<Double, ?> map, double neq) {
        if (map.containsKey(neq)) return "Exact table value";
        java.util.Map.Entry<Double, ?> lower = map.floorEntry(neq);
        java.util.Map.Entry<Double, ?> upper = map.ceilingEntry(neq);
        if (lower == null) return "Clamped to minimum table value";
        if (upper == null) return "Clamped to maximum table value";
        if (lower.getKey().equals(upper.getKey())) return "Exact table value";
        return "Linear interpolation between " + lower.getKey() + " kg and " + upper.getKey() + " kg";
    }

    private long round(double v) {
        return Math.round(v);
    }
} 