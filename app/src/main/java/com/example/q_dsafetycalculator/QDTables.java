package com.example.q_dsafetycalculator;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class QDTables {

    // ---------- helpers ----------
    public static double q13(double qkg){ return Math.cbrt(qkg); }
    public static long rint(double x){ return Math.round(x); }

    // ---------- Table I-A (full) ----------
    // Keys:
    //   PES: "Igloo", "Bunkers", "Above Ground Magazine"
    //   ES key format:
    //     For Igloo/Bunkers ES: "Igloo a.Side", "Igloo b.Rear", "Igloo c.Front UT", "Igloo d.Front T"
    //                           "Bunkers a.Side", ... etc.
    //     For Above Ground Magazine ES: "Above Ground Magazine UT", "Above Ground Magazine T"
    // Values: "D1".."D6"
    public static Map<String, Map<String, String>> TABLE_IA = new HashMap<String, Map<String, String>>() {{

        // From PES = Igloo
        put("Igloo", new HashMap<String, String>() {{
            // To ES = Igloo
            put("Igloo a.Side", "D1");
            put("Igloo b.Rear", "D1");
            put("Igloo c.Front UT", "D3");
            put("Igloo d.Front T", "D3");
            // To ES = Bunkers
            put("Bunker a.Side", "D1");
            put("Bunker b.Rear", "D1");
            put("Bunker c.Front UT", "D5");
            put("Bunker d.Front T", "D5");
            // To ES = Above Ground Magazine
            put("Above Ground Magazine UT", "D5");
            put("Above Ground Magazine T",  "D4");
        }});

        // From PES = Bunkers
        put("Bunker", new HashMap<String, String>() {{
            // To ES = Igloo
            put("Igloo a.Side", "D1");
            put("Igloo b.Rear", "D1");
            put("Igloo c.Front UT", "D3");
            put("Igloo d.Front T",  "D3");
            // To ES = Bunkers
            put("Bunker a.Side", "D3");
            put("Bunker b.Rear", "D3");
            put("Bunker c.Front UT", "D5");
            put("Bunker d.Front T",  "D5");
            // To ES = Above Ground Magazine
            put("Above Ground Magazine UT", "D5");
            put("Above Ground Magazine T",  "D5");
        }});

        // From PES = Above Ground Magazine
        put("Above Ground Magazine", new HashMap<String, String>() {{
            // To ES = Igloo
            put("Igloo a.Side", "D5");
            put("Igloo b.Rear", "D5");
            put("Igloo c.Front UT", "D6");
            put("Igloo d.Front T",  "D5");
            // To ES = Bunkers
            put("Bunker a.Side", "D5");
            put("Bunker b.Rear", "D5");
            put("Bunker c.Front UT", "D6");
            put("Bunker d.Front T",  "D5");
            // To ES = Above Ground Magazine
            put("Above Ground Magazine UT", "D6");
            put("Above Ground Magazine T",  "D5");
        }});
    }};

    // ---------- Table I-B (HD 1.1 SIQD: D1..D6 by NEQ) ----------
    public static class HD11_SIQD_Row {
        public double D1, D2, D3, D4, D5, D6;
        public HD11_SIQD_Row(double d1, double d2, double d3, double d4, double d5, double d6) {
            this.D1=d1; this.D2=d2; this.D3=d3; this.D4=d4; this.D5=d5; this.D6=d6;
        }
    }

    public static TreeMap<Double, HD11_SIQD_Row> HD11_SIQD = new TreeMap<Double, HD11_SIQD_Row>() {{
        put(50.0,   new HD11_SIQD_Row(10,10,10,10,10,18));
        put(100.0,  new HD11_SIQD_Row(10,10,10,10,12,23));
        put(150.0,  new HD11_SIQD_Row(10,10,10,13,26,29));
        put(200.0,  new HD11_SIQD_Row(10,10,10,11,14,29));
        put(300.0,  new HD11_SIQD_Row(10,10,10,12,17,33));
        put(400.0,  new HD11_SIQD_Row(10,10,10,14,18,36));
        put(500.0,  new HD11_SIQD_Row(10,10,10,15,20,39));
        put(600.0,  new HD11_SIQD_Row(10,10,10,16,21,41));
        put(700.0,  new HD11_SIQD_Row(10,10,10,16,22,43));
        put(800.0,  new HD11_SIQD_Row(10,10,11,17,23,45));
        put(900.0,  new HD11_SIQD_Row(10,10,11,18,24,47));
        put(1000.0, new HD11_SIQD_Row(10,10,11,18,24,48));
        put(1500.0, new HD11_SIQD_Row(10,10,13,21,28,55));
        put(2000.0, new HD11_SIQD_Row(10,11,14,23,31,61));
        put(2500.0, new HD11_SIQD_Row(10,11,15,25,33,66));
        put(3000.0, new HD11_SIQD_Row(10,12,16,26,35,70));
        put(3500.0, new HD11_SIQD_Row(10,13,17,27,36,73));
        put(4000.0, new HD11_SIQD_Row(10,13,18,29,38,76));
        put(4500.0, new HD11_SIQD_Row(10,13,18,30,40,79));
        put(5000.0, new HD11_SIQD_Row(10,14,19,31,41,82));
        put(6000.0, new HD11_SIQD_Row(10,15,20,33,44,87));
        put(7000.0, new HD11_SIQD_Row(10,15,21,35,46,92));
        put(8000.0, new HD11_SIQD_Row(10,16,22,36,48,96));
        put(9000.0, new HD11_SIQD_Row(11,17,23,38,50,100));
        put(10000.0,new HD11_SIQD_Row(11,17,24,39,52,105));
        put(15000.0,new HD11_SIQD_Row(13,20,27,45,59,120));
        put(20000.0,new HD11_SIQD_Row(14,22,30,49,65,135));
        put(25000.0,new HD11_SIQD_Row(15,24,32,53,70,145));
        put(30000.0,new HD11_SIQD_Row(16,25,34,56,75,150));
        put(35000.0,new HD11_SIQD_Row(17,26,36,59,79,160));
        put(40000.0,new HD11_SIQD_Row(17,27,38,62,82,165));
        put(45000.0,new HD11_SIQD_Row(18,29,39,64,86,175));
        put(50000.0,new HD11_SIQD_Row(19,30,41,67,89,180));
        put(60000.0,new HD11_SIQD_Row(20,32,43,71,94,190));
        put(70000.0,new HD11_SIQD_Row(21,33,46,74,100,200));
        put(80000.0,new HD11_SIQD_Row(22,35,48,78,105,210));
        put(90000.0,new HD11_SIQD_Row(23,36,49,81,110,215));
        put(100000.0,new HD11_SIQD_Row(23,37,51,84,115,225));
        put(112500.0,new HD11_SIQD_Row(24,39,53,87,120,235));
        put(125000.0,new HD11_SIQD_Row(25,40,55,90,120,240));
        put(136000.0,new HD11_SIQD_Row(26,41,57,93,125,250));
        put(150000.0,new HD11_SIQD_Row(27,43,59,96,130,260));
        put(175000.0,new HD11_SIQD_Row(28,45,62,101,135,270));
        put(200000.0,new HD11_SIQD_Row(30,47,65,106,145,285));
        put(220000.0,new HD11_SIQD_Row(31,49,67,109,145,290));
    }};

    public static double getHD11_SIQD(String dCode, double neqKg) {
        // Exact match
        if (HD11_SIQD.containsKey(neqKg)) {
            return getDValue(HD11_SIQD.get(neqKg), dCode);
        }

        // Find bounding keys
        Map.Entry<Double, HD11_SIQD_Row> lower = HD11_SIQD.floorEntry(neqKg);
        Map.Entry<Double, HD11_SIQD_Row> upper = HD11_SIQD.ceilingEntry(neqKg);

        // If below smallest → clamp
        if (lower == null) return getDValue(HD11_SIQD.firstEntry().getValue(), dCode);

        // If above largest → clamp
        if (upper == null) return getDValue(HD11_SIQD.lastEntry().getValue(), dCode);

        // If exact bound (already handled above, but just in case)
        if (lower.getKey().equals(upper.getKey())) {
            return getDValue(lower.getValue(), dCode);
        }

        // Interpolation ratio
        double x0 = lower.getKey(), x1 = upper.getKey();
        double t = (neqKg - x0) / (x1 - x0);

        double y0 = getDValue(lower.getValue(), dCode);
        double y1 = getDValue(upper.getValue(), dCode);

        return y0 + t * (y1 - y0); // linear interpolation
    }

    // Helper to extract D1..D6
    private static double getDValue(HD11_SIQD_Row r, String dCode) {
        switch (dCode) {
            case "D1": return r.D1;
            case "D2": return r.D2;
            case "D3": return r.D3;
            case "D4": return r.D4;
            case "D5": return r.D5;
            case "D6": return r.D6;
            default: return r.D1;
        }
    }


    // ---------- Table I-C (HD 1.1 PIQD & OQD family: D7, D8 (8Q1/3), D9, D10 (22.2Q1/3), D11, D12) ----------
    public static TreeMap<Double, Double> T1C_D7 = new TreeMap<Double, Double>() {{
        put(50.0,18.0); put(100.0,23.0); put(150.0,26.0); put(200.0,29.0); put(300.0,33.0);
        put(400.0,36.0); put(500.0,39.0); put(600.0,42.0); put(700.0,45.0); put(800.0,48.0);
        put(900.0,50.0); put(1000.0,53.0); put(1500.0,66.0); put(2000.0,78.0); put(2500.0,90.0);
        put(3000.0,105.0); put(3500.0,115.0); put(4000.0,130.0);
        // Then column goes blank; use D8 formula
    }};
    public static double T1C_D8_formula(double neq){ return rint(8.0 * q13(neq)); }

    public static TreeMap<Double, Double> T1C_D9 = new TreeMap<Double, Double>() {{
        put(50.0,45.0); put(100.0,45.0); put(150.0,45.0); put(200.0,52.0); put(300.0,68.0);
        put(400.0,82.0); put(500.0,95.0); put(600.0,110.0); put(700.0,120.0); put(800.0,130.0);
        put(900.0,140.0); put(1000.0,150.0); put(1500.0,200.0); put(2000.0,240.0); put(2500.0,280.0);
        put(3000.0,305.0); put(3500.0,330.0); put(4000.0,350.0); put(4500.0,370.0); put(5000.0,380.0);
        put(6000.0,405.0); put(7000.0,425.0); put(8000.0,445.0); put(9000.0,465.0);
        // Then column goes blank; use D10 formula
    }};
    public static double T1C_D10_formula(double neq){ return rint(22.2 * q13(neq)); }

    public static TreeMap<Double, Double> T1C_D11 = new TreeMap<Double, Double>() {{
        put(50.0,180.0); put(100.0,180.0); put(150.0,180.0); put(200.0,180.0); put(300.0,180.0);
        put(400.0,180.0); put(500.0,180.0); put(600.0,180.0); put(700.0,180.0); put(800.0,180.0);
        put(900.0,180.0); put(1000.0,180.0); put(1500.0,180.0); put(2000.0,190.0); put(2500.0,205.0);
        put(3000.0,215.0); put(3500.0,225.0); put(4000.0,235.0); put(4500.0,245.0); put(5000.0,255.0);
        put(6000.0,270.0); put(7000.0,285.0); put(8000.0,300.0); put(9000.0,310.0); put(10000.0,320.0);
        put(15000.0,365.0); put(20000.0,405.0); put(25000.0,435.0); put(30000.0,450.0); put(35000.0,485.0);
        put(40000.0,510.0); put(45000.0,530.0); put(50000.0,545.0); put(60000.0,580.0); put(70000.0,610.0);
        put(80000.0,640.0); put(90000.0,665.0); put(100000.0,690.0); put(120000.0,730.0); put(136000.0,765.0);
        put(150000.0,790.0); put(175000.0,830.0); put(200000.0,870.0); put(220000.0,895.0);
    }};
    public static TreeMap<Double, Double> T1C_D12 = new TreeMap<Double, Double>() {{
        put(50.0,275.0); put(100.0,275.0); put(150.0,275.0); put(200.0,275.0); put(300.0,275.0);
        put(400.0,275.0); put(500.0,275.0); put(600.0,275.0); put(700.0,275.0); put(800.0,275.0);
        put(900.0,275.0); put(1000.0,275.0); put(1500.0,275.0); put(2000.0,280.0); put(2500.0,305.0);
        put(3000.0,320.0); put(3500.0,340.0); put(4000.0,355.0); put(4500.0,370.0); put(5000.0,380.0);
        put(6000.0,405.0); put(7000.0,425.0); put(8000.0,445.0); put(9000.0,465.0); put(10000.0,480.0);
        put(15000.0,550.0); put(20000.0,605.0); put(25000.0,650.0); put(30000.0,690.0); put(35000.0,730.0);
        put(40000.0,760.0); put(45000.0,790.0); put(50000.0,820.0); put(60000.0,870.0); put(70000.0,920.0);
        put(80000.0,960.0); put(90000.0,995.0); put(100000.0,1035.0); put(120000.0,1100.0); put(136000.0,1145.0);
        put(150000.0,1180.0); put(175000.0,1245.0); put(200000.0,1300.0); put(220000.0,1345.0);
    }};

    // Table I-D — HD 1.1 OQD for NEQ < 50 kg
    public static TreeMap<Double, Double> OQD_lt50 = new TreeMap<Double, Double>() {{
        put(5.0,65.0); put(10.0,65.0); put(15.0,80.0); put(20.0,115.0); put(25.0,140.0);
        put(30.0,165.0); put(35.0,180.0); put(40.0,195.0); put(45.0,220.0);
    }};

    // ---------- Table II — HD 1.2 (you provided) ----------
    // Columns in your sheet: D1, D2, D3, D4
    // Interpreted as:
    //   SIQD to storage (D1),
    //   PIQD to process (D2),
    //   OQD (PTRD/main offices/etc.) (D3),
    //   OQD (dwellings etc.) (D4)
    public static class HD12_Row {
        public double D1_SIQD, D2_PIQD, D3_OQD_PTRD, D4_OQD_Dwell;
        public HD12_Row(double d1, double d2, double d3, double d4) {
            this.D1_SIQD=d1; this.D2_PIQD=d2; this.D3_OQD_PTRD=d3; this.D4_OQD_Dwell=d4;
        }
    }
    public static TreeMap<Double, HD12_Row> HD12 = new TreeMap<Double, HD12_Row>() {{
        put(50.0,   new HD12_Row(10,20,180,275));
        put(100.0,  new HD12_Row(10,20,180,275));
        put(150.0,  new HD12_Row(10,20,180,275));
        put(300.0,  new HD12_Row(10,24,180,275));
        put(500.0,  new HD12_Row(10,27,180,275));
        put(1000.0, new HD12_Row(10,32,180,275));
        put(2000.0, new HD12_Row(13,37,190,280));
        put(3000.0, new HD12_Row(16,40,215,320));
        put(5000.0, new HD12_Row(20,45,255,350));
        put(10000.0,new HD12_Row(24,51,295,390));
        put(15000.0,new HD12_Row(25,55,315,420));
        put(20000.0,new HD12_Row(26,58,335,445));
        put(25000.0,new HD12_Row(27,60,345,460));
        put(30000.0,new HD12_Row(28,62,360,475));
        put(40000.0,new HD12_Row(30,66,375,500));
        put(50000.0,new HD12_Row(30,68,390,520));
        put(60000.0,new HD12_Row(30,70,400,525));
        put(70000.0,new HD12_Row(30,72,410,530));
        put(80000.0,new HD12_Row(30,74,410,540));
        put(90000.0,new HD12_Row(30,75,410,550));
        put(100000.0,new HD12_Row(30,76,410,560));
        put(120000.0,new HD12_Row(30,79,410,560));
        put(136000.0,new HD12_Row(30,80,410,560));
        put(150000.0,new HD12_Row(30,82,410,560));
        put(175000.0,new HD12_Row(30,80,410,560));
        put(200000.0,new HD12_Row(30,85,410,560));
        put(220000.0,new HD12_Row(30,87,410,560));
    }};

    public static double getHD12(String dCode, double neqKg) {
        if (HD12.containsKey(neqKg)) {
            return getHD12Value(HD12.get(neqKg), dCode);
        }
        Map.Entry<Double, HD12_Row> lower = HD12.floorEntry(neqKg);
        Map.Entry<Double, HD12_Row> upper = HD12.ceilingEntry(neqKg);
        if (lower == null) return getHD12Value(HD12.firstEntry().getValue(), dCode);
        if (upper == null) return getHD12Value(HD12.lastEntry().getValue(), dCode);
        if (lower.getKey().equals(upper.getKey())) {
            return getHD12Value(lower.getValue(), dCode);
        }
        double x0 = lower.getKey(), x1 = upper.getKey();
        double t = (neqKg - x0) / (x1 - x0);
        double y0 = getHD12Value(lower.getValue(), dCode);
        double y1 = getHD12Value(upper.getValue(), dCode);
        return y0 + t * (y1 - y0);
    }

    private static double getHD12Value(HD12_Row r, String dCode) {
        switch (dCode) {
            case "D1": return r.D1_SIQD;
            case "D2": return r.D2_PIQD;
            case "D3": return r.D3_OQD_PTRD;
            case "D4": return r.D4_OQD_Dwell;
            default: return r.D1_SIQD;
        }
    }

    // ---------- HD 1.3 (you provided the full table with numeric values) ----------
    // Header mapping (from your sheet):
    //   SIQD: Traverse/Untraversed D1=0.22*sqrt(Q), Bunker D2=0.11*sqrt(Q)
    //   PIQD: D3=3.2 Q^(1/3)
    //   PTRD: D4=4.3 Q^(1/3)
    //   OQD : D5=6.4 Q^(1/3)
    public static class HD13_Row {
        public double D1_SIQD_TU, D2_SIQD_Bunker, D3_PIQD, D4_PTRD, D5_OQD;
        public HD13_Row(double d1, double d2, double d3, double d4, double d5) {
            this.D1_SIQD_TU=d1; this.D2_SIQD_Bunker=d2; this.D3_PIQD=d3; this.D4_PTRD=d4; this.D5_OQD=d5;
        }
    }
    public static TreeMap<Double, HD13_Row> HD13 = new TreeMap<Double, HD13_Row>() {{
        put(50.0,   new HD13_Row(10,10,12,60,60));
        put(100.0,  new HD13_Row(10,10,15,60,60));
        put(150.0,  new HD13_Row(10,10,17,60,60));
        put(200.0,  new HD13_Row(10,10,19,60,60));
        put(300.0,  new HD13_Row(10,10,22,60,60));
        put(400.0,  new HD13_Row(10,10,24,60,60));
        put(500.0,  new HD13_Row(10,10,26,60,60));
        put(600.0,  new HD13_Row(10,10,27,60,60));
        put(700.0,  new HD13_Row(10,10,29,60,60));
        put(800.0,  new HD13_Row(10,10,30,60,60));
        put(900.0,  new HD13_Row(10,10,31,60,60));
        put(1000.0, new HD13_Row(10,10,32,60,60));
        put(1500.0, new HD13_Row(10,10,37,60,60));
        put(2000.0, new HD13_Row(10,10,41,60,65));
        put(2500.0, new HD13_Row(12,10,44,60,73));
        put(3000.0, new HD13_Row(13,10,46,60,80));
        put(3500.0, new HD13_Row(14,10,49,60,86));
        put(4000.0, new HD13_Row(15,10,51,62,92));
        put(4500.0, new HD13_Row(16,10,53,66,98));
        put(5000.0, new HD13_Row(16,10,55,74,110));
        put(6000.0, new HD13_Row(16,10,58,78,120));
        put(7000.0, new HD13_Row(19,10,62,82,125));
        put(8000.0, new HD13_Row(21,11,64,86,130));
        put(9000.0, new HD13_Row(22,11,67,89,135));
        put(10000.0,new HD13_Row(23,12,69,92,140));
        put(15000.0,new HD13_Row(28,14,79,110,160));
        put(20000.0,new HD13_Row(32,16,87,120,175));
        put(25000.0,new HD13_Row(36,18,94,125,190));
        put(30000.0,new HD13_Row(40,20,100,135,200));
        put(35000.0,new HD13_Row(43,22,105,140,210));
        put(40000.0,new HD13_Row(46,23,110,150,220));
        put(45000.0,new HD13_Row(48,24,115,155,230));
        put(50000.0,new HD13_Row(51,26,120,160,240));
        put(60000.0,new HD13_Row(56,28,130,170,255));
        put(70000.0,new HD13_Row(60,30,135,180,265));
        put(80000.0,new HD13_Row(64,32,140,185,280));
        put(90000.0,new HD13_Row(68,34,145,195,290));
        put(100000.0,new HD13_Row(72,36,150,200,300));
        put(120000.0,new HD13_Row(79,40,160,215,320));
        put(136000.0,new HD13_Row(84,42,165,220,330));
        put(150000.0,new HD13_Row(87,44,175,230,345));
        put(175000.0,new HD13_Row(95,48,180,245,360));
        put(200000.0,new HD13_Row(100,50,190,255,375));
        put(220000.0,new HD13_Row(105,52,195,260,390));
    }};

    public static double getHD13(String dCode, double neqKg) {
        // Exact match
        if (HD13.containsKey(neqKg)) {
            return getHD13Value(HD13.get(neqKg), dCode);
        }

        // Find bounds
        Map.Entry<Double, HD13_Row> lower = HD13.floorEntry(neqKg);
        Map.Entry<Double, HD13_Row> upper = HD13.ceilingEntry(neqKg);

        if (lower == null) return getHD13Value(HD13.firstEntry().getValue(), dCode);
        if (upper == null) return getHD13Value(HD13.lastEntry().getValue(), dCode);
        if (lower.getKey().equals(upper.getKey())) {
            return getHD13Value(lower.getValue(), dCode);
        }

        // Interpolate
        double x0 = lower.getKey(), x1 = upper.getKey();
        double t = (neqKg - x0) / (x1 - x0);

        double y0 = getHD13Value(lower.getValue(), dCode);
        double y1 = getHD13Value(upper.getValue(), dCode);

        return y0 + t * (y1 - y0);
    }

    private static double getHD13Value(HD13_Row r, String dCode) {
        switch (dCode) {
            case "D1": return r.D1_SIQD_TU;
            case "D2": return r.D2_SIQD_Bunker;
            case "D3": return r.D3_PIQD;
            case "D4": return r.D4_PTRD;
            case "D5": return r.D5_OQD;
            default: return r.D1_SIQD_TU;
        }
    }


    // ---------- Special features ----------
    // Barricade (you gave: OQD around adit 465 m without vs 200 m with)
    public static double BARRICADE_OQD_FACTOR = 200.0 / 465.0;

    // Underground (requirements)
    public static double UNDERGROUND_MIN_SCALED_COVER = 0.35; // m per Q^(1/3)
    public static double UNDERGROUND_INTERVAL_SOFT = 1.4;     // m per Q^(1/3)
    public static double UNDERGROUND_INTERVAL_HARD = 2.0;     // m per Q^(1/3)
    public static double UNDERGROUND_OQD_AROUND_CHAMBER_K = 6.0; // m per Q^(1/3)

    // Blast wall thickness table (exact values you sent)
    public static class BlastWallRow {
        public Double brick1m, brick1_25m, brick1_5m, rc1m, rc1_25m, rc1_5m;
        public BlastWallRow(Double b1, Double b125, Double b15, Double r1, Double r125, Double r15) {
            this.brick1m=b1; this.brick1_25m=b125; this.brick1_5m=b15; this.rc1m=r1; this.rc1_25m=r125; this.rc1_5m=r15;
        }
    }
    public static Map<Double, BlastWallRow> BLAST_WALL = new HashMap<Double, BlastWallRow>() {{
        put(5.0,  new BlastWallRow(0.34,0.23,0.23,null,null,null));
        put(10.0, new BlastWallRow(0.46,0.46,0.34,null,null,null));
        put(15.0, new BlastWallRow(0.56,0.46,0.46,0.25,0.20,0.20));
        put(20.0, new BlastWallRow(0.81,0.70,0.56,0.35,0.30,0.25));
        put(25.0, new BlastWallRow(null,null,null,0.40,0.30,0.25));
        put(35.0, new BlastWallRow(null,null,null,0.50,0.40,0.35));
        put(50.0, new BlastWallRow(null,null,null,0.60,0.50,0.40));
        put(75.0, new BlastWallRow(null,null,null,0.80,0.65,0.55));
        put(100.0,new BlastWallRow(null,null,null,1.00,0.80,0.65));
        put(125.0,new BlastWallRow(null,null,null,1.15,0.90,0.75));
    }};

    // Generic floor lookup
    public static double lookupFloor(TreeMap<Double, Double> map, double neq) {
        Map.Entry<Double, Double> e = map.floorEntry(neq);
        if (e == null) e = map.firstEntry();
        return e.getValue();
    }
}
