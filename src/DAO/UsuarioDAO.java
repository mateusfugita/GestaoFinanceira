package DAO;

import VO.PadraoVO;
import VO.UsuarioVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class UsuarioDAO extends PadraoDAO {
    public UsuarioDAO(){
        setTabela("tbl_Usuario");
        setChave("Id_Usuario");
    }

    @Override
    protected String nomeProcedureInsert() {
        return "{CALL sp_inserir_usuario(?,?,?,?)}";
    }

    @Override
    protected String nomeProcedureUpdate() {
        return "{CALL sp_atualizar_usuario(?,?,?,?,?,?,?)}";
    }

    @Override
    protected CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        CallableStatement stmt = connection.prepareCall(comando);
        UsuarioVO usuarioVO = (UsuarioVO)o;
        stmt.setString("Nome", usuarioVO.getNome());
        stmt.setString("Conta_Usuario", usuarioVO.getLogin());
        stmt.setString("Senha", usuarioVO.getSenha());
        stmt.setInt("Idade", usuarioVO.getIdade());
        stmt.setInt(getChave(), usuarioVO.getId());
        return stmt;
    }
}
