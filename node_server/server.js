var express = require('express');
var mongo = require('mongodb');
var MongoClient = require('mongodb').MongoClient;
var _ = require('lodash');
var geocoderProvider = 'google';
var httpAdapter = 'http';

var geocoder = require('node-geocoder')(geocoderProvider, httpAdapter, {});
var bodyParser = require('body-parser');
var app = express();

var url = "mongodb://localhost:27017/watr";
var db;

app.use(bodyParser.json());
app.use(bodyParser.text());
app.use(bodyParser.urlencoded({ extended: false }));
MongoClient.connect(url, function(err, database) {
    db = database;

    app.listen(3000);
    console.log("Listening on port 3000");
});

function write_report(report, res) {
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
}

app.post('/reports', function(req, res) {
    console.log("Got a post");
    console.log(req.body);
    var report = req.body; 

    write_report(report, res);
});

app.get('/reports', function(req, res) {
    db.collection('reports', function(err, collection) {
        collection.find().toArray(function(err, reports) {
            res.status(200).json(reports);
        });
    });
});

function parse_sms(sms) {
    var lines = sms.toLowerCase().split(';');
    var obj = {};
    obj.measurements = {};
    _(lines).forEach(function(line) {
        pair = line.split(':');
        var key = pair[0].trim();
        var value = pair[1].trim();
        if(key == "zipcode") {
            obj[key] = value;
        } else {
            obj.measurements[key] = value;
        }
    });

    return obj;
}

app.post('/sms', function(req, res) {
    console.log(req.body);
    var report = parse_sms(req.body.Body);

    write_report(report, res);
});
