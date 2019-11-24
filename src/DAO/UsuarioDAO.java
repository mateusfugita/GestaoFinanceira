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
        return "{CALL sp_inserir_usuario(?,?,?,?,?,?,?)}";
    }

    @Override
    protected String nomeProcedureUpdate() {
        return "{CALL sp_atualizar_usuario(?,?,?,?,?,?,?)}";
    }

    @Override
    protected CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        CallableStatement stmt = connection.prepareCall(comando);
        UsuarioVO usuarioVO = (UsuarioVO)o;
        stmt.setString("nome", usuarioVO.getNome());
        stmt.setString("conta_usuario", usuarioVO.getLogin());
        stmt.setString("senha", usuarioVO.getSenha());
        stmt.setInt("idade", usuarioVO.getIdade());
        stmt.setFloat("saldo", usuarioVO.getSaldo());
        stmt.setInt("id_perfil", usuarioVO.getIdPerfilGasto());
        stmt.setInt("id", usuarioVO.getId());
        return stmt;
    }
}
