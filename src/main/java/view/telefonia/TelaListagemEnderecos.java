package view.telefonia;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.EnderecoController;
import model.exception.EnderecoInvalidoException;
import model.vo.telefonia.Endereco;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class TelaListagemEnderecos {

	// ATRIBUTOS DA CLASSE TELA(VISUAIS)

	private JFrame frmListagemDeEnderecos;
	private JTable tblEnderecos;
	String[] nomeColunas = { "#", "Cep", "Rua", "Número", "Bairro", "Cidade", "Estado" };
	private JButton btnBuscarTodos;
	private JButton btnEditar;
	private JButton btnExcluir;

	// LISTA PARA ARMAZENAR ENDERECOS CONSULTADOS NO BANCO
	private ArrayList<Endereco> enderecos;

	// OBJETO USADO PARA ARMAZENAR O ENDERECO QUE O USUARIO SELECIONAR NA
	// TABELA(TBLENDERECOS)
	private Endereco enderecoSelecionado;
	private EnderecoController enderecoController = new EnderecoController();

	// MÉTODOS USADOS NO JTABLE
	private void limparTela() {
		tblEnderecos.setModel(new DefaultTableModel(new Object[][] { nomeColunas, }, nomeColunas));
	}

	private void atualizarTabelaEnderecos() {
		this.limparTela();
		EnderecoController controller = new EnderecoController();
		enderecos = (ArrayList<Endereco>) controller.consultarTodos();

		DefaultTableModel model = (DefaultTableModel) tblEnderecos.getModel();
		// PREENCHER OS VALORES NA TABELA LINHA A LINHA
		for (Endereco e : enderecos) {
			Object[] novaLinhaDaTabela = new Object[7];
			novaLinhaDaTabela[0] = e.getId();
			novaLinhaDaTabela[1] = e.getCep();
			novaLinhaDaTabela[2] = e.getRua();
			novaLinhaDaTabela[3] = e.getNumero();
			novaLinhaDaTabela[4] = e.getCidade();
			novaLinhaDaTabela[5] = e.getBairro();
			novaLinhaDaTabela[6] = e.getEstado();

			model.addRow(novaLinhaDaTabela);
		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaListagemEnderecos window = new TelaListagemEnderecos();
					window.frmListagemDeEnderecos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaListagemEnderecos() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmListagemDeEnderecos = new JFrame();
		frmListagemDeEnderecos.setTitle("ListagemEndereços");
		frmListagemDeEnderecos.setBounds(100, 100, 720, 506);
		frmListagemDeEnderecos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmListagemDeEnderecos.getContentPane().setLayout(null);

		btnBuscarTodos = new JButton("BuscarTodos");
		btnBuscarTodos.setBounds(262, 11, 158, 40);
		btnBuscarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarTabelaEnderecos();
			}
		});
		frmListagemDeEnderecos.getContentPane().add(btnBuscarTodos);

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// MOSTRA A TELACADASTROENDERECO, PASSANDO ENDERECO SELECIONADO COMO PARAMETRO
				TelaCadastroEndereco telaEdicaoEndereco = new TelaCadastroEndereco(enderecoSelecionado);
			}
		});
		btnEditar.setBounds(222, 416, 129, 40);
		frmListagemDeEnderecos.getContentPane().add(btnEditar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(361, 416, 124, 40);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opcaoSelecionada = JOptionPane.showConfirmDialog(null,
						"Confirma a exclusão do endereço selecionado?");

				if (opcaoSelecionada == JOptionPane.YES_OPTION) {
					try {
						enderecoController.excluir(enderecoSelecionado.getId());
						JOptionPane.showMessageDialog(null, "Endereço excluído!", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);

						atualizarTabelaEnderecos();
					} catch (EnderecoInvalidoException excecao) {
						JOptionPane.showMessageDialog(null, excecao.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		frmListagemDeEnderecos.getContentPane().add(btnExcluir);
		frmListagemDeEnderecos.getContentPane().add(btnEditar);
		//BOTÕES INICIAM BLOQUEADOS
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);

		tblEnderecos = new JTable();
		this.limparTela();
		tblEnderecos.setBounds(10, 68, 684, 341);
		
		
		//EVENTO DE CLIQUE EM UMA LINHA DA TABELA
		//HABILITA//DESABILITA OS BOTOES EDITAR E EXCLUIR
		tblEnderecos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indiceSelecionado = tblEnderecos.getSelectedRow();
				
				if(indiceSelecionado > 0) {
					//PRIMEIRA LINHA DA TABELA CONTEM O CABECALHO POR USSO O '-1'
					enderecoSelecionado = enderecos.get(indiceSelecionado - 1); 
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				} else {
					btnEditar.setEnabled(false);
					btnExcluir.setEnabled(false);
				}
			}
		});

		frmListagemDeEnderecos.getContentPane().add(tblEnderecos);
	}
}
