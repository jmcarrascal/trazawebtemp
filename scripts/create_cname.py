import sys
import commands

cmd = '''curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer dafb8d6d1acc7ae0409186e91c1555d673db5fc0238c4bc36ffe562f13ebfeb5" -d '{{"type":"CNAME","name":"{SUBDOMINIO}","data":"vps2.cipres.io.","priority":null,"port":null,"weight":null}}' "https://api.digitalocean.com/v2/domains/cipres.io/records"'''.format(SUBDOMINIO=sys.argv[1])
tb = commands.getoutput(cmd)
print tb



if len(sys.argv) < 2:
    u = "Usage: create_cname <subdomain>\n"
    sys.stderr.write(u)
    sys.exit(1)
