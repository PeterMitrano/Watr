var express = require('express');
var mongo = require('mongodb');
var MongoClient = require('mongodb').MongoClient;

var api_keys = require('./api_keys.js');

var geocoderProvider = 'google';
var httpAdapter = 'http';

var extra = {
    //apiKey: api_keys.google_api_key, // for Mapquest, OpenCage, Google Premier
    //formatter: null         // 'gpx', 'string', ...
};
var geocoder = require('node-geocoder')(geocoderProvider, httpAdapter, extra);
//var geocoder = require('geocoder');
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
            collection.insert(mongo_report, {safe:true}, function(err, result) {
                res.writeHead(200, "OK", {'Content-Type': 'text/html'});
                res.end();
                //res.status(200).end();
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
