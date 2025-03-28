import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


import javax.swing.*;

public class FrmDibujo extends JFrame {

    Lista lista = new Lista();
    BufferedImage imagen;
    BufferedImage imagen2;
    String[] opciones = { "Linea", "Rectangulo", "Circulo" };
    JComboBox<String> ComboBox;

    private JButton btnSeleccionar;
    private JButton btnEliminar;
    private JButton btnGuardar;
    private JButton btnCargar;
    private JToolBar tbDibujo;
    private JPanel panel;
    int x, y;
    boolean trazando = false;
    Graphics g;

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
                g.drawImage(imagen, 0, 0, this);
                g.drawImage(imagen2, 0, 0, this);
            }
        };

        btnSeleccionar.setIcon(new ImageIcon(getClass().getResource("/iconos/seleccionar.png")));
        btnSeleccionar.setToolTipText("Seleccionar");
        btnSeleccionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSeleccionarClick();
            }

            private void btnSeleccionarClick() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'btnSeleccionarClick'");
            }
        });

        tbDibujo.add(btnSeleccionar);

        panel.setBackground(Color.BLACK);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rayar(e.getX(), e.getY());

            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                rayando(e.getX(), e.getY());

            }
        });

        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminar.png")));
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btnEliminarClick(e);
            }

            private void btnEliminarClick(ActionEvent e) {
            }
        });
        tbDibujo.add(btnEliminar);

        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/iconos/guardar.png")));
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btnGuardarClick(e);
            }

            private void btnGuardarClick(ActionEvent e) {

            }
        });
        tbDibujo.add(btnGuardar);

        btnCargar.setIcon(new ImageIcon(getClass().getResource("/iconos/cargar.png")));
        btnCargar.setToolTipText("Cargar");
        btnCargar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btnCargarClick(e);
            }

            private void btnCargarClick(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'btnCargarClick'");
            }
        });

        tbDibujo.add(btnCargar);
        String nombreArchivo = System.getProperty("user.dir") + "/src/archivos/archivo.txt";

        getContentPane().add(tbDibujo, BorderLayout.SOUTH);
        getContentPane().add(panel, BorderLayout.CENTER);

        imagen = new BufferedImage(700, 500, BufferedImage.TYPE_INT_ARGB);
        imagen2 = new BufferedImage(700, 500, BufferedImage.TYPE_INT_ARGB);

    }

    private void rayando(int x, int y) {

        if (trazando) {
            limpiarimagen2();
            Graphics2D g2 = imagen2.createGraphics();
            g2.setColor(Color.blue);
            int ancho = Math.abs(this.x - x);
            int alto = Math.abs(this.y - y);
            int x1 = Math.min(this.x, x);
            int y1 = Math.min(this.y, y);

            switch (ComboBox.getSelectedIndex()) {
                case 0:
                    g2.drawLine(this.x, this.y, x, y);
                    break;
                case 1:
                    g2.drawRect(x1, y1, ancho, alto);
                    break;
                case 2:
                    g2.drawOval(x1, y1, ancho, alto);
                    break;

            }
            panel.repaint();

        }
    }

    private void rayar(int x, int y) {
        g = imagen.getGraphics();
        g.setColor(Color.white);
        if (!trazando) {
            trazando = true;
            this.x = x;
            this.y = y;
        } else {
            trazando = false;
            Graphics g2 = imagen2.getGraphics();
            g2.setColor(Color.white);
            int ancho = Math.abs(this.x - x);
            int alto = Math.abs(this.y - y);
            int x1 = Math.min(this.x, x);
            int y1 = Math.min(this.y, y);

            switch (ComboBox.getSelectedIndex()) {
                case 0:
                    g.drawLine(this.x, this.y, x, y);
                    g2.drawLine(this.x, this.y, x, y);
                    break;
                case 1:
                    g.drawRect(x1, y1, ancho, alto);
                    g2.drawRect(x1, y1, ancho, alto);
                    break;
                case 2:
                    g.drawOval(x1, y1, ancho, alto);
                    g2.drawOval(x1, y1, ancho, alto);
                    break;

            }
        }
        limpiarimagen2();
        panel.repaint();

    }

    private void limpiarimagen2() {

        Graphics2D g2 = imagen2.createGraphics();
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, imagen2.getWidth(), imagen2.getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
    }

}
