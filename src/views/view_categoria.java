package views;

import controllers.controller_categoria;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.model_categoria;

/**
 *
 * @author Eymar
 */
public class view_categoria extends JFrame {

    private JTextField txtClave;
    private JTextField txtNombre;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JTable table;
    private controller_categoria categoriaDAO;
    private DefaultTableModel tableModel;

    public view_categoria(Connection conn) {
        categoriaDAO = new controller_categoria(conn);

        setTitle("CRUD de Categorías");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2));
        formPanel.setBorder(BorderFactory.createTitledBorder("Categoría"));

        JLabel lblClave = new JLabel("Clave:");
        txtClave = new JTextField();
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        formPanel.add(lblClave);
        formPanel.add(txtClave);
        formPanel.add(lblNombre);
        formPanel.add(txtNombre);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new String[]{"Clave", "Fecha Creado", "Nombre", "Activo"}, 0);
        table = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(table);
        mainPanel.add(tableScrollPane, BorderLayout.SOUTH);

        cargarCategoriasEnTabla();

        add(mainPanel);

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarCategoria();
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarCategoria();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarCategoria();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void cargarCategoriasEnTabla() {
        List<model_categoria> categorias = categoriaDAO.listarCategorias();
        tableModel.setRowCount(0);
        for (model_categoria categoria : categorias) {
            tableModel.addRow(new Object[]{categoria.getClave(), categoria.getFechaCreado(), categoria.getNombre(),
                categoria.getActivo()});
        }
    }

    private void agregarCategoria() {
        String clave = txtClave.getText();
        String nombre = txtNombre.getText();
        Long fechaCreado = System.currentTimeMillis();
        boolean activo = true;

        model_categoria categoria = new model_categoria(clave, fechaCreado, nombre, activo);

        if (categoriaDAO.agregarCategoria(categoria)) {
            cargarCategoriasEnTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar la categoría.");
        }
    }

    private void editarCategoria() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría para editar.");
            return;
        }

        String nuevaNombre = txtNombre.getText();
        String clave = (String) table.getValueAt(filaSeleccionada, 0);

        Long fechaCreado = (Long) table.getValueAt(filaSeleccionada, 1);
        boolean activo = (boolean) table.getValueAt(filaSeleccionada, 3);

        model_categoria categoria = new model_categoria(clave, fechaCreado, nuevaNombre, activo);

        if (categoriaDAO.editarCategoria(categoria)) {
            cargarCategoriasEnTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al editar la categoría.");
        }
    }

    private void eliminarCategoria() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría para eliminar.");
            return;
        }

        String clave = (String) table.getValueAt(filaSeleccionada, 0);

        if (categoriaDAO.eliminarCategoria(clave)) {
            cargarCategoriasEnTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar la categoría.");
        }
    }

    private void limpiarCampos() {
        txtClave.setText("");
        txtNombre.setText("");
    }

    public static void main(String[] args) {
        Connection conn = null;

        SwingUtilities.invokeLater(() -> {
            view_categoria app = new view_categoria(conn);
            app.setVisible(true);
        });
    }
}
