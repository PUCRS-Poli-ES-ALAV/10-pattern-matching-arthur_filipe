public class Problema1 {
    // contadores estáticos
    private static long iterCount   = 0;  // número de comparações de caracteres
    private static long instrCount  = 0;  // número estimado de “instruções”

    /** Zera ambos os contadores */
    public static void resetCount() {
        iterCount = 0;
        instrCount = 0;
    }

    /**
     * Busca o padrão em força-bruta.
     * Incrementa iterCount a cada comparação de caractere,
     * instrCount em operações principais.
     * @return índice da primeira ocorrência, ou -1 se não encontrado.
     */
    public static int search(String text, String pattern) {
        int n = text.length();      instrCount++;
        int m = pattern.length();   instrCount++;
        int pos = -1;               instrCount++;

        for (int i = 0; i <= n - m; i++) {
            instrCount++;           // cada iteração do loop externo
            int j = 0;              instrCount++;
            for (; j < m; j++) {
                instrCount++;       // cada iteração do loop interno
                iterCount++;        // comparação de caractere
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    instrCount++;   // operação de branch
                    break;
                }
            }
            instrCount++;           // checagem de j == m
            if (j == m) {
                instrCount++;
                pos = i;            // padrão encontrado
                instrCount++;
                break;
            }
        }
        instrCount++;               // para o return
        return pos;
    }

    /** @return total de comparações feitas na última busca */
    public static long getIterCount() {
        return iterCount;
    }

    /** @return total de “instruções” contabilizadas na última busca */
    public static long getInstrCount() {
        return instrCount;
    }

    /** Demonstração rápida usando o mesmo exemplo de sala de aula */
    public static void main(String[] args) {
        String s1 = "ABCDCBDCBDACBDABDCBADF";
        String s2 = "ADF";

        resetCount();
        int pos = search(s1, s2);

        System.out.printf(
                "Posição: %d%n" +
                        "Comparações (iterações): %d%n" +
                        "Instruções estimadas: %d%n",
                pos, getIterCount(), getInstrCount()
        );
    }
}
