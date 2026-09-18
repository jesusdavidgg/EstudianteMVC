package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Vista: JFrame principal del módulo Estudiante.
 * Contiene un campo de búsqueda y una tabla de resultados.
 *
 * IMPORTANTE (MVC): esta clase NO conoce ni importa el Modelo (Estudiante).
 * Solo trabaja con tipos genéricos (Object[], List<Object[]>) que el
 * Controlador le entrega ya preparados. Así la Vista queda desacoplada
 * del Modelo y toda la comunicación pasa por el Controlador.
 */
public class EstudianteView extends JFrame {

    // ── Componentes UI ────────────────────────────────────────────────────────
    private JTextField             txtNombre;
    private JButton                btnBuscar;
    private JTable                 tblResultados;
    private DefaultTableModel      modeloTabla;
    private JLabel                 lblEstado;
    private JButton                btnMostrarTodos;
    
    
    //--Componentes Añadir Estudiantes-----------
    private JTextField txtNombreAgregar;
    private JTextField txtCarreraAgregar;
    private JTextField txtPromedioAgregar;
    private JButton btnAgregar;
     
    //componentes Ordenar Resultados
    private JComboBox<String>      cmbCriterio;
    private JButton                btnOrdenar;
    
    
    // ── Controlador ───────────────────────────────────────────────────────────
    private EstudianteController controlador;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteView() {
        initComponentes();
        initEventos();
    }

    // ── Inicialización de componentes ─────────────────────────────────────────

    private void initComponentes() {
        setTitle("Búsqueda de Estudiantes — MVC NetBeans");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior — barra de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar estudiante"));
        

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(25);
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(59, 139, 212));
        btnBuscar.setForeground(Color.GREEN);
        btnBuscar.setFocusPainted(false);
        
        btnMostrarTodos = new JButton("Mostrar todos");
        btnMostrarTodos.setFocusPainted(false);

        panelBusqueda.add(lblNombre);
        panelBusqueda.add(txtNombre);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnMostrarTodos);
        
        //Panel Central -- Añadir estudiante
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar estudiante"));
        
        JLabel lblNombreAgregar = new JLabel("Nombre:");
        txtNombreAgregar = new JTextField(15);
        JLabel lblCarreraAgregar = new JLabel("Carrera:");
        txtCarreraAgregar = new JTextField(15);
        JLabel lblPromedioAgregar = new JLabel("Promedio:");
        txtPromedioAgregar = new JTextField(5);
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(46, 160, 67));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);

        panelAgregar.add(lblNombreAgregar);
        panelAgregar.add(txtNombreAgregar);
        panelAgregar.add(lblCarreraAgregar);
        panelAgregar.add(txtCarreraAgregar);
        panelAgregar.add(lblPromedioAgregar);
        panelAgregar.add(txtPromedioAgregar);
        panelAgregar.add(btnAgregar);

        // Panel — Ordenar resultados
        JPanel panelOrdenar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelOrdenar.setBorder(BorderFactory.createTitledBorder("Ordenar resultados"));

        JLabel lblCriterio = new JLabel("Criterio:");
        cmbCriterio = new JComboBox<>(new String[]{"Nombre", "Promedio"});
        btnOrdenar = new JButton("Ordenar");
        btnOrdenar.setFocusPainted(false);

        panelOrdenar.add(lblCriterio);
        panelOrdenar.add(cmbCriterio);
        panelOrdenar.add(btnOrdenar);

        
        JPanel panelSuperior = new JPanel(new GridLayout(3, 1));
        panelSuperior.add(panelBusqueda);
        panelSuperior.add(panelAgregar);
        panelSuperior.add(panelOrdenar);

        // Panel inferior — tabla de resultados
        String[] columnas = {"ID", "Nombre", "Carrera", "Promedio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblResultados = new JTable(modeloTabla);
        tblResultados.setRowHeight(24);
        tblResultados.getTableHeader().setReorderingAllowed(false);
        tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tblResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));

        // Panel inferior — estado
        lblEstado = new JLabel("Ingrese un nombre y presione Buscar.");
        lblEstado.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        lblEstado.setForeground(Color.GRAY);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll,        BorderLayout.CENTER);
        add(lblEstado,     BorderLayout.SOUTH);
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    private void initEventos() {
        btnBuscar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                controlador.buscarEstudiante(txtNombre.getText().trim());
            }
        });

        // También buscar al presionar Enter en el campo de texto
        txtNombre.addActionListener((ActionEvent e) -> btnBuscar.doClick());
        btnMostrarTodos.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                controlador.mostrarTodos();
            }
        });

      
        btnAgregar.addActionListener((ActionEvent e) -> onAgregar());
        txtPromedioAgregar.addActionListener((ActionEvent e) -> btnAgregar.doClick());

       
        btnOrdenar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String criterio = (String) cmbCriterio.getSelectedItem();
                controlador.ordenarPor(criterio);
            }
        });
    }
    private void onAgregar() {
        if (controlador == null) {
            return;
        }

        String nombre  = txtNombreAgregar.getText().trim();
        String carrera = txtCarreraAgregar.getText().trim();
        String textoPromedio = txtPromedioAgregar.getText().trim();

        double promedio;
        try {
            promedio = Double.parseDouble(textoPromedio.replace(',', '.'));
        } catch (NumberFormatException ex) {
            mostrarError("El promedio debe ser un número válido (ej: 3.75).");
            return;
        }

        controlador.agregarEstudiante(nombre, carrera, promedio);
    }
    // ── Métodos públicos que llama el Controlador ─────────────────────────────
    // ninguno de estos métodos recibe un Estudiante: reciben
    // Object[] / List<Object[]> ya armados, que es lo único que la Vista
    // necesita saber para pintar la tabla.

    /**
     * Muestra una única fila en la tabla.
     * @param fila arreglo con {id, nombre, carrera, promedioFormateado}
     */
    public void mostrarEstudiante(Object[] fila) {
        limpiarTabla();
        agregarFila(fila);
        setEstado("Se encontró 1 estudiante.");
    }

    /**
     * Muestra varias filas en la tabla.
     * @param filas lista de arreglos {id, nombre, carrera, promedioFormateado}
     */
    public void mostrarEstudiantes(List<Object[]> filas) {
        limpiarTabla();
        if (filas == null || filas.isEmpty()) {
            setEstado("No se encontraron estudiantes con ese criterio.");
            return;
        }
        for (Object[] fila : filas) {
            agregarFila(fila);
        }
        setEstado("Se encontraron " + filas.size() + " estudiante(s).");
    }

    /**
     * Muestra un mensaje de error en la barra de estado.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        setEstado("Error: " + mensaje);
    }
    //confirmar
    public void mostrarConfirmacion(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        setEstado(mensaje);
        limpiarFormularioAgregar();
    }

    /**
     * Devuelve el texto ingresado en el campo de nombre.
     */
    public String getNombreBuscado() {
        return txtNombre.getText().trim();
    }

    // ── Setter del controlador ────────────────────────────────────────────────

    public void setControlador(EstudianteController controlador) {
        this.controlador = controlador;
    }

    // ── Helpers privados ──────────────────────────────────────────────────────

    private void agregarFila(Object[] fila) {
        modeloTabla.addRow(fila);
    }

    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    private void setEstado(String texto) {
        lblEstado.setText(texto);
    }
     private void limpiarFormularioAgregar() {
        txtNombreAgregar.setText("");
        txtCarreraAgregar.setText("");
        txtPromedioAgregar.setText("");
        txtNombreAgregar.requestFocus();
    }
}