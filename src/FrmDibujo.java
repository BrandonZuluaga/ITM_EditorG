import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FrmDibujo extends JFrame {

    Lista lista = new Lista();
    Nodo nodoSeleccionado = null;
    BufferedImage imagen;
    String[] opciones = {"Linea", "Rectangulo", "Circulo"};
    JComboBox<String> ComboBox;

    private JButton btnSeleccionar;
    private JButton btnEliminar;
    private JButton btnGuardar;
    private JButton btnCargar;
    private JToolBar tbDibujo;
    private JPanel panel;
    int x, y;
    boolean trazando = false;

    public FrmDibujo() {
        tbDibujo = new JToolBar();
        ComboBox = new JComboBox<>(opciones);
        btnSeleccionar = new JButton();
        btnEliminar = new JButton();
        btnGuardar = new JButton();
        btnCargar = new JButton();

        tbDibujo.add(ComboBox);

        setSize(700, 500);
        setTitle("Dibujos Vectoriales");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarTrazos(g);
            }
        };

        btnSeleccionar.setIcon(new ImageIcon(getClass().getResource("/iconos/seleccionar.png")));
        btnSeleccionar.setToolTipText("Seleccionar");
        btnSeleccionar.addActionListener(e -> btnSeleccionarClick());
        tbDibujo.add(btnSeleccionar);

        panel.setBackground(Color.BLACK);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                trazando = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (trazando) {
                    int x2 = e.getX();
                    int y2 = e.getY();
                    String tipo = opciones[ComboBox.getSelectedIndex()];
                    lista.agregar(new Nodo(tipo, x, y, x2, y2));
                    trazando = false;
                    panel.repaint();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarTrazo(e.getX(), e.getY());
            }
        });

        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminar.png")));
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.addActionListener(e -> btnEliminarClick());
        tbDibujo.add(btnEliminar);

        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/iconos/guardar.png")));
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.addActionListener(e -> btnGuardarClick());
        tbDibujo.add(btnGuardar);

        btnCargar.setIcon(new ImageIcon(getClass().getResource("/iconos/cargar.png")));
        btnCargar.setToolTipText("Cargar");
        btnCargar.addActionListener(e -> btnCargarClick());
        tbDibujo.add(btnCargar);

        getContentPane().add(tbDibujo, BorderLayout.SOUTH);
        getContentPane().add(panel, BorderLayout.CENTER);

        imagen = new BufferedImage(700, 500, BufferedImage.TYPE_INT_ARGB);
    }

    private void seleccionarTrazo(int x, int y) {
        for (Nodo n : lista.getNodos()) {
            if (x >= Math.min(n.x1, n.x2) && x <= Math.max(n.x1, n.x2) &&
                    y >= Math.min(n.y1, n.y2) && y <= Math.max(n.y1, n.y2)) {
                nodoSeleccionado = n;
                panel.repaint();
                return;
            }
        }
        nodoSeleccionado = null;
        panel.repaint();
    }

    private void btnSeleccionarClick() {
        nodoSeleccionado = null;
        panel.repaint();
    }

    private void btnEliminarClick() {
        if (nodoSeleccionado != null) {
            lista.eliminarNodo(nodoSeleccionado);
            nodoSeleccionado = null;
            panel.repaint();
        }
    }

    private void btnGuardarClick() {
        String rutaArchivo = Archivo.elegirArchivo();
        if (!rutaArchivo.isEmpty()) {
            if (!rutaArchivo.endsWith(".dbj")) {
                rutaArchivo += ".dbj"; // Asegurar extensión
            }

            // Convertir la lista de nodos en un arreglo de Strings para guardarlo
            String[] lineas = lista.getNodos().stream()
                    .map(n -> n.tipoTrazo + ";" + n.x1 + ";" + n.y1 + ";" + n.x2 + ";" + n.y2)
                    .toArray(String[]::new);

            if (Archivo.guardarArchivo(rutaArchivo, lineas)) {
                JOptionPane.showMessageDialog(this, "Dibujo guardado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo.");
            }
        }
    }

    private void btnCargarClick() {
        String rutaArchivo = Archivo.elegirArchivo();
        if (!rutaArchivo.isEmpty()) {
            BufferedReader br = Archivo.abrirArchivo(rutaArchivo);
            if (br != null) {
                lista = new Lista(); // Reiniciar la lista de nodos
                try {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        String[] datos = linea.split(";");
                        if (datos.length == 5) {
                            lista.agregar(new Nodo(datos[0], Integer.parseInt(datos[1]), Integer.parseInt(datos[2]),
                                    Integer.parseInt(datos[3]), Integer.parseInt(datos[4])));
                        }
                    }
                    br.close();
                    panel.repaint();
                    JOptionPane.showMessageDialog(this, "Dibujo cargado exitosamente.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al leer el archivo.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo abrir el archivo.");
            }
        }
    }


    private void dibujarTrazos(Graphics g) {
        for (Nodo n : lista.getNodos()) {
            g.setColor(n == nodoSeleccionado ? Color.RED : Color.WHITE);
            switch (n.tipoTrazo) {
                case "Linea":
                    g.drawLine(n.x1, n.y1, n.x2, n.y2);
                    break;
                case "Rectangulo":
                    g.drawRect(Math.min(n.x1, n.x2), Math.min(n.y1, n.y2),
                            Math.abs(n.x2 - n.x1), Math.abs(n.y2 - n.y1));
                    break;
                case "Circulo":
                    g.drawOval(Math.min(n.x1, n.x2), Math.min(n.y1, n.y2),
                            Math.abs(n.x2 - n.x1), Math.abs(n.y2 - n.y1));
                    break;
            }
        }
    }
}