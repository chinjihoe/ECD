var mysql = require('mysql');
var dateFormat = require('dateformat');

var db = mysql.createConnection({
		host : 'localhost',
		user : 'root',
		password : 'careniet',
		database : 'mydb'
	});

module.exports = {

	getClient : function (req, res, next) {
		console.log('getClients aangeroepen');
		if (!req.params.id) {
			errorReceived(140, "clientId parameter cannot be null", res);
			res.end();
		}
		db.query('SELECT COUNT(*) as id from clients WHERE id =?', [req.params.id] , function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			if (rows[0].id == 0) {
				errorReceived(141, "The record with the specified id(s) does not exist", res);
				res.end();
			}
		});
		db.query('SELECT * FROM clients WHERE id =?', [req.params.id] , function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			res.write(
				JSON.stringify({
					'name' : rows[0].name,
					'surname' : rows[0].surname,
					'phonenumber' : rows[0].phonenumber,
					'email' : rows[0].email,
					'birthdate' : dateFormat(rows[0].birthdate,"yyyy-mm-dd"),
					'room' : rows[0].room,
					'martial' : rows[0].martial,
					'weight' : rows[0].weight,
					'detail' : rows[0].detail,
					'sex' : rows[0].sex
				}))
			res.end();
		});
	},

	getEmployee : function (req, res, next) {
		console.log('getEmployees aangeroepen');
		if (!req.params.id) {
			errorReceived(150, "employeeId parameter cannot be null", res);
			res.end();
		}
		db.query('SELECT COUNT(*) as id from employees WHERE id =?', [req.params.id] , function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			if (rows[0].id == 0) {
				errorReceived(151, "The record with the specified id(s) does not exist", res);
				res.end();
			}
		});
		db.query('SELECT * FROM employees WHERE id =?', [req.params.id] , function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			res.write(
				JSON.stringify({
					'name' : rows[0].name,
					'surname' : rows[0].surname,
					'phonenumber' : rows[0].phonenumber,
					'email' : rows[0].email,
					'birthdate' : dateFormat(rows[0].birthdate, "yyyy-mm-dd"),
					'city' : rows[0].city,
					'postal' : rows[0].postal,
					'country' : rows[0].country,
					'accounts_id' : rows[0].accounts_id
				}))
			res.end();
		});
	},

	getActivity : function (req, res, next) {
		console.log('getActivity aangeroepen');
		if (!req.params.id) {
			errorReceived(160, "activityId parameter cannot be null", res);
			res.end();
		}
		db.query('SELECT COUNT(*) as id from activities WHERE id =?', [req.params.id], function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			if (rows[0].id == 0) {
				errorReceived(161, "The record with the specified id(s) does not exist", res);
				res.end();
			}
		});
		db.query('SELECT * FROM activities WHERE id =? ', [req.params.id], function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			res.write(
				JSON.stringify({
					'account_id' : rows[0].account_id,
					'client_id' : rows[0].client_id,
					'symptom' : rows[0].symptom,
					'cause' : rows[0].cause,
					'dicease' : rows[0].dicease,
					'medicine' : rows[0].medicine
				}))
			res.end();
		});
	},

	getAdmins : function (req, res, next) {
		console.log('getAdmins aangeroepen');
		db.query('SELECT id FROM accounts WHERE isAdmin = TRUE', function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			ids = Array()
				for (row in rows) {
					ids[row] = rows[row]['id']
				}
				res.write(JSON.stringify({
						admins : ids
					}))
				res.end();
		});
	},

	login : function (req, res, next) {
		console.log('login aangeroepen');

		db.query('SELECT COUNT(*) as login, employees.id as id FROM accounts, employees WHERE username = ? AND password = ? AND employees.accounts_id = accounts.id', [req.params.username, req.params.password], function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			console.log(rows);

			if (rows[0].login) {
				res.write(JSON.stringify({
						'login' : true,
						'id' : rows[0].id
					}));
				res.end();
			} else {
				res.write(JSON.stringify({
						'login' : false,
						'id' : null
					}));
				res.end();
			}
		});
	},

	logout : function (req, res, next) {
		console.log('logout aangeroepen');

		db.query('INSERT INTO activities')
	},

	addActivity : function (req, res, next) {
		console.log('addActivity aangeroepen')
        
        var insert = {
            'account_id' : req.params.account_id,
            'client_id' : req.params.client_id,
            'symptom' : req.params.symptom,
            'cause': req.params.cause,
            'disease': req.params.disease,
            'medicine': req.params.medicine,            
        };

		db.query("INSERT INTO activities SET ?, date=NOW() ", insert, function (err, results) {
			if (err) {
				console.log(err);
				errorReceived(500, err, res);
			} else {
				console.log('Succes');
				res.end();
			}
		});
	}
};

var errorReceived = function (errnum, err, res) {
	res.write(
		JSON.stringify({
			'code' : errnum,
			'error' : err
		}));
	res.end();
};
