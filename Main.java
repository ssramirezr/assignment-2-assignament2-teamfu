import java.util.*;

public class Main {
    public static String acepta(String entrada, Map<String, Set<String>> CFG) {

        int n = entrada.length();
        Set<String>[][] tabla = new Set[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (Map.Entry<String, Set<String>> entradaCFG : CFG.entrySet()) {
                String noTerminal = entradaCFG.getKey();
                Set<String> producciones = entradaCFG.getValue();
                if (producciones.contains(String.valueOf(entrada.charAt(i - 1)))) {
                    tabla[i - 1][i] = new HashSet<>();
                    tabla[i - 1][i].add(noTerminal);
                }
            }
        }

        for (int l = 2; l <= n; l++) {
            for (int i = 0; i <= n - l; i++) {
                int f = i + l;
                for (int h = i + 1; h < f; h++) {
                    for (Map.Entry<String, Set<String>> entradaCFG : CFG.entrySet()) {
                        String noTerminal = entradaCFG.getKey();
                        Set<String> producciones = entradaCFG.getValue();
                        for (String produccion : producciones) {
                            if (produccion.length() == 2) {
                                String A = produccion.substring(0, 1);
                                String B = produccion.substring(1);
                                if (tabla[i][h] != null && tabla[h][f] != null &&
                                        tabla[i][h].contains(A) && tabla[h][f].contains(B)) {
                                    if (tabla[i][f] == null)
                                        tabla[i][f] = new HashSet<>();
                                    tabla[i][f].add(noTerminal);
                                }
                            }
                        }
                    }
                }
            }
        }

        return (tabla[0][n] != null && tabla[0][n].contains("S")) ? "yes" : "no";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            int k = sc.nextInt();// numero de producciones
            int m = sc.nextInt();// numero de cadenas
            sc.nextLine();

            // se crea la gramatica tomando como key el no terminal y como value las producciones
            Map<String, Set<String>> CFG = new HashMap<>();


            // se llena la gramatica
            for (int j = 0; j < k; j++) {
                String[] produccion = sc.nextLine().split("\\s+");
                String noTerminal = produccion[0];
                Set<String> alternativas = new HashSet<>(Arrays.asList(produccion).subList(1, produccion.length));
                CFG.put(noTerminal, alternativas);
            }

            // empieza a analizar las cadenas
            for (int j = 0; j < m; j++) {
                String x = sc.nextLine();
                String resultado = acepta(x, CFG);
                System.out.println(resultado);
            }
        }
    }
}




