echo "instalando servicios requeridos para la máquina Servidor2:"
sudo yum install vsftpd -y
sudo yum install vim -y
sudo yum install bind-utils bin-libs bind-* -y
sudo userradd montoya
sudo yum install openssl -y
sudo yum install mod_ssl -y
sudo openssl genrsa -out ca.key 1024
sudo openssl req -new -key ca.key -out ca.csr
sudo openssl x509 -req -days 365 -in ca.csr -signkey ca.key -out ca.crt
sudo cp ca.crt /etc/pki/tls/certs/
sudo cp ca.key /etc/pki/tls/private/
sudo cp ca.csr /etc/pki/tls/private/
sudo chmod 600 /etc/pki/tls/private/ca.csr
sudo chmod 600 /etc/pki/tls/private/ca.key