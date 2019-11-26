create database Financeira;
use Financeira;

create table tbl_CatEmpresa(
		Id_Categoria int auto_increment primary key,
		DescCategoria varchar(40) not null
		);
	
create table tbl_Empresa(
	Id_Empresa int auto_increment primary key,
	Nome_Empresa varchar(40) not null,
	Id_Categoria int not null,
	CONSTRAINT Fk_CatEmpresa FOREIGN KEY (Id_Categoria) 
	REFERENCES tbl_CatEmpresa(Id_Categoria)
	);
	-- go
	
create table tbl_Pagamento(
	Id_Pagamento int primary key auto_increment,
	Tipo_Pagamento varchar(40) not null
	);
	-- go
	
create table tbl_PerfilGasto(
	Id_PerfilGasto int primary key auto_increment,
	Descricao varchar(40) not null
	);
	-- go

create table tbl_Usuario(
	Id_Usuario int auto_increment primary key,
	Nome varchar(40) not null,
	Conta_Usuario varchar(15) not null,
	Senha varchar(40) not null,
    Idade int not null,
	Saldo float,
	Id_Perfil int,
	CONSTRAINT Fk_Perfil FOREIGN KEY (Id_Perfil)
	REFERENCES tbl_PerfilGasto(Id_PerfilGasto)
	);
	-- go

create table tbl_Entrada(
	Id_Entrada int auto_increment primary key,
	Valor float not null,
	Data_Entrada date not null,
	Id_Usuario int not null,
	CONSTRAINT Fk_Usuario FOREIGN KEY (Id_Usuario)
	REFERENCES tbl_Usuario(Id_Usuario)
	);
	-- go

create table tbl_Gasto(
	IdGasto int primary key,
	Id_Empresa int not null,
	Data_Gasto date not null,
	Valor float not null,
	Id_Usuario int not null,
	Id_Pagamento int not null,
	CONSTRAINT Fk_Usuario_01 FOREIGN KEY (Id_Usuario) 
	REFERENCES tbl_Usuario(Id_Usuario),
	CONSTRAINT Fk_Empresa FOREIGN KEY (Id_Empresa) 
	REFERENCES tbl_Empresa(Id_Empresa),
	CONSTRAINT Fk_Pagamento FOREIGN KEY (Id_Pagamento)
	REFERENCES tbl_Pagamento(Id_Pagamento)
	);
-- go

INSERT INTO tbl_Pagamento(Tipo_Pagamento) VALUES('Dinheiro');
INSERT INTO tbl_Pagamento(Tipo_Pagamento) VALUES('Débito');
INSERT INTO tbl_Pagamento(Tipo_Pagamento) VALUES('Crédito');
INSERT INTO tbl_Pagamento(Tipo_Pagamento) VALUES('Cheque');

insert into tbl_PerfilGasto(Descricao) values ("Conservador");
insert into tbl_PerfilGasto(Descricao) values ("Moderado");
insert into tbl_PerfilGasto(Descricao) values ("Consumista");

INSERT INTO tbl_CatEmpresa(DescCategoria) VALUES('Alimenticia');
INSERT INTO tbl_CatEmpresa(DescCategoria) VALUES('Transporte');
INSERT INTO tbl_CatEmpresa(DescCategoria) VALUES('Varejo');
INSERT INTO tbl_CatEmpresa(DescCategoria) VALUES('Educação');
INSERT INTO tbl_CatEmpresa(DescCategoria) VALUES('Games');

DELIMITER $$
CREATE TRIGGER trg_Inserir_Entrada AFTER INSERT
ON tbl_Entrada
FOR EACH ROW
BEGIN
	UPDATE tbl_Usuario SET Saldo = NEW.Valor WHERE tbl_Usuario.Id_Usuario = NEW.Id_Usuario;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_deletar (IN nome_tabela varchar(40), IN nome_id varchar(40), IN id int)
BEGIN
	set @ComandoSQL = 'DELETE FROM ? WHERE ? = ?;';
	set @Tabela = nome_tabela;
	set @Campo = nome_id;
	set @Id = CAST(id as CHAR);
	PREPARE query FROM @ComandoSQL;
	EXECUTE query USING @Tabela, @Campo, @Id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_inserir_categoriaEmpresa (IN desc_categoria varchar(40), IN id int)
BEGIN
    INSERT INTO tbl_CatEmpresa (DescCategoria) VALUES (desc_categoria);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_atualizar_categoriaEmpresa (IN desc_categoria varchar(40), IN id int)
BEGIN
    UPDATE tbl_CatEmpresa SET DescCategoria = desc_categoria WHERE Id_Categoria = id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_inserir_empresa (IN nome varchar(40), IN id_categoria int, IN id int)
BEGIN
    INSERT INTO tbl_Empresa (Nome_Empresa, Id_Categoria) VALUES (nome, id_categoria);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_atualizar_empresa (IN nome varchar(40), IN id_categoria int, IN id int)
BEGIN
    UPDATE tbl_CatEmpresa SET Nome_Empresa = nome, Id_Categoria = id_categoria WHERE Id_Empresa = id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_inserir_pagamento (IN tipo_pagamento varchar(40), IN id int)
BEGIN
    INSERT INTO tbl_Pagamento (Tipo_Pagamento) VALUES (tipo_pagamento);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_atualizar_pagamento (IN tipo_pagamento varchar(40), IN id int)
BEGIN
	UPDATE tbl_Pagamento SET Tipo_Pagamento = tipo_pagamento WHERE Id_Pagamento = id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_inserir_perfilGasto (IN descricao varchar(40), IN id int)
BEGIN
    INSERT INTO tbl_PerfilGasto (Descricao) VALUES (descricao);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_atualizar_perfilGasto (IN descricao varchar(40), IN id int)
BEGIN
    UPDATE tbl_PerfilGasto SET Descricao = descricao WHERE Id_PerfilGasto = id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_inserir_usuario (IN nome varchar(40), IN conta_usuario varchar(15), IN senha varchar(40), IN idade int,
									 IN saldo float, IN id_perfil int, IN id int)
BEGIN
    INSERT INTO tbl_Usuario (Nome, Conta_Usuario, Senha, Idade) VALUES (nome, conta_usuario, senha, idade);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_atualizar_usuario (IN nome varchar(40), IN conta_usuario varchar(15), IN senha varchar(40), IN idade int,
									   IN saldo float, IN id_perfil int, IN id int)
BEGIN
	UPDATE tbl_Usuario SET Nome = nome, Conta_Usuario = conta_usuario, Senha = senha, Idade = idade, Saldo = saldo,
						   Id_Perfil = id_perfil WHERE Id_Usuario = id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_inserir_entrada (IN valor float, IN data_entrada date, IN id_usuario int, IN id int)
BEGIN
    INSERT INTO tbl_Entrada (Valor, Data_Entrada, Id_Usuario) VALUES (valor, data_entrada, id_usuario);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_atualizar_entrada (IN valor float, IN data_entrada date, IN id_usuario int, IN id int)
BEGIN
    UPDATE tbl_Entrada SET Valor = valor, Data_Entrada = data_entrada, Id_Usuario = id_usuario WHERE Id_Entrada = id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_inserir_gasto (IN id_empresa int, IN data_gasto date, IN valor float, IN id_usuario int,
								   IN id_pagamento int, IN id int)
BEGIN
    INSERT INTO tbl_Gasto (Id_Empresa, Data_Gasto, Valor, Id_Usuario, Id_Pagamento) VALUES (id_empresa, data_gasto,
						   valor, id_usuario, id_pagamento);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_atualizar_gasto (IN id_empresa int, IN data_gasto date, IN valor float, IN id_usuario int,
								   IN id_pagamento int, IN id int)
BEGIN
    UPDATE tbl_Gasto SET Id_Empresa = id_empresa, Data_Gasto = data_gasto, Valor = valor, Id_Usuario = Id_Usuario,
						 Id_Pagamento = id_pagamento WHERE IdGasto = id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_validar_usuario (IN conta varchar(40), IN pass varchar(40))
BEGIN
	select * from tbl_Usuario where Conta_Usuario = conta and Senha = pass;
END $$
DELIMITER ;