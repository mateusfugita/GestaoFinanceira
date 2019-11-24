import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sb
from sklearn.cluster import KMeans
import mysql.connector
import Metodos as metodos
import DAO as dao

log = open('log.txt', 'w')
log.close()
log = open('log.txt', 'r')
conteudo = log.readlines()
log.close()
conteudo.append('Processo iniciado \n')
try:
    resultSelect = dao.BuscaDados()
    conteudo.append('Dados obtidos com sucesso \n')
    resultSelect = resultSelect.rename(columns={0: 'Data', 1: 'Valor', 2:'Id_Usuario', 3:'Id_Perfil'})
    print(resultSelect)

    ListaDatas = list(resultSelect['Data'])
    ListaUsuarios = list(resultSelect['Id_Usuario'])

    print(ListaDatas)
    try:
        print(ListaDatas)
        resultSelect = resultSelect.drop('Data', axis=1)
        resultSelect = resultSelect.drop('Id_Usuario', axis=1)
        resultSelect['Data'] = ListaDatas
        x = np.array(resultSelect)
        print(resultSelect)
        print(x)
        conteudo.append('Treino preparado \n')
    except:
        conteudo.append('Falha ao preparar os dados \n')

    try:
        kmeans = KMeans(n_clusters=3, random_state=0)
        kmeans.fit(x)
        conteudo.append('Treino executado com sucesso \n')
        print(kmeans.labels_)
        print(type(kmeans.labels_))
    except:
        conteudo.append('Erro ao realizar o treino \n')

    lista = list(kmeans.labels_)
    lis = []
    for i in lista:
        if i == 0:
            lis.append("Conservador")
        if i == 1:
            lis.append("Moderado")
        if i == 2:
            lis.append("Consumista")

    resultSelect["Categoria"] = lis
    resultSelect['Id_Usuario'] = ListaUsuarios
    print(resultSelect)

    Categoria = []
    ListaUsuarios = metodos.Remove_repetidos(ListaUsuarios)
    for i in metodos.Remove_repetidos(ListaUsuarios):
        TabelaConservador = resultSelect.loc[
            (resultSelect["Categoria"] == "Conservador") & (resultSelect["Id_Usuario"] == i), ["Categoria",                                                                                "Id_Usuario"]]
        TabelaModerado = resultSelect.loc[
            (resultSelect["Categoria"] == "Moderado") & (resultSelect["Id_Usuario"] == i), ["Categoria", "Id_Usuario"]]
        TabelaAgressivo = resultSelect.loc[
            (resultSelect["Categoria"] == "Consumista") & (resultSelect["Id_Usuario"] == i), ["Categoria",
                                                                                              "Id_Usuario"]]
        aux = [TabelaConservador.Categoria.count(), TabelaModerado.Categoria.count(), TabelaAgressivo.Categoria.count()]
        print(aux)
        if (max(aux) == aux[0]):
            print('Usuario:', i, 'é Conservador')
            Categoria.append(1)
        if (max(aux) == aux[1]):
            print('Usuario:', i, 'é Moderado')
            Categoria.append(2)
        if (max(aux) == aux[2]):
            print('Usuario:', i, 'é Consumista')
            Categoria.append(3)

    print(ListaUsuarios)
    print(Categoria)

    Usuarios_Categoria = list(zip(ListaUsuarios, Categoria))
    print(Usuarios_Categoria[0][0])
    print(Usuarios_Categoria[0][1])

    n = 0
    try:
        for i in Usuarios_Categoria:
            dao.AtualizaTabela(Usuarios_Categoria[n][0], Usuarios_Categoria[n][1])
            n += 1
        conteudo.append('Update realizado com sucesso \n')
    except:
        conteudo.append('Erro no update \n')

except:
    conteudo.append('Erro ao buscar os dados \n')

finally:
    conteudo.append('Processo finalizado \n')


log = open('log.txt', 'w')
log.writelines(conteudo)    # escreva o conteúdo criado anteriormente nele.
log.close()




