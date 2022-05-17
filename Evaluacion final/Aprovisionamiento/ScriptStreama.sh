sudo -i
yum -y update
yum install wget -y
wget --no-cookies --no-check-certificate --header "Cookie:oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.rpm"
yum -y localinstall jdk-8u131-linux-x64.rpm
wget https://github.com/dularion/streama/releases/download/v1.1/streama-1.1.war
mkdir /opt/streama
mv streama-1.1.war /opt/streama/streama.war
mkdir /opt/streama/media
chmod 664 /opt/streama/media
echo -e "[Unit]\n Description=Streama Server \n After=syslog.target \n After=network.target \n [Service] \n User=root \n Type=simple \n ExecStart=/bin/java -jar /opt/streama/streama.war \n Restart=always \n StandardOutput=syslog
\n StandardError=syslog \n SyslogIdentifier=Streama \n [Install] \n WantedBy=multi-user.target" >> /etc/systemd/system/streama.service
systemctl start streama
systemctl enable streama
