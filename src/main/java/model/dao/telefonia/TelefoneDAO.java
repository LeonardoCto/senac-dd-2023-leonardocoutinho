package model.dao.telefonia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.Banco;
import model.vo.telefonia.Telefone;

public class TelefoneDAO {
	
	
	public Telefone inserir(Telefone novoTelefone) {
		
		Connection conexao = Banco.getConnection();
		String sql = " INSERT INTO TELEFONE (ID_CLIENTE, DDD, NUMERO, ATIVO, MOVEL )"
			    + " VALUES(?,?,?,?,?) ";
		
		PreparedStatement query = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			query.setInt(1, novoTelefone.getIdCliente());
			query.setString(2, novoTelefone.getDdd());
			query.setString(3, novoTelefone.getNumero());			
			query.setBoolean(4, novoTelefone.isAtivo());
			query.setBoolean(5, novoTelefone.isMovel());
			query.execute();
			
			//PREENCHER O ID GERADO NO BANCO NO OBJETO
			ResultSet resultado = query.getGeneratedKeys();
			if(resultado.next()) { 
				novoTelefone.setId(resultado.getInt(1));
			}
					
			} catch (SQLException e ) { 
				System.out.println("Erro ao inserir telefone. "
						+ " \nCausa: " + e.getMessage());
			}finally {
				//FECHAR A CONEXÃO
				Banco.closePreparedStatement(query);
				Banco.closeConnection(conexao);
			}
		
		
		return novoTelefone;
				
	}

}
