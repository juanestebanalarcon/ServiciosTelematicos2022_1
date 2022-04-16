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