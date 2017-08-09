import sys
import commands
import os


path="/tmp"
schema = sys.argv[1]
file_conf= open(path + os.sep + "create_schema.sql", "w+")

script = '''use sys; create database {schema};'''.format(schema=schema)

file_conf.write(script)
file_conf.close()

cmd = '''mysql --user root --password=root < /tmp/create_schema.sql'''

tb = commands.getoutput(cmd)
print tb

if len(sys.argv) < 2:
    u = "Usage: create_schema <schema>\n"
    sys.stderr.write(u)
    sys.exit(1)
