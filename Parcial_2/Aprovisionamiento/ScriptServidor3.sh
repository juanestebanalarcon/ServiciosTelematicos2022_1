yum install bind-utils bind-libs bind-* -y
yum install vim -y
sudo cp /var/named/named.empty /var/named/stars.com.fwd
sudo chmod 755 troyanos.com.fwd
sudo cp /var/named/stars.com.fwd /var/named/stars.com.rev