var express = require('express');
var mongo = require('mongodb');
var MongoClient = require('mongodb').MongoClient;

var geocoderProvider = 'google';
var httpAdapter = 'http';

var geocoder = require('node-geocoder')(geocoderProvider, httpAdapter, {});
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
    console.log("Got a post");
    console.log(req.body);
    var report = req.body;
    geocoder.geocode({address:report.zipcode, countryCode:'us'}, function(err, geo_res) {
        var geo_data = geo_res[0];
        var mongo_report = {
            position: {type: "Point", coordinates:[geo_data.latitude, geo_data.longitude]},
            locality: geo_data.city,
            zipcode: report.zipcode,
            measurements: report.measurements
        };
        
        db.collection('reports', function(err, collection) {
            if(err) {
                res.status(500);
                res.type('html');
                res.send(err);
            }
            collection.insert(mongo_report, {safe:true}, function(err, result) {
                res.status(200);
                res.type('html');
                res.send("OK");
            });
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
