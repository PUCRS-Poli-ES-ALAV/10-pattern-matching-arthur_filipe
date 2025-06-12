public class Problema3 {
    private static long iterCount  = 0;  // comparações caractere-a-caractere
    private static long instrCount = 0;  // instruções estimadas

    /** Zera ambos os contadores */
    public static void resetCount() {
        iterCount = 0;
        instrCount = 0;
    }

    /**
     * KMP: O(n + m). Retorna índice da 1ª ocorrência ou -1.
     */
    public static int search(String txt, String pat) {
        int n = txt.length();    instrCount++;
        int m = pat.length();    instrCount++;
        if (m == 0) { instrCount++; return 0; }

        // constrói o array LPS
        int[] lps = new int[m];  instrCount++;
        buildLPS(pat, m, lps);

        int i = 0, j = 0;         instrCount += 2;
        int pos = -1;            instrCount++;
        while (i < n) {
            iterCount++;         instrCount++;
            // correspondência direta
            if (pat.charAt(j) == txt.charAt(i)) {
                i++; instrCount++;
                j++; instrCount++;
            }
            instrCount++;
            // padrão completo?
            if (j == m) {
                pos = i - j;    instrCount++;
                break;
            } else if (i < n && pat.charAt(j) != txt.charAt(i)) {
                instrCount += 2;
                if (j != 0) {
                    j = lps[j - 1]; instrCount++;
                } else {
                    i++;           instrCount++;
                }
                instrCount++;
            }
        }

        instrCount++;
        return pos;
    }

    /** Constroi o “lps[]” para o padrão */
    private static void buildLPS(String pat, int m, int[] lps) {
        int len = 0;             instrCount++;
        int i   = 1;             instrCount++;
        lps[0]  = 0;             instrCount++;

        while (i < m) {
            iterCount++;        instrCount++;
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;          instrCount++;
                lps[i] = len;   instrCount++;
                i++;            instrCount++;
            } else {
                instrCount++;
                if (len != 0) {
                    len = lps[len - 1]; instrCount++;
                } else {
                    lps[i] = 0;  instrCount++;
                    i++;         instrCount++;
                }
            }
            instrCount++;
        }
    }

    public static long getIterCount()  { return iterCount; }
    public static long getInstrCount() { return instrCount; }
}
