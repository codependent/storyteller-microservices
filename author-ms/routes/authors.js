var express = require('express');
var router = express.Router();

var authors = ['Miguel de Cervantes',
               'William Shakespeare',
               'Ken Follet',
               'Jack Kerouac',
               'Groucho Marx',
               'J.D. Salinger'];

/* GET authors listing. */
router.get('/', function(req, res, next) {
  if(req.query.random){
	  var random = Math.round(Math.random()* (authors.length-1));
	  var auth = authors[random]
	  res.json({author : auth});
  }else{
	  res.status(400).end();
  }
});

module.exports = router;
