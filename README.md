## Repositorio <<Servicios Telemáticos>>
# Descripción
Éste repositorio contiene diferentes prácticas realizadas en linux, en su distribución Centos 7.9 y Centos stream 8 para fines educativos con casos muy allegados a la realidad.

### Vagrantfile prácticas principales:

```bash
Vagrant.configure("2") do |config|
if Vagrant.has_plugin?("vagrant-vbguest")
    config.vbguest.auto_update = false  
  end
config.vm.define :cliente do |cliente|
 cliente.vm.box = "centos/stream8"
 cliente.vm.network :private_network, ip: "192.168.50.2"
 cliente.vm.hostname = "cliente"
end
config.vm.define :servidorpxe do |servidorpxe|
 servidorpxe.vm.box = "bento/centos-7.9"
 servidorpxe.vm.network :private_network, ip: "192.168.50.4"
 servidorpxe.vm.hostname = "servidorpxe"
end
config.vm.define :servidor do |servidor|
 servidor.vm.box = "juanesalarcon08/ServidorActualizado"
 servidor.disksize.size = '30GB'
 servidor.vm.network :private_network, ip: "192.168.50.3"
 servidor.vm.hostname = "servidor"
 servidor.vm.network :forwarded_port, guest: 80, host: 5567
 servidor.vm.network :forwarded_port, guest: 443, host: 5570
end
config.vm.define :clienteFw do |clienteFw|
clienteFw.vm.box = "bento/centos-7.9"
clienteFw.vm.network :private_network, ip: "209.191.100.2"
clienteFw.vm.hostname = "clienteFw"
end
config.vm.define :firewall do |firewall|
firewall.vm.box = "bento/centos-7.9"
firewall.vm.network :private_network, ip: "209.191.100.3"
firewall.vm.network :private_network, ip: "192.168.100.3"
firewall.vm.hostname = "firewall"
firewall.vm.network :forwarded_port, guest: 443, host: 5569
end
config.vm.define :servidor2 do |servidor2|
servidor2.vm.box = "bento/centos-7.9"
servidor2.vm.network :private_network, ip: "192.168.100.4"
servidor2.vm.hostname = "servidor2"
servidor2.vm.network :forwarded_port, guest: 443, host: 5568
end
config.vm.define :clientenat do |clientenat|
clientenat.vm.box = "bento/centos-7.9"
clientenat.vm.network :private_network, ip: "192.168.50.2"
clientenat.vm.hostname = "clientenat"
end
config.vm.define :firewallnat do |firewallnat|
firewallnat.vm.box = "bento/centos-7.9"
firewallnat.vm.network :private_network, ip: "192.168.50.3"
firewallnat.vm.hostname = "firewallnat"
end
	
end	
```