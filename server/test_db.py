import pymysql

host = 'localhost'
user = 'cs411sp20team25_team25'
passwd = 'team25team25'
db = 'cs411sp20team25_team25db'

while True:
    #db = pymysql.connect(host='localhost', user='cs411sp20team25_team25', passwd='team25team25', db='cs411sp20team25_team25db')
    conn = pymysql.connect(host=host, user=user, passwd=passwd, db=db)
    curs = conn.cursor()
    query = input('> ')
    try:
        curs.execute(query)
        print(curs.fetchall())
        conn.commit()
        conn.close()
    except Exception as e:
        print(e)
