Instruções:

(usando o eclipse com o plugin adt)


1. O aplicativo é compatível com as versões 2.2 até 4.2 do android. 

2. Após importar o projeto para o eclipse pode ser necessário setar o build target, (project->properties->android) e escolher a versao mais nova do sdk (4.2.2 no momento).

3. O aplicativo usa google maps entao é necessário baixar os serviçoes da google play no sdk manger e importá-lo pelo eclipse pelo menu import->android->existing android code into workspace. Após isso, deve- se ir em project->properties->android e clicar em add, na áre referente a library.

4. Os mapas não funcionam no simulador.

5. Para usar o google maps é necessário fazer a chave de acesso e colocá-la no android manifest. O link abaixo explica o processo:
https://developers.google.com/maps/documentation/android/start?hl=pt-br
