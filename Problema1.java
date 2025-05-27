public class Problema1 {
    public static void main(String[] args) {
        String s1 = "ABCDCBDCBDACBDABDCBADF";
        String s2 = "ADF";
        int tams1 = s1.length();
        int tams2 = s2.length();
        int pos = -1;

        for (int i = 0; i <= tams1 - tams2; i++) {
            int j;
            for (j = 0; j < tams2; j++) {
                if (s1.charAt(i + j) != s2.charAt(j)) {
                    break;
                }
            }
            if (j == tams2) {
                pos = i;
                break;
            }
        }
        System.out.println(pos);
    }

}
