# Evaluación final - Servicios Telemáticos.

- Desarrollar las siguientes implementaciones. Estas deben ser sustentadas individualmente.
1. [2.0 Puntos (Funcionamiento + Sustentación)] SERVICIO + FIREWALL. Instalar el servidor de
streaming Streama protegido por Firewall como se muestra en la figura. Todas las solicitudes hacia el
servidor Streama deberán ser realizadas al firewall y no directamente al servicio configurado. El firewall
debe redirigir las peticiones al servicio.

- Compruebe el funcionamiento desde el navegador del anfitrión y del SmartPhone.

![Estructura](./Estructura.png)

- 2. [1.0 Puntos (Funcionamiento + Sustentación)]] APROVISIONAMIENTO. Utilice los servicios de
aprovisionamiento que provee Vagrant usando Shell para que los servicios del punto anterior (Firewall +
Streama) queden aprovisionados de manera automática.

- Vagrantfile utilizado:
 ```bash
 Vagrant.configure("2") do |config|
if Vagrant.has_plugin?("vagrant-vbguest")
    config.vbguest.auto_update = false 
end
config.vm.define :firewall do |firewall|
firewall.vm.box = "bento/centos-7.9"
firewall.vm.network :private_network, ip: "192.168.50.2"
firewall.vm.hostname = "firewall"
end
config.vm.define :streama do |streama|
streama.vm.box = "bento/centos-7.9"
streama.vm.network :private_network, ip: "192.168.50.3"
streama.vm.hostname = "streama"
end

end
```
