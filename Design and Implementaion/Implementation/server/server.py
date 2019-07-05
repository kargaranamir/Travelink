import torndb
import tornado.escape
import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web
from tornado.options import define, options
import string
import random
import json

define("port", default=1104, help="run on the given port", type=int)
define("mysql_host", default="127.0.0.1:3306", help="database host")
define("mysql_database", default="tickets", help="database name")
define("mysql_user", default="mohammad", help="database user")
define("mysql_password", default="1234", help="database password")


class Application(tornado.web.Application):
    def __init__(self):
        handlers = [
            (r"/signup", Signup),
            (r"/login", Login),
            (r"/sendticket", sendticket),
            (r"/answerticket", Answerticket),
            (r"/getticket", GetTicket),
            (r"/changemode", changeMode),
            (r".*", defaulthandler),
        ]
        settings = dict()
        super(Application, self).__init__(handlers, **settings)
        self.db = torndb.Connection(
            host=options.mysql_host, database=options.mysql_database,
            user=options.mysql_user, password=options.mysql_password)


class BaseHandler(tornado.web.RequestHandler):
    @property
    def db(self):
        return self.application.db


class defaulthandler(BaseHandler):
    def get(self):
        pass


class Signup(BaseHandler):

    def get(self, *args, **kwargs):
        self.render("signup.html")

    def post(self, *args, **kwargs):
        x = [self.get_argument("name"), self.get_argument("username"), self.get_argument("password")]
        token = ''.join(
            random.choice(string.ascii_uppercase + string.digits + string.ascii_lowercase) for _x in range(32))
        search = self.db.get("SELECT name FROM users WHERE username='%s';" % x[1])

        if search is None:
            self.db.execute("INSERT INTO users (name, username, password ,role,token) values "
                            "('{}', '{}', '{}', 'user', '{}');".format(x[0], x[1], x[2], token))
            response = {"message": "Signed Up Successfully", "code": "200"}
            self.write(json.dumps(response))
        else:
            a = "is that you " + str(search["name"]) + "?\n you have signed up,use login page!"
            self.write(a)


class Login(BaseHandler):

    def get(self):
        self.render("login.html")

    def post(self, *args, **kwargs):
        username = self.get_argument("username")
        password = self.get_argument("password")
        result = self.db.get("SELECT token FROM users WHERE (username='%s'AND password='%s');" % (username, password))
        if result is not None:
            response = {"message": "Logged in Successfully", "code": "200", "token": result["token"]}
            self.write(json.dumps(response))
        else:
            self.write("WRONG")


class sendticket(BaseHandler):

    def post(self, *args, **kwargs):
        user = self.get_argument("token")
        body = self.get_argument("textTicket")
        subject = self.get_argument("subject")
        token = ''.join(random.choice(string.digits) for _x in range(10))
        self.db.execute("INSERT INTO chat (ticketText, owner, token, subject, status) "
                        "VALUES ('{}', '{}', '{}', '{}', 'open');".format(body, user, token, subject))
        self.write("done")


class Answerticket(BaseHandler):
    def post(self, *args, **kwargs):
        user = self.get_argument("owner")
        requisition = self.get_argument("ticket-num")
        answer = self.get_argument("answer")
        if user == "SAbaNBHvKcQxibvHyECSuRJt386TZBaV":
            self.db.execute("UPDATE chat SET Answer='{}' WHERE token='{}' ;".format(answer, requisition))


class GetTicket(BaseHandler):
    def post(self, *args, **kwargs):
        owner = self.get_argument("owner")
        if owner == "SAbaNBHvKcQxibvHyECSuRJt386TZBaV":
            response = self.db.query("SELECT * FROM chat where status !='close' ")
            for x in response:
                self.write(json.dumps({"ticket.Num": x["token"], "subject": x["subject"], "body": x["ticketText"]}))
        else:
            response = self.db.query("SELECT * FROM chat WHERE owner='%s'" % owner)
            for x in response:
                self.write(json.dumps({"ticket.Num": x["token"], "subject": x["subject"], "body": x["ticketText"],
                                       "Answer": x["Answer"], "Status": x["status"]}))


class changeMode(BaseHandler):
    def post(self, *args, **kwargs):
        owner = self.get_argument("owner")
        requisition = self.get_argument("ticket-num")
        if owner == "SAbaNBHvKcQxibvHyECSuRJt386TZBaV":
            mode = self.get_argument("mode")
            self.db.execute("UPDATE chat SET status='{}' WHERE token='{}';".format(mode, requisition))
        else:
            self.db.execute("UPDATE chat SET status='close' WHERE token='{}';".format(requisition))


def main():
    http_server = tornado.httpserver.HTTPServer(Application())
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.current().start()


if __name__ == "__main__":
    main()
