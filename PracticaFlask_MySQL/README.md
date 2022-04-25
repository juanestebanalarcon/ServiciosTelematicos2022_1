# Vagrantfile utilizado para la práctica:

```bash
Vagrant.configure("2") do |config|
if Vagrant.has_plugin?("vagrant-vbguest")
    config.vbguest.auto_update = false 
end
config.vm.define :servidor do |servidor|
servidor.vm.box = "centos/stream8"
servidor.vm.network :private_network, ip: "192.168.50.5"
servidor.vm.hostname = "servidor"
end
end
```

## Paquetes instalados:

```bash
yum install vim httpd python3 epel-release python3-devel -y
sudo dnf install python3-mod_wsgi -y
pip3 install Flask
pip3 install apispec
pip3 install apispec-webframeworks
pip3 install marshmallow
sudo yum update

sudo wget https://dev.mysql.com/get/mysql80-community-release-el7-1.noarch.rpm
sudo rpm -Uvh mysql80-community-release-el7-1.noarch.rpm
sudo yum install mysql-server
sudo yum install mysql-devel
sudo yum install python3-devel
sudo yum install gcc
sudo pip3 install flask-mysqldb
sudo pip3 install Flask-WTF
sudo pip3 install passlib
```
## Configuraciones necesarias para MySQL

```bash
sudo systemctl start mysqld
sudo systemctl status mysqld
mysql -u root -p <password>
ALTER USER 'root'@'localhost' IDENTIFIED BY 'Autonoma123*';
create database prcFlask;
use prcFlask

CREATE TABLE users(id INT(11) AUTO_INCREMENT PRIMARY KEY, 
name VARCHAR(100), email VARCHAR(100),
username VARCHAR(30), password VARCHAR(100), register_date
TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

CREATE TABLE articles(id INT(11) AUTO_INCREMENT PRIMARY KEY, 
title VARCHAR(100),
body VARCHAR(30),
crration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

```
## Configuraciones necesarias en el archivo httpd.conf

```bash
vim /etc/httpd/conf/httpd.conf
WSGIScriptAlias / /var/www/<directorio_proyecto>/application.wsgi

<VirtualHost *>
ServerName 192.168.50.5
<Directory /var/www/PracticaFlask/>
Order Allow,Deny
Allow From all
</Directory>
</VirtualHost>
```

- Para levantar la API:

```bash
export FLASK_ENV=development
python3 -m flask run --host=0.0.0.0
```