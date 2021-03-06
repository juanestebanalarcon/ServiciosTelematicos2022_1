#app/views.py
from flask import render_template
from app import app
from app import data as d
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