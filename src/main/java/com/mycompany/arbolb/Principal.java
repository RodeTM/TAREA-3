package com.mycompany.arbolb;

import java.util.Scanner;

public class Principal {

    static class NodoB {
        int[] claves;
        int t;
        NodoB[] hijos;
        int n;
        boolean hoja;

        public NodoB(int t, boolean hoja) {
            this.t = t;
            this.hoja = hoja;
            this.claves = new int[2 * t - 1];
            this.hijos = new NodoB[2 * t];
            this.n = 0;
        }

        public void recorrer() {
            for (int i = 0; i < n; i++) {
                if (!hoja) {
                    hijos[i].recorrer();
                }
                System.out.print(claves[i] + " ");
            }
            if (!hoja) {
                hijos[n].recorrer();
            }
        }

        public NodoB buscar(int k) {
            int i = 0;
            while (i < n && k > claves[i]) {
                i++;
            }
            if (i < n && claves[i] == k) {
                return this;
            }
            if (hoja) {
                return null;
            }
            return hijos[i].buscar(k);
        }

        public void insertarNoLleno(int k) {
            int i = n - 1;
            if (hoja) {
                while (i >= 0 && claves[i] > k) {
                    claves[i + 1] = claves[i];
                    i--;
                }
                claves[i + 1] = k;
                n++;
            } else {
                while (i >= 0 && claves[i] > k) {
                    i--;
                }
                if (hijos[i + 1].n == 2 * t - 1) {
                    dividirHijo(i + 1, hijos[i + 1]);
                    if (claves[i + 1] < k) {
                        i++;
                    }
                }
                hijos[i + 1].insertarNoLleno(k);
            }
        }

        public void dividirHijo(int i, NodoB y) {
            NodoB z = new NodoB(y.t, y.hoja);
            z.n = t - 1;
            for (int j = 0; j < t - 1; j++) {
                z.claves[j] = y.claves[j + t];
            }
            if (!y.hoja) {
                for (int j = 0; j < t; j++) {
                    z.hijos[j] = y.hijos[j + t];
                }
            }
            y.n = t - 1;
            for (int j = n; j >= i + 1; j--) {
                hijos[j + 1] = hijos[j];
            }
            hijos[i + 1] = z;
            for (int j = n - 1; j >= i; j--) {
                claves[j + 1] = claves[j];
            }
            claves[i] = y.claves[t - 1];
            n++;
        }

        public void eliminar(int k) {
            // Implementar eliminación
            System.out.println("Eliminación aún no implementada.");
        }
    }

    static class ArbolB {
        NodoB raiz;
        int t;

        public ArbolB(int t) {
            this.t = t;
            raiz = null;
        }

        public void recorrer() {
            if (raiz != null) {
                raiz.recorrer();
                System.out.println();
            } else {
                System.out.println("El arbol está vacío.");
            }
        }

        public boolean buscar(int k) {
            return raiz != null && raiz.buscar(k) != null;
        }

        public void insertar(int k) {
            if (raiz == null) {
                raiz = new NodoB(t, true);
                raiz.claves[0] = k;
                raiz.n = 1;
            } else {
                if (raiz.n == 2 * t - 1) {
                    NodoB s = new NodoB(t, false);
                    s.hijos[0] = raiz;
                    s.dividirHijo(0, raiz);

                    int i = 0;
                    if (s.claves[0] < k) {
                        i++;
                    }
                    s.hijos[i].insertarNoLleno(k);
                    raiz = s;
                } else {
                    raiz.insertarNoLleno(k);
                }
            }
        }

        public void eliminar(int k) {
            if (raiz != null) {
                raiz.eliminar(k);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el grado minimo del arbol B (t): ");
        int grado = sc.nextInt();

        ArbolB arbol = new ArbolB(grado);

        int opcion;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Insertar clave");
            System.out.println("2. Buscar clave");
            System.out.println("3. Eliminar clave");
            System.out.println("4. Recorrer arbol");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la clave a insertar: ");
                    int clave = sc.nextInt();
                    arbol.insertar(clave);
                    break;
                case 2:
                    System.out.print("Ingrese la clave a buscar: ");
                    int claveBuscar = sc.nextInt();
                    if (arbol.buscar(claveBuscar)) {
                        System.out.println("Clave encontrada.");
                    } else {
                        System.out.println("Clave no encontrada.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese la clave a eliminar: ");
                    int claveEliminar = sc.nextInt();
                    arbol.eliminar(claveEliminar);
                    break;
                case 4:
                    System.out.println("Recorrido del arbol:");
                    arbol.recorrer();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);

        sc.close();
    }
}
