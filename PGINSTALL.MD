#################################################
 CONFIGURAÇÃO DO POSTGRESQL PARA O UBIBUS-CARS
#################################################

Instruções para instalação do banco PostGreSQL

1. Download do PostGreSQL

 1.1 Faça download da versão 9.1.9 32 bits de acordo com seu sistema operacional:  
     >  http://www.enterprisedb.com/products-services-training/pgdownload 
    Obs. Mesmo se seu sistema for 64 bits, instale a versão 32 bits.
     

2. Instalação

    2.1. Next, Next, Next... até a etapa Password, digite uma senha (guarde ela pois será usada depois e Next, Next;
    2.2. Aguarde;
    2.3. Na etapa Completing the PostgreSQL Setup Wizard deixe marcado o "Stack Builder ..." e Finish;
    2.4. No Setup do Stack Builder, selecione o seu servidor (algo parecido com PostgreSQL 9.1 (x86) on port 5432) e Next;
    2.5. Em Spatial Extensions marque PostGIS 1.5 for PostgreSQL 9.1 (32 bit) v 1.5.5 e Next, Next, Next;
    2.6. Leia o License Agreement (muito importante! :) ) e I Agree;
    2.7. Marque Create spatial database e Next, Next;
    2.8. Coloque em Password a senha que você definiu anteriormente e Next e Install e Yes;
    2.9. Aguarde;
    2.10. Next, Next... Yes... Next... Finish.

3. Importando o banco
 
 3.1 Baixe o backup do banco:
     > https://dl.dropboxusercontent.com/u/10935623/CEManTIKA/simtur.zip
 3.2 Descompacte o arquivo.
 
 3.3. No Windows: 
     3.3.1. Aperte Windows + R, digite cmd e aperte Enter.
     
  3.3.2. Digite cd "C:\Program Files (x86)\PostgreSQL\9.1\bin\"
  3.1.3. Digite psql.exe --username=postgres <  "C:\CAMINHO_DO_BACKUP\ubibuscars.backup"
     Obs. Troque CAMINHO_DO_BACKUP pelo caminho do arquivo no seu computador.
  
 3.4  Linux ou Mac
  3.4.1 No terminal execute:  psql.exe --username=postgres <  ubibuscars.backup
  
4.0. Configurando a aplicação
  
  4.1 Edite o arquivo criaConexao.php, e substitua 200.128.51.48 por localhost, e o password pela senha que você escolheu.
    > $con=pg_connect("host='200.128.51.48' port='5432' dbname='simtur' user='postgres' password='senha'") or die ("Não foi possível conectar o banco");

4.1. Fazendo alterações no banco

  Use a ferramenta PgAdmin, veja a documentação: http://www.pgadmin.org/docs/1.16/index.html
