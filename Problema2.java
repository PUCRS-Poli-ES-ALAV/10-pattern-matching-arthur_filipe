public class Problema2 {
    private static final int    R = 256;
    private static final long   Q = 1_000_000_007L;

    // — Contadores Horner (sem rolling) —
    private static long iterCountNo    = 0;
    private static long instrCountNo   = 0;
    public static void resetNo()       { iterCountNo = 0; instrCountNo = 0; }
    public static long getIterNo()     { return iterCountNo; }
    public static long getInstrNo()    { return instrCountNo; }

    // — Contadores Rolling —
    private static long iterCountRoll  = 0;
    private static long instrCountRoll = 0;
    public static void resetRoll()     { iterCountRoll = 0; instrCountRoll = 0; }
    public static long getIterRoll()   { return iterCountRoll; }
    public static long getInstrRoll()  { return instrCountRoll; }

    /**
     * Rabin–Karp sem rolling hash: O(n·m) no pior caso,
     * recalcula o hash do zero em cada posição.
     * @return índice da 1ª ocorrência ou -1.
     */
    public static int searchNoRolling(String txt, String pat) {
        int n = txt.length();    instrCountNo++;
        int m = pat.length();    instrCountNo++;
        if (n < m) { instrCountNo++; return -1; }

        // hash do padrão
        long patHash = 0;        instrCountNo++;
        for (int j = 0; j < m; j++) {
            instrCountNo += 2;
            patHash = (patHash * R + pat.charAt(j)) % Q;
            instrCountNo++;
        }

        for (int i = 0; i <= n - m; i++) {
            instrCountNo++;  // iteração do laço externo

            // hash do texto na janela [i..i+m-1]
            long txtHash = 0; instrCountNo++;
            for (int j = 0; j < m; j++) {
                instrCountNo += 2;
                txtHash = (txtHash * R + txt.charAt(i + j)) % Q;
                instrCountNo++;
            }

            iterCountNo++; instrCountNo++;
            if (txtHash == patHash) {
                instrCountNo++;
                // confirmação caractere-a-caractere
                int j = 0; instrCountNo++;
                for (; j < m; j++) {
                    instrCountNo++;
                    iterCountNo++;
                    if (txt.charAt(i + j) != pat.charAt(j)) {
                        instrCountNo++;
                        break;
                    }
                }
                instrCountNo++;
                if (j == m) {
                    instrCountNo++;
                    return i;
                }
            }
        }

        instrCountNo++;
        return -1;
    }

    /**
     * Rabin–Karp com rolling hash: O(n + m) esperado.
     * @return índice da 1ª ocorrência ou -1.
     */
    public static int searchRolling(String txt, String pat) {
        int n = txt.length();    instrCountRoll++;
        int m = pat.length();    instrCountRoll++;
        if (n < m) { instrCountRoll++; return -1; }

        // pré-computa R^(m-1) % Q
        long RM = 1;             instrCountRoll++;
        for (int i = 1; i < m; i++) {
            instrCountRoll += 2;
            RM = (RM * R) % Q;
            instrCountRoll++;
        }

        // hash inicial do padrão e do primeiro trecho do texto
        long patHash = 0;        instrCountRoll++;
        long txtHash = 0;        instrCountRoll++;
        for (int j = 0; j < m; j++) {
            instrCountRoll += 3;
            patHash = (patHash * R + pat.charAt(j)) % Q;
            txtHash = (txtHash * R + txt.charAt(j)) % Q;
        }

        // testa na posição 0
        iterCountRoll = 1;       instrCountRoll++;
        if (patHash == txtHash) {
            instrCountRoll++;
            return 0;
        }

        // desliza a janela de 1 até n-m
        for (int i = 1; i <= n - m; i++) {
            instrCountRoll++;
            // remove char antigo
            txtHash = (txtHash + Q - RM * txt.charAt(i - 1) % Q) % Q;
            instrCountRoll += 4;
            // adiciona novo char
            txtHash = (txtHash * R + txt.charAt(i + m - 1)) % Q;
            instrCountRoll += 3;

            iterCountRoll++; instrCountRoll++;
            if (txtHash == patHash) {
                instrCountRoll++;
                return i;
            }
        }

        instrCountRoll++;
        return -1;
    }
}
