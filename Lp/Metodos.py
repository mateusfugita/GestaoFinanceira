def TestaClasse(Var):
    print(Var)

def Remove_repetidos(lista):
    l = []
    for i in lista:
        if i not in l:
            l.append(i)
    l.sort()
    return l