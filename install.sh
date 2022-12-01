#!/bin/bash

clear
echo -e "Olá! Seja bem-vindo ao instalador SafeCommerce!\n\n"
echo -e "Verificando se você já possui o Java instalado...\n\n"
sleep 2

java -version
if [ $? = 0 ]
	then
		echo -e "\n\nVocê já tem o java instalado!"
        sleep 1
	else
		echo -e "\n\nHmm... Você não tem o Java instalado, mas não se preocupe, irei instalar para você!\n\n"
        sleep 2
		sudo apt install default-jre; apt install openjdk-11-jre-headless; -y
fi
echo -e "\n\nAgora que verificamos que vocẽ tem o Java, iremos instalar a nossa aplicação!\n\n"

sleep 2

wget https://github.com/CCO-SafeCommerce/SafeCommerceClient-Java/raw/main/SafeCommerce-client/target/SafeCommerce-client-1.0-SNAPSHOT-jar-with-dependencies.jar

mv ./SafeCommerce-client-1.0-SNAPSHOT-jar-with-dependencies.jar ./SafeCommerce.jar

java -jar SafeCommerce.jar

