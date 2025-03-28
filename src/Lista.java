import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;



public class Lista {
    /*private Nodo cabeza;
   
private List<Nodo> nodos;
   public Lista() {
    nodos = new ArrayList<Nodo>();
   }
    
    public void agregar(Nodo nodo){
        nodos.add(nodo);
    }
    public void eliminarNodo(Nodo n) {
        if (n != null && cabeza != null) {

            Nodo antecesor = null;
            Nodo apuntador = cabeza;
            while (apuntador != null && apuntador != n) {
                antecesor = apuntador;
                apuntador = apuntador.siguiente;
            }
            if (apuntador != null) {
                if (antecesor == null) {
                    apuntador = apuntador.siguiente;

                } else {
                    antecesor.siguiente = apuntador.siguiente;
                }

            }
        nodos.remove(n);
        }
    }

    public List<Nodo> getNodos() {
        return nodos;
    }


    /*public void agregar(Nodo trazo) {
        if (trazo != null) {
            if (cabeza == null) {
                cabeza = trazo;
            } else {
                Nodo apuntador = cabeza;
                while (apuntador.siguiente != null) {
                    apuntador = apuntador.siguiente;

                }
                apuntador.siguiente = trazo;

            }
            trazo.siguiente = null;
        }
    }

    public void mostrar() {
        Nodo apuntador = cabeza;
        int ind = 0;
        while (apuntador != null) {
            apuntador = apuntador.siguiente;
            ind++;
        }
    }
    public void eliminar(Nodo n) {
        if (n != null && cabeza != null) {

            Nodo antecesor = null;
            Nodo apuntador = cabeza;
            while (apuntador != null && apuntador != n) {
                antecesor = apuntador;
                apuntador = apuntador.siguiente;
            }
            if (apuntador != null) {
                if (antecesor == null) {
                    apuntador = apuntador.siguiente;

                } else {
                    antecesor.siguiente = apuntador.siguiente;
                }

            }

        }
    }
    public void desdeArchivo(String nombreArchivo) {
        cabeza = null;
        BufferedReader br = Archivo.abrirArchivo(nombreArchivo);
        if (br != null) {
            try {
                String linea = br.readLine();
                while (linea != null) {
                    String[] datos = linea.split(";");
                    if (datos.length >= 5) {
                        //Nodo n = new Nodo();
                       // seleccionar(n);
                    }
                    linea = br.readLine();
                }

            } catch (IOException ex) {

            } catch (Exception ex) {

            }
        }
    }*/
    private Nodo cabeza;

    public Lista() {
        cabeza = null; // Inicialmente, la lista está vacía
    }

    public void agregar(Nodo nodo) {
        if (cabeza == null) {
            cabeza = nodo; // Si la lista está vacía, el nuevo nodo es la cabeza
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente; // Avanzar al último nodo
            }
            actual.siguiente = nodo; // Enlazar el nuevo nodo al final de la lista
        }
    }

    public Nodo getCabeza() {
        return cabeza; // Método para obtener la cabeza de la lista
    }

    public List<Nodo> getNodos() {
        List<Nodo> nodos = new ArrayList<>();
        Nodo actual = cabeza;
        while (actual != null) {
            nodos.add(actual);
            actual = actual.siguiente; // Avanzar al siguiente nodo
        }
        return nodos;
    }

    public void eliminarNodo(Nodo n) {
        if (n != null && cabeza != null) {
            if (cabeza == n) {
                cabeza = cabeza.siguiente; // Si el nodo a eliminar es la cabeza
            } else {
                Nodo actual = cabeza;
                while (actual.siguiente != null && actual.siguiente != n) {
                    actual = actual.siguiente; // Buscar el nodo anterior
                }
                if (actual.siguiente == n) {
                    actual.siguiente = n.siguiente; // Enlazar el nodo anterior al siguiente del nodo a eliminar
                }
            }
        }
    }
}

