# Balanceador de carga de servidores web <<Apache mod_proxy_balancer>>

## Pruebas a realizar con JMeter:

1. Realice pruebas de carga variando los parámetros de las pruebas y analice
que sucede.

2. Agregue diferentes números de servidores web y verifique como responde el
sistema.

3. Explore las diferentes estadísticas que arroje el balanceador de carga. Tales
como numero de peticiones resueltas, taza de llegada de peticiones, porcentaje de fallas en la atención de peticiones, etc.

## Pasos para el desarrollo del proyecto.

1. Inicializar un nuevo ambiente de vagrant

```bash
vagrant init
```
2. Abrir el archivo Vagrantfile y modificar con la siguiente configuración:

```bash
Vagrant.configure("2") do |config|
if Vagrant.has_plugin?("vagrant-vbguest")
    config.vbguest.auto_update = false 
end
config.vm.define :servidor1 do |servidor1|
servidor1.vm.box = "bento/centos-7.9"
servidor1.vm.network :private_network, ip: "192.168.50.10"
servidor1.vm.hostname = "servidor1"
end
config.vm.define :servidor2 do |servidor2|
servidor2.vm.box = "bento/centos-7.9"
servidor2.vm.network :private_network, ip: "192.168.50.20"
servidor2.vm.hostname = "servidor2"
end
config.vm.define :loadbalancer do |loadbalancer|
loadbalancer.vm.box = "bento/centos-7.9"
loadbalancer.vm.network :private_network, ip: "192.168.50.30"
loadbalancer.vm.hostname = "loadbalancer"
end

end
```
Para este proyecto fue necesario utilizar estas tres máquinas para cumplir el siguiente esquema:
![Esquema para el balanceador de carga de servidores web](Img.png)
