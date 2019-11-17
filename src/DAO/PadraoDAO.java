package DAO;

import VO.PadraoVO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class PadraoDAO {
    protected abstract String nomeProcedureInsert();
    protected abstract String nomeProcedureUpdate();
    //protected abstract String nomeProcedureDelete();

    private String Tabela;
    private String Chave;

    public void inserir(PadraoVO vo) throws SQLException{
        try(Connection c = ConexaoBD.getInstance().getConexao()){
            //CallableStatement stmt = c.prepareCall((nomeProcedureInsert()));
            criarParametros(c, vo).executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void atualizar(PadraoVO vo){
        try(Connection c = ConexaoBD.getInstance().getConexao()){
            //CallableStatement stmt = c.prepareCall((nomeProcedureUpdate()));
            criarParametros(c, vo).executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deletar(int id) throws SQLException {
        try(Connection c = ConexaoBD.getInstance().getConexao()) {
            String query = "{CALL Deletar_BD(?, ?)}";
            CallableStatement stmt = c.prepareCall(query);
            stmt.setString("Tabela", getTabela());
            stmt.setString("Chave", getChave());
            stmt.setInt("id", id);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public abstract CallableStatement criarParametros(Connection connection, PadraoVO o) throws SQLException;

    protected String getTabela() {
        return Tabela;
    }

    protected void setTabela(String tabela) {
        Tabela = tabela;
    }

    protected String getChave() {
        return Chave;
    }

    protected void setChave(String chave) {
        Chave = chave;
    }
}
