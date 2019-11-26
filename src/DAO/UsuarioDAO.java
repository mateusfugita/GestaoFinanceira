package DAO;

import VO.PadraoVO;
import VO.UsuarioVO;

import javax.xml.transform.Result;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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

    private boolean validaIdPerfil(ResultSet retorno){
        try{
            if(retorno.getString("Id_Perfil").trim().length() > 0){
                return true;
            }

            return false;
        }
        catch (Exception e){
            return false;
        }
    }

    public UsuarioVO criaUsuario(ResultSet retorno) throws SQLException {
        UsuarioVO usuario = UsuarioVO.getInstance();
        usuario.setId(Integer.parseInt(retorno.getString("Id_Usuario")));
        usuario.setNome(retorno.getString("Nome"));
        usuario.setLogin(retorno.getString("Conta_Usuario"));
        usuario.setSenha(retorno.getString("Senha"));
        usuario.setIdade(Integer.parseInt(retorno.getString("Idade")));
        if(validaIdPerfil(retorno)){
            usuario.setIdPerfilGasto(Integer.parseInt(retorno.getString("Id_Perfil")));
        }

        return usuario;
    }
}
