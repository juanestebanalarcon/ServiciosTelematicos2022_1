service NetworkManager stop
chkconfig NetworkManager off
sysctl -w net.ipv4.ip_forward=1
service firewalld start
chkconfig firewalld on
firewall-cmd --permanent --zone=public --add-service=http 
firewall-cmd --permanent --zone=public --add-port=80/tcp 
firewall-cmd --permanent --zone=public --add-port=8080/tcp 
firewall-cmd --permanent --zone=public --add-masquerade
firewall-cmd --permanent --zone=public --add-forward-port=port=80:proto=tcp:toport=8080:toaddr=192.168.50.3
firewall-cmd --permanent --zone=public --add-forward-port=port=8080:proto=tcp:toport=8080:toaddr=192.168.50.3
firewall-cmd --permanent --zone=public --add-forward-port=port=443:proto=tcp:toport=8080:toaddr=192.168.50.3
service firewalld restart