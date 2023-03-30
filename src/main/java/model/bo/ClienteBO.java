package model.bo;

import model.exception.CpfJaUtilizadoException;
import model.exception.EnderecoInvalidoException;
import model.vo.telefonia.Cliente;

public class ClienteBO {

	private ClienteDAO dao = new ClienteDAO();

	public Cliente inserir(Cliente novoCliente) throws CpfJaUtilizadoException, EnderecoInvalidoException {
		if (dao.cpfJaUtilizado(novoCliente.getCpf())) {
			// CASO CPF JA ULTIZADO -> Não salvar e lançar exceção

			throw new CpfJaUtilizadoException("CPF informado já foi utilizado!");
		}
		 if(novoCliente.getEndereco() == null) {
			 throw new EnderecoInvalidoException("Endereco não informado!")
		 }
		// CASO CPF NAO UTILIZADO -> SALVAR
		return dao.inserir(novoCliente);

	}

}
