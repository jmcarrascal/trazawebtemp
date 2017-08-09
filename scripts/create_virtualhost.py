import sys
import os


virtualhost = '''
<VirtualHost *:80>
        ServerName {SUBDOMAIN}.cipres.io

        DocumentRoot /var/sources/{SUBDOMAIN}/cipres/src/main/webapp

        <Directory /var/sources/{SUBDOMAIN}/cipres/src/main/webapp>
                Options Indexes FollowSymLinks
                AllowOverride None
                Require all granted
        </Directory>


        ProxyPreserveHost On
        ProxyRequests     Off
        AllowEncodedSlashes NoDecode
        ProxyPass /{SUBDOMAIN} http://0.0.0.0:8080/{SUBDOMAIN} nocanon
        ProxyPassReverse /{SUBDOMAIN} http://0.0.0.0:8080/{SUBDOMAIN}

        <Proxy http://localhost:8080/{SUBDOMAIN}*>
                Order deny,allow
                Allow from all
        </Proxy>

        LogLevel error

        ErrorLog ${{APACHE_LOG_DIR}}/error-{SUBDOMAIN}-cipres-io.log
        CustomLog ${{APACHE_LOG_DIR}}/access-{SUBDOMAIN}-cipres-io.log combined
</VirtualHost>
'''.format(SUBDOMAIN=sys.argv[1])

apache_sites_path="/etc/apache2/sites-available"
file_conf= open(apache_sites_path + os.sep + sys.argv[1] + "-cipres-io.conf","w+")
file_conf.write(virtualhost)
file_conf.close()


if len(sys.argv) < 2:
    u = "Usage: create_virtualhost <subdomain>\n"
    sys.stderr.write(u)
    sys.exit(1)


