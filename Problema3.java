import java.util.Random;

public class Problema3 {
    static long iteracaoCount = 0;
    static long instruCount   = 0;

    public static void calculaLPS(String cam, int M, int[] lps) {
        int len = 0;        instruCount++;
        int i   = 1;        instruCount++;
        lps[0]  = 0;        instruCount++;

        while (i < M) {
            iteracaoCount++;    // iteração do loop de LPS
            instruCount++;

            if (cam.charAt(i) == cam.charAt(len)) {
                len++;           instruCount++;
                lps[i] = len;    instruCount++;
                i++;
            } else {
                if (len != 0) {
                    instruCount++;
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;  instruCount++;
                    i++;
                }
            }
            instruCount++;
        }
    }

    public static long KMPBusca(String cam, String txt) {
        int M = cam.length();    instruCount++;
        int N = txt.length();    instruCount++;

        // trata padrão vazio: não há buscas a fazer
        if (M == 0) {
            return 0;
        }

        int[] lps = new int[M];  instruCount++;
        int j    = 0;            instruCount++;
        long matchCount = 0;

        calculaLPS(cam, M, lps);

        int i = 0;               instruCount++;
        while (i < N) {
            iteracaoCount++;      // iteração do loop de busca
            instruCount++;

            if (j < M && cam.charAt(j) == txt.charAt(i)) {
                i++;              instruCount++;
                j++;              instruCount++;
            }

            if (j == M) {
                matchCount++;     instruCount++;
                j = lps[j - 1];   instruCount++;
            }
            else if (i < N && (j >= M || cam.charAt(j) != txt.charAt(i))) {
                instruCount += 2;
                if (j != 0) {
                    instruCount++;
                    j = lps[j - 1];
                } else {
                    i++;
                }
                instruCount++;
            }
        }

        return matchCount;
    }

    public static String geraUniforme(char c, int N) {
        return String.valueOf(c).repeat(Math.max(0, N));
    }

    public static String geraRandom(int N) {
        Random r = new Random(123);
        StringBuilder sb = new StringBuilder(N);
        for (int i = 0; i < N; i++) {
            sb.append((char)('a' + r.nextInt(26)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[][] casos = {
                { "1. Padrão não existe",
                        geraUniforme('a', 500_000),
                        geraUniforme('b', 1_000) },

                { "2. Padrão no início",
                        geraUniforme('a', 1_000) + geraRandom(499_000),
                        geraRandom(1_000) },

                { "3. Padrão no fim",
                        geraRandom(499_000) + geraRandom(1_000),
                        geraRandom(1_000) },

                { "4. Ocorrências sobrepostas",
                        geraUniforme('a', 500_000),
                        "aaa" },

                { "5. Texto e padrão aleatórios",
                        geraRandom(500_000),
                        geraRandom(1_000) },

                { "6. Padrão maior que o texto",
                        geraRandom(800_000),
                        geraRandom(1_000_000) },

                { "7a. Texto vazio, padrão não vazio",
                        "",
                        geraRandom(1_000) },

                { "7b. Texto não vazio, padrão vazio",
                        geraRandom(1_000),
                        "" },

                { "7c. Texto e padrão vazios",
                        "",
                        "" },

                { "8. Long-tail repetitivo",
                        new String(new char[250_000]).replace("\0", "ab"),
                        new String(new char[500]).replace("\0", "ab") }
        };

        for (String[] caso : casos) {
            String nome = caso[0];
            String txt  = caso[1];
            String cam  = caso[2];

            iteracaoCount = 0;
            instruCount   = 0;

            System.out.println("\n=== Caso de Teste: " + nome + " ===");
            long t0 = System.currentTimeMillis();
            long matches = KMPBusca(cam, txt);
            long dt = System.currentTimeMillis() - t0;

            System.out.printf(
                    "Ocorrências: %d, Iterações: %d, Instruções: %d, Tempo: %d ms%n",
                    matches, iteracaoCount, instruCount, dt
            );
        }
    }
}
