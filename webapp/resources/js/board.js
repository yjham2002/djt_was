var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

var connection = mysql.createConnection({
  host    :'localhost',
  port : 3306,
  user : 'root',
  password : 'lelab2016',
  database:'bidinfo'
});
var router = express.Router();

connection.connect(function(err) {
    if (err) {
        console.error('mysql connection error');
        console.error(err);
        throw err;
    }
});

router.post('/check', bodyParser.urlencoded({
    extended: true
}), function(req, res) {
    var query = connection.query('select count(*) as `check` from `Bidinfo_like` where `bid`=' + req.body.bid + ' and mid=' + req.body.mid, [], function(err,rows){
        res.json(rows);
        console.log(rows);
    });
    console.log(query);
});

router.post('/newc', bodyParser.urlencoded({
    extended: true
}), function(req, res) {
    var data = {
        'Amount':req.body.Amount,
        'Comment':req.body.Comment,
        'bid':req.body.bid,
        'mid':req.body.mid
    };
    var query = connection.query('insert into Bidinfo_comment set ?', data, function(err,rows){
        res.json(rows);
        console.log(rows);
    });
    console.log(query);
});

router.post('/removes/:id', bodyParser.urlencoded({
    extended: true
}), function(req, res) {
    var query = connection.query('DELETE FROM Bidinfo_bidlist where id=?', req.params.id, function(err,rows){
        var que = connection.query('DELETE FROM Bidinfo_comment where bid=?', req.params.id, function(err,rows){
            res.json(rows);
            console.log(rows);
        });
    });
    console.log(query);
});

router.post('/remove/comment', bodyParser.urlencoded({
    extended: true
}), function(req, res) {
    var query = connection.query('DELETE FROM Bidinfo_comment where id=' + req.body.id, [], function(err,rows){
        res.json(rows);
        console.log(rows);
    });
    console.log(query);
});

router.post('/like', bodyParser.urlencoded({
    extended: true
}), function(req, res) {
    var data = {
        'bid':req.body.bid,
        'mid':req.body.mid
    };
    var query = connection.query('insert into Bidinfo_like set ?', data, function(err,rows){
        res.json(rows);
        console.log(rows);
    });
    console.log(query);
});

router.post('/unlike', bodyParser.urlencoded({
    extended: true
}), function(req, res) {
    var query = connection.query('delete from Bidinfo_like where bid=' + req.body.bid + ' and mid=' + req.body.mid, [], function(err,rows){
        res.json(rows);
        console.log(rows);
    });
    console.log(query);
});

router.get('/:id', function(req, res) {
    var query = connection.query('select * from Bidinfo_bidlist where `id`=' + req.params.id, [], function(err,rows){
        res.json(rows);
        console.log(rows);
    });
    console.log(query);
});

router.get('/pcomment/:id', function(req, res) {
    var query = connection.query('SELECT `Bidinfo_comment`.*, (SELECT Name FROM `Bidinfo_user` WHERE `Bidinfo_comment`.mid = `Bidinfo_user`.id) '
    +'AS userName FROM `Bidinfo_comment` WHERE `bid` in (SELECT id from Bidinfo_bidlist where mid=' + req.params.id +') and mid <> '+ req.params.id +' ORDER BY date DESC', [], function(err,rows){
        res.json(rows);
        console.log(rows);
    });
    console.log(query);
});

router.get('/comment/:id', function(req, res) {
    var query = connection.query('SELECT `Bidinfo_comment`.*, (SELECT Name FROM `Bidinfo_user` WHERE `Bidinfo_comment`.mid = `Bidinfo_user`.id) '
    +'AS userName FROM `Bidinfo_comment` WHERE `bid`=' + req.params.id +' ORDER BY date DESC', [], function(err,rows){
        res.json(rows);
        console.log(rows);
    });
    console.log(query);
});

router.post('/new', bodyParser.urlencoded({
    extended: true
}), function(req, res) {
    var data = {
        'Title':req.body.Title,
        'mid':req.body.mid,
        'Dept':req.body.Dept,
        'Url':req.body.Url,
        'Bstart':req.body.Bstart,
        'Bexpire':req.body.Bexpire,
        'Charge':req.body.Charge,
        'BidNo':req.body.BidNo,
        'Type':req.body.Type,
        'hid':req.body.hid
    };
    var query = connection.query('insert into Bidinfo_bidlist set ?', data, function(err,rows){
        res.json(rows);
        console.log(rows);
    });
    console.log(query);
});

router.get('/count/:id', function(req, res) {
    var query = connection.query('UPDATE Bidinfo_bidlist set `view`=`view`+1 where id='+req.params.id, [], function(err,rows){
        res.json(rows);
        console.log(rows);
    });
    console.log(query);
});

router.get('/', function(req, res) {
    var query = connection.query('SELECT `Bidinfo_bidlist` . * ,'
    +' (SELECT COUNT( * ) FROM `Bidinfo_like` '
    +'WHERE `Bidinfo_bidlist`.id = `Bidinfo_like`.bid) AS likecount, '
    +'(SELECT COUNT( * ) FROM `Bidinfo_comment` '
    +'WHERE `Bidinfo_bidlist`.id = `Bidinfo_comment`.bid) AS commentcount'
    +' FROM `Bidinfo_bidlist` WHERE title regexp(select hid from Bidinfo_user where id=' + req.query.id + ')'
    +' OR hid regexp(select hid from Bidinfo_user where id=' + req.query.id + ') ORDER BY view desc, date DESC', [], function(err,rows){
        res.json(rows);
        //console.log(rows);
    });
    console.log(query);
});

module.exports = router;
