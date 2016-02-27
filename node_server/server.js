var express = require('express');
var mongo = require('mongodb');
var MongoClient = require('mongodb').MongoClient;

var http = require('http');

var bodyParser = require('body-parser');
var app = express();

var url = "mongodb://localhost:27017/watr";
var db;

app.use(bodyParser.json());

MongoClient.connect(url, function(err, database) {
    db = database;

    app.listen(3000);
    console.log("Listening on port 3000");
});

app.post('/reports', function(req, res) {
    console.log(req);
    console.log(req.body);
    db.collection('reports', function(err, collection) {
        collection.insert(req.body, {safe:true}, function(err, result) {
            res.writeHead(200, "OK", {'Content-Type': 'text/html'});
            res.end();
            //res.status(200).end();
        });
    });
});

app.get('/reports', function(req, res) {
    db.collection('reports', function(err, collection) {
        collection.find().toArray(function(err, reports) {
            res.status(200).json(reports);
        });
    });
});
