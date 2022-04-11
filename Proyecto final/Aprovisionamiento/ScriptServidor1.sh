sudo -i
yum install vim httpd -y
touch /var/www/html/index.html
echo "Página principal, servidor 1, Team Rocket" >> /var/www/html/index.html
service httpd restart