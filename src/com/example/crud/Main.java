package com.example.crud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private final JFrame frame;
    private final JTextField idField;
    private final JTextField nomeField;
    private final JTextField emailField;
    private final DefaultTableModel tableModel;
    private final List<Usuario> usuarios;

    public Main() {
        usuarios = new ArrayList<>();

        frame = new JFrame("CRUD Simples");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(20);
        formPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        nomeField = new JTextField(20);
        formPanel.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton addButton = new JButton("Adicionar");
        JButton updateButton = new JButton("Atualizar");
        JButton deleteButton = new JButton("Excluir");
        JButton clearButton = new JButton("Limpar");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        String[] columns = {"ID", "Nome", "Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> adicionarUsuario());
        updateButton.addActionListener(e -> atualizarUsuario(table.getSelectedRow()));
        deleteButton.addActionListener(e -> excluirUsuario(table.getSelectedRow()));
        clearButton.addActionListener(e -> limparCampos());

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                carregarUsuarioSelecionado(table.getSelectedRow());
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void adicionarUsuario() {
        String id = idField.getText().trim();
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();

        if (id.isEmpty() || nome.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                JOptionPane.showMessageDialog(frame, "ID já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Usuario novoUsuario = new Usuario(id, nome, email);
        usuarios.add(novoUsuario);
        tableModel.addRow(new Object[]{id, nome, email});
        limparCampos();
    }

    private void atualizarUsuario(int rowIndex) {
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(frame, "Selecione um usuário para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = idField.getText().trim();
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();

        if (id.isEmpty() || nome.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = usuarios.get(rowIndex);
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);

        tableModel.setValueAt(id, rowIndex, 0);
        tableModel.setValueAt(nome, rowIndex, 1);
        tableModel.setValueAt(email, rowIndex, 2);

        limparCampos();
    }

    private void excluirUsuario(int rowIndex) {
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(frame, "Selecione um usuário para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        usuarios.remove(rowIndex);
        tableModel.removeRow(rowIndex);
        limparCampos();
    }

    private void carregarUsuarioSelecionado(int rowIndex) {
        if (rowIndex < 0) {
            return;
        }

        Usuario usuario = usuarios.get(rowIndex);
        idField.setText(usuario.getId());
        nomeField.setText(usuario.getNome());
        emailField.setText(usuario.getEmail());
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
