from flask import render_template, flash, redirect, url_for, session, request, logging
from flask_mysqldb import MySQL
from wtforms import Form, StringField, TextAreaField, PasswordField, validators
from passlib.hash import sha256_crypt
from functools import wraps
from app import app
from app import data as d
from apispec import APISpec
from apispec.ext.marshmallow import MarshmallowPlugin
from apispec_webframeworks.flask import FlaskPlugin
from flask import Flask,jsonify,send_from_directory
from marshmallow import Schema, fields
from datetime import date

#agregar clave secreta
app.secret_key='secret123'
#config MySQL
app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] ='Autonoma123*'
app.config['MYSQL_DB'] = 'prcFlask'
app.config['MYSQL_CURSORCLASS'] = 'DictCursor'

#INIT MYSQL
mysql = MySQL(app)

spec = APISpec(
        title='Flask-api-swagger-doc',
        version='1.0.0.',
        openapi_version='3.0.2',
        plugins=[FlaskPlugin(),MarshmallowPlugin()]
            )

@app.route('/api/swagger.json')
def create_swagger_spec():
    return jsonify(spec.to_dict())
class ArticleResponseSchema(Schema):
    id = fields.Int()
    title = fields.Str()
    body = fields.Str()
    author = fields.Str()
    create_date = fields.Date()
class ArticleListResponseSchema(Schema):
    article_list = fields.List(fields.Nested(ArticleResponseSchema))
@app.route('/articlesdocs')
def article2():
    arts=d.Articles()
    """
    Get List of Articles
                ---
                get:
                description: Get List of Articles
                responses:
                        200:
                            description: Return an article list
                            content:
                                application/json:
                                  schema: ArticleListResponseSchema
    """
    return ArticleListResponseSchema().dump({'article_list':arts})
with app.test_request_context():
    spec.path(view=article2)

class RegisterForm(Form):
    name = StringField('Name', [validators.Length(min=1, max=50)])
    username = StringField('Username', [validators.Length(min=4,max=25)])
    email = StringField('Email', [validators.Length(min=6, max=50)])
    password = PasswordField('Password', [
    validators.DataRequired(),
    validators.EqualTo('confirm', message='Password do not match')
    ])
    confirm = PasswordField('Confirm Password')
# CREATE VALIDATOR FORM FOR ARTICLES
class ArticleForm(Form):
    title = StringField('Title', [validators.Length(min=1, max=100)])
    body = StringField('Body', [validators.Length(min=1,max=2000)])
    author = StringField('Author', [validators.Length(min=3, max=100)])

@app.route('/')
def index():
    return render_template("index.html")

@app.route('/about')
def about():
    return render_template("about.html")
@app.route('/articles')
def articles():
    art=len(d.Articles())
    return render_template("articles.html",data=art)
@app.route('/article/<int:id>')
def getArticle(id):
    articles=d.Articles()
    for data in articles:
        if data['id']==id:
           art=data
           return render_template("article.html",data=art)
    return render_template("article.html")

@app.route('/register', methods=['GET','POST'])
def register():
    form = RegisterForm(request.form)
    if request.method == 'POST' and form.validate():
        name = form.name.data
        email = form.email.data
        username = form.username.data
        password = sha256_crypt.encrypt(str(form.password.data))
        # Create Cursor
        cur = mysql.connection.cursor()
        # Execute Query
        cur.execute("INSERT INTO users(name, email, username, password) VALUES(%s, %s, %s, %s)", (name,email,username,password))
        # Commit to DB
        mysql.connection.commit()
        # Close connection
        cur.close()
        flash('You are now registered and can log in', 'success')
        return redirect(url_for('index'))
    return render_template('register.html', form=form)
# User login
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        # Get Form Fields
        username = request.form['username']
        password_candidate = request.form['password']
        # Create cursor
        cur = mysql.connection.cursor()
        # Get user by username
        result = cur.execute("SELECT * FROM users WHERE username =%s",[username])
        if result > 0:
            data = cur.fetchone()
            password = data['password']
            if sha256_crypt.verify(password_candidate, password):
                session['logged_in'] = True
                session['username'] = username
                flash('You are logged in', 'success')
                app.logger.info('PASSWORD MATCHED')
                return redirect(url_for('dashboard'))
            else:
                app.logger.info('PASSWORD NO MATCHED')
                error = 'Invalid login'
                return render_template('login.html',error=error)
                cur.close()
        else:
            app.logger.info('NO USER')
            error = 'Username not found'
            return render_template('login.html', error=error)
    return render_template('login.html')
# Check if user logged in
def is_logged_in(f):
    @wraps(f)
    def wrap(*args, **kwargs):
        if 'logged_in' in session:
            return f(*args, **kwargs)
        else:
            flash('Unauthorized, Plese login', 'danger')
            return redirect(url_for('login'))
    return wrap
# Dashboard
@app.route('/dashboard')
@is_logged_in
def dashboard():
    cur = mysql.connection.cursor()
    cur.execute("SELECT * FROM articles")
    articles = cur.fetchall()
    cur.close()
    return render_template('dashboard.html', articles=articles)
# Logout
@app.route('/logout')
def logout():
    session.clear()
    flash('You are now logged out','success')
    return redirect(url_for('login'))
# Add Article
@app.route('/add_article', methods=['GET', 'POST'])
def add_article():
    form = ArticleForm(request.form)
    if request.method == 'POST' and form.validate():
        title = form.title.data
        body = form.body.data
        author = form.author.data
        cur = mysql.connection.cursor()
        cur.execute("INSERT INTO articles(title, body, author) VALUES(%s, %s, %s)", (title,body,author))
        mysql.connection.commit()
        cur.close()
        flash('The article has successfully added!', 'success')
        return redirect(url_for('dashboard'))
    return render_template('add_article.html', form=form)
# Edit Article
@app.route('/edit_article/<string:id>', methods=['GET', 'POST'])
def edit_article(id):
    form = ArticleForm()
    cur = mysql.connection.cursor()
    result = cur.execute("SELECT * FROM articles WHERE id =%s",(id))
    row = cur.fetchone()
    #title = row['title']
    form = ArticleForm(request.form)
    if request.method == 'POST' and form.validate():
        title = form.title.data
        body = form.body.data
        author = form.author.data
        cur.execute("UPDATE articles SET title=%s, body=%s, author=%s WHERE id=%s", (title,body,author,id))
        mysql.connection.commit()
        cur.close()
        flash('The article has successfully updated!', 'success')
        return redirect(url_for('dashboard'))
    else:
        return render_template('edit_article.html', form=form, row=row)
    return render_template('edit_article.html', form=form, row=row)
#Delete article
@app.route('/delete_article/<string:id>', methods=['GET', 'POST'])
def delete_article(id):
    cur = mysql.connection.cursor()
    cur.execute("DELETE FROM articles WHERE id =%s",(id))
    mysql.connection.commit()
    cur.close()
    flash('The article has successfully deleted!', 'success')
    return redirect(url_for('dashboard'))