public class Problema2 {
    // alfabeto
    private final int R;
    private final long Q;
    private long RM; // R^(M-1) % Q
    private final long camHash; // é o hash padrão
    // são os contadores de desempenho:
    private long iterCont;
    private long instruCont;

    public Problema2(String cam, int R, long Q) {
        this.R = R;
        this.Q = Q;
        int M = cam.length();
        // faz a pré-computação de R^(M-1) % Q isso vai remover o caractere mais alto
        RM = 1;
        for (int i = 1; i < M; i++) {
            RM = (RM * R) % Q;
            instruCont += 2;
        }
        camHash = hash(cam, M);
    }

    // acredito que 0(M)
    private long hash(String s, int M) {
        long h = 0;
        for (int j = 0; j < M; j++) {
            h = (h * R + s.charAt(j)) % Q;
            instruCont += 3;
        }
        return h;
    }

    /**
     * Busca um padrão do txt e retorna o indice da primeira ocorrencia
     * ou o length de txt se não encontrar esse indice.
     */
    public int busca(String txt, String cam) {
        int N = txt.length();
        int M = cam.length();
        if (N < M) return N;

        long txtHash = hash(txt.substring(0, M), M);
        iterCont = 1;
        instruCont++;

        if (camHash == txtHash) return 0;

        for (int i = 1; i <= N - M; i++) {
            iterCont++;
            txtHash = (txtHash + Q - RM * txt.charAt(i - 1) % Q) % Q;
            instruCont += 4;

            txtHash = (txtHash * R + txt.charAt(i + M - 1)) % Q;
            instruCont += 3;

            instruCont++;
            if (txtHash == camHash) {
                return i;
            }
        }

        return N;
    }

    public long getIterCont() {
        return iterCont;
    }

    public long getInstruCont() {
        return instruCont;
    }

    private static String geraUniforme(char c, int N) {
        return Problema3.geraUniforme(c, N);
    }

    private static String geraRandom(int N) {
        return Problema3.geraRandom(N);
    }

    private static String geraRepetitivo(int vezes) {
        return "ab".repeat(Math.max(0, vezes));
    }

    public static void main(String[] args) {
        int R = 256;
        long Q = 1_000_000_007L;

        // monta todos os textos e padrões usados nos casos de teste
        String txt1  = geraUniforme('a', 500_000);
        String cam1  = geraUniforme('b', 1_000);

        String cam2  = geraRandom(1_000);
        String txt2  = cam2 + geraRandom(499_000);

        String cam3  = geraRandom(1_000);
        String txt3  = geraRandom(499_000) + cam3;

        String txt4  = geraUniforme('a', 500_000);
        String cam4  = "aaa";

        String txt5  = geraRandom(500_000);
        String cam5  = geraRandom(1_000);

        String txt6  = geraRandom(800_000);
        String cam6  = geraRandom(1_000_000);

        String txt7a = "";
        String cam7a = geraRandom(1_000);

        String txt7b = geraRandom(1_000);
        String cam7b = "";

        String txt7c = "";
        String cam7c = "";

        String txt8  = geraRepetitivo(250_000);
        String cam8  = geraRepetitivo(500);

        String[][] casos = {
                { "1. Padrão não existe",              txt1,  cam1  },
                { "2. Padrão no início",               txt2,  cam2  },
                { "3. Padrão no fim",                  txt3,  cam3  },
                { "4. Ocorrências sobrepostas",        txt4,  cam4  },
                { "5. Texto e padrão aleatórios",      txt5,  cam5  },
                { "6. Padrão maior que o texto",       txt6,  cam6  },
                { "7a. Texto vazio, padrão não vazio", txt7a, cam7a },
                { "7b. Texto não vazio, padrão vazio", txt7b, cam7b },
                { "7c. Texto e padrão vazios",        txt7c, cam7c },
                { "8. Long-tail repetitivo",           txt8,  cam8  }
        };

        for (String[] caso : casos) {
            String nome = caso[0];
            String txt = caso[1];
            String cam = caso[2];

            Problema2 p2 = new Problema2(cam, R, Q);
            long t0 = System.currentTimeMillis();
            int pos = p2.busca(txt, cam);
            long dt = System.currentTimeMillis() - t0;

            System.out.printf(
                    "\n=== %s ===%n" +
                            "Índice de primeira ocorrência: %d%n" +
                            "Iterações: %d%n" +
                            "Instruções: %d%n" +
                            "Tempo: %d ms%n",
                    nome,
                    pos,
                    p2.getIterCont(),
                    p2.getInstruCont(),
                    dt
            );
        }
    }
}
