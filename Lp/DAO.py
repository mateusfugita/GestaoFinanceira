import mysql.connector
import pandas as pd
import mysql.connector


def BuscaDados():
    #connection = mysql.connector.connect(host='localhost',database='Financeira',user='root',password='1234')
    connection = mysql.connector.connect(host='localhost',database='Financeira',user='root',password='')
    try:
        print("Conectado")
        sql_select_Query = "select datediff(Data_Gasto, '1934-10-03') as Data , Valor, gt.Id_Usuario, usr.Id_Perfil from tbl_Gasto as gt inner join tbl_Usuario as usr on usr.Id_Usuario = gt.Id_Usuario"
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        resultSelect = pd.DataFrame(records)
        return resultSelect
    finally:
        cursor.close()
        connection.close()

def AtualizaTabela(id, perfil):
        SQL = 'UPDATE tbl_Usuario SET Id_Perfil = ' + str(perfil) + ' WHERE Id_Usuario = ' + str(id) + ';'
        #connection = mysql.connector.connect(host='localhost', database='Financeira', user='root', password='1234')
        connection = mysql.connector.connect(host='localhost', database='Financeira', user='root', password='')
        try:
            print(SQL)
            cursor = connection.cursor().execute(SQL)
            #cursor.execute(SQL)
            connection.cursor().stored_results()
            connection.commit()
            #records = cursor.fetchall()
        finally:
            connection.cursor().close()
            connection.close()
