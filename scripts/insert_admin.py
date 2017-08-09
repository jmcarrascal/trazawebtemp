import sys
import commands
import os


path="/tmp"
schema = sys.argv[1]
file_conf= open(path + os.sep + "insert_admin.sql", "w+")

script = '''
USE {schema}; 
INSERT INTO rol(id, abrev, descripcion, nombre)
VALUES(1, "ADMIN", "", "ADMINISTRADOR"),(2, "OPER", "", "OPERADOR"),(3, "OPERADOR_DEVOLUCION", "", "OPERADOR_DEVOLUCION"),(4, "OFICIAL_CUENTA", "", "OFICIAL_CUENTA"),(5, "VENTAS", "", "VENTAS");
INSERT INTO usuario(id, locked, apellido, nombre, password, salt, username, idrol)
VALUES(1, 0, "admin", "admin", "1144d43d2d4f306a49ea3c62db3471b3", "[B@4394cc7", "admin", 1);
'''.format(schema=schema)

file_conf.write(script)
file_conf.close()

cmd = '''mysql --user root --password=root < /tmp/insert_admin.sql'''

tb = commands.getoutput(cmd)
print tb

if len(sys.argv) < 2:
    u = "Usage: create_schema <schema>\n"
    sys.stderr.write(u)
    sys.exit(1)
