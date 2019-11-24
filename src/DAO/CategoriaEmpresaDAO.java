package DAO;

import VO.CategoriaEmpresaVO;
import VO.PadraoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class CategoriaEmpresaDAO extends PadraoDAO {
    public CategoriaEmpresaDAO(){
        setTabela("tbl_CatEmpresa");
        setChave("Id_Categoria");
    }
    @Override
    protected String nomeProcedureInsert() {
        return "{CALL sp_inserir_categoriaEmpresa(?,?)}";
    }

    @Override
    protected String nomeProcedureUpdate() {
        return "{CALL sp_atualizar_categoriaEmpresa(?,?)}";
    }

    @Override
    protected CallableStatement criarParametros(Connection connection, PadraoVO o, String comando) throws SQLException {
        CategoriaEmpresaVO categoriaEmpresaVO = (CategoriaEmpresaVO)o;
        CallableStatement stmt = connection.prepareCall(comando);
        stmt.setString("desc_categoria", categoriaEmpresaVO.getDescricao());
        stmt.setInt("id", categoriaEmpresaVO.getId());
        return stmt;
    }
}
