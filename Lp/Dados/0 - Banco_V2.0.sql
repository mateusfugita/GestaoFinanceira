-- use master;
create database Financeira;
use Financeira;


create table tbl_CatEmpresa(
		Id_Categoria int auto_increment primary key,
		DescCategoria varchar(40) not null
		);
	
		
create table tbl_Empresa(
	Id_Empresa int auto_increment primary key,
	Nome_Empresa varchar(40) not null,
	-- Logo_Empresa image not null,
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
	Id_PerfilGasto int primary key,
	Descricao varchar(40) not null
	);
	-- go

create table tbl_Usuario(
	Id_Usuario int auto_increment primary key,
	Conta_Usuario varchar(15) not null,
	Senha varchar(40),
    	Idade int not null,
	Saldo float not null,
	Id_Perfil int not null,
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

DELIMITER $$
CREATE TRIGGER trg_Inserir_Entrada AFTER INSERT
ON tbl_Entrada
FOR EACH ROW
BEGIN
	UPDATE tbl_Usuario SET Saldo = NEW.Valor WHERE tbl_Usuario.Id_Usuario = NEW.Id_Usuario;
END $$
DELIMITER ;






