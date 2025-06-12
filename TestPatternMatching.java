import java.util.Random;

public class TestPatternMatching {
    public static void main(String[] args) {
        // ─── Configurações de teste ─────────────────────────────────────────
        String textSmall    = "ABCDCBDCBDACBDABDCBADF";
        String patternSmall = "ADF";

        String textLarge    = randomText(500_000, new char[]{'A','B','C','D'});
        String patternLarge = "ZZZZZ";  // garantido não existir no texto

        System.out.printf(
                "%-25s %10s %10s %10s %10s %10s %10s%n",
                "Algoritmo",
                "Cmp(P)", "Ins(P)", "T(s)P",
                "Cmp(G)", "Ins(G)", "T(s)G"
        );
        System.out.println("--------------------------------------------------------------------------------");

        runNaive(
                "Naive ",
                textSmall, patternSmall,
                textLarge, patternLarge
        );

        runRabinNoRolling(
                "RK sem rolling ",
                textSmall, patternSmall,
                textLarge, patternLarge
        );

        runRabinRolling(
                "RK com rolling ",
                textSmall, patternSmall,
                textLarge, patternLarge
        );

        runKMP(
                "KMP ",
                textSmall, patternSmall,
                textLarge, patternLarge
        );
    }

    private static void runNaive(
            String name,
            String ts, String ps,
            String tl, String pl
    ) {
        // Pequeno
        Problema1.resetCount();
        long t0 = System.nanoTime();
        Problema1.search(ts, ps);
        double dtS = (System.nanoTime() - t0) / 1e9;
        long itS = Problema1.getIterCount();
        long inS = Problema1.getInstrCount();

        // Grande
        Problema1.resetCount();
        t0 = System.nanoTime();
        Problema1.search(tl, pl);
        double dtL = (System.nanoTime() - t0) / 1e9;
        long itL = Problema1.getIterCount();
        long inL = Problema1.getInstrCount();

        System.out.printf(
                "%-25s %10d %10d %10.3f %10d %10d %10.3f%n",
                name, itS, inS, dtS, itL, inL, dtL
        );
    }

    private static void runRabinNoRolling(
            String name,
            String ts, String ps,
            String tl, String pl
    ) {
        // Pequeno
        Problema2.resetNo();
        long t0 = System.nanoTime();
        Problema2.searchNoRolling(ts, ps);
        double dtS = (System.nanoTime() - t0) / 1e9;
        long itS = Problema2.getIterNo();
        long inS = Problema2.getInstrNo();

        // Grande
        Problema2.resetNo();
        t0 = System.nanoTime();
        Problema2.searchNoRolling(tl, pl);
        double dtL = (System.nanoTime() - t0) / 1e9;
        long itL = Problema2.getIterNo();
        long inL = Problema2.getInstrNo();

        System.out.printf(
                "%-25s %10d %10d %10.3f %10d %10d %10.3f%n",
                name, itS, inS, dtS, itL, inL, dtL
        );
    }

    private static void runRabinRolling(
            String name,
            String ts, String ps,
            String tl, String pl
    ) {
        // Pequeno
        Problema2.resetRoll();
        long t0 = System.nanoTime();
        Problema2.searchRolling(ts, ps);
        double dtS = (System.nanoTime() - t0) / 1e9;
        long itS = Problema2.getIterRoll();
        long inS = Problema2.getInstrRoll();

        // Grande
        Problema2.resetRoll();
        t0 = System.nanoTime();
        Problema2.searchRolling(tl, pl);
        double dtL = (System.nanoTime() - t0) / 1e9;
        long itL = Problema2.getIterRoll();
        long inL = Problema2.getInstrRoll();

        System.out.printf(
                "%-25s %10d %10d %10.3f %10d %10d %10.3f%n",
                name, itS, inS, dtS, itL, inL, dtL
        );
    }

    private static void runKMP(
            String name,
            String ts, String ps,
            String tl, String pl
    ) {
        // Pequeno
        Problema3.resetCount();
        long t0 = System.nanoTime();
        Problema3.search(ts, ps);
        double dtS = (System.nanoTime() - t0) / 1e9;
        long itS = Problema3.getIterCount();
        long inS = Problema3.getInstrCount();

        // Grande
        Problema3.resetCount();
        t0 = System.nanoTime();
        Problema3.search(tl, pl);
        double dtL = (System.nanoTime() - t0) / 1e9;
        long itL = Problema3.getIterCount();
        long inL = Problema3.getInstrCount();

        System.out.printf(
                "%-25s %10d %10d %10.3f %10d %10d %10.3f%n",
                name, itS, inS, dtS, itL, inL, dtL
        );
    }

    private static String randomText(int length, char[] alphabet) {
        Random rnd = new Random(0); // semente fixa
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(alphabet[rnd.nextInt(alphabet.length)]);
        }
        return sb.toString();
    }
}
