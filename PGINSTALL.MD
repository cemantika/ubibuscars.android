#################################################
 CONFIGURAÇÃO DO POSTGRESQL PARA O UBIBUS-CARS
#################################################

Instruções para instalação do banco PostGreSQL

1) Download do PostGreSQL

Faça download da versão 9.1.9 32 bits de acordo com seu sistema operacional:  

http://www.enterprisedb.com/products-services-training/pgdownload

Obs. Mesmo se seu sistema for 64 bits, instale a versão 32 bits.
     

2) Instalação

    2.1 Next, Next, Next... na etapa Password, digite uma senha, e Next (guarde ela pois será usada depois); 
    


3) Importando o banco
 
 Baixe o backup do banco:
 
 https://dl.dropboxusercontent.com/u/10935623/CEManTIKA/simtur.backup
 
 
 3.1. No Windows: 
   
  3.1.1. Aperte Windows + R, digite cmd e aperte Enter.
  
  3.1.2. Digite cd "C:\Program Files (x86)\PostgreSQL\9.1\bin\"
  
  3.1.3. Digite psql.exe --username=postgres <  "C:\CAMINHO_DO_BACKUP\ubibuscars.backup"
  
  Obs. Troque CAMINHO_DO_BACKUP pelo caminho do arquivo no seu computador.
  
 3.2  Linux ou Mac
  
  No terminal execute:  psql.exe --username=postgres <  ubibuscars.backup
  
