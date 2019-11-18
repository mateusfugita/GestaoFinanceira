package DAO;

import VO.PadraoVO;
import VO.UsuarioVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class UsuarioDAO extends PadraoDAO {
    public UsuarioDAO(){
        setTabela("Usuario");
        setChave("UsuarioId");
    }

    @Override
    protected String nomeProcedureInsert() {
        return "Insere_Usuario";
    }

    @Override
    protected String nomeProcedureUpdate() {
        return "Altera_Usuario";
    }

    @Override
    public CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        CallableStatement stmt = connection.prepareCall(comando);
        UsuarioVO usuarioVO = (UsuarioVO)o;
        stmt.setInt("usuarioId", usuarioVO.getId());
        stmt.setString("nome", usuarioVO.getNome());
        stmt.setString("conta_usuario", usuarioVO.getLogin());
        stmt.setString("senha", usuarioVO.getSenha());
        stmt.setInt("idPerfilGasto", usuarioVO.getIdPerfilGasto());
        return stmt;
    }
}
