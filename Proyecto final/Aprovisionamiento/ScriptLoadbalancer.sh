sudo -i
yum install httpd vim -y
echo "LoadModule proxy_module modules/mod_proxy.so" >> /etc/httpd/conf/httpd.conf
echo "LoadModule proxy_http_module modules/mod_proxy_http.so" >> /etc/httpd/conf/httpd.conf
echo -e "<VirtualHost *:80> \n <Proxy balancer://clusterServicios> \n BalancerMember http://192.168.50.10 \n BalancerMember http://192.168.50.20 \n ProxySet lbmethod=bytraffic \n </Proxy> \n ProxyPreserveHost On \n ProxyPass "/" "balancer://clusterServicios/" \n ProxyPassReverse "/" "balancer://clusterServicios/" \n </VirtualHost>" >> /etc/httpd/conf/httpd.conf
service httpd restart