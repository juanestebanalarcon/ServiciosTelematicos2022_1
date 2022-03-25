service firewalld start
service NetworkManager stop
chkconfig NetworkManager off
firewall-cmd --zone=dmz --add-interface=eth1 --permanent
firewall-cmd --zone=dmz --add-service=https --permanent
firewall-cmd --zone=dmz --add-service=http --permanent
firewall-cmd --zone=dmz --add-service=ftp --permanent
firewall-cmd --zone=dmz --add-service=dns --permanent
firewall-cmd --zone=internal --add-interface=eth2 --permanent
firewall-cmd --permanent --zone=dmz --add-masquerade
firewall-cmd --permanent --zone=internal --add-masquerade
firewall-cmd --permanent --zone="dmz" --add-forward-port=port=443:proto=tcp:toport=443:toaddr=192.168.50.4
firewall-cmd --permanent --zone="dmz" --add-forward-port=port=22:proto=tcp:toport=22:toaddr=192.168.50.3
firewall-cmd --permanent --zone="dmz" --add-forward-port=port=53:proto=udp:toport=53:toaddr=192.168.50.4
firewall-cmd --reload
service firewalld start