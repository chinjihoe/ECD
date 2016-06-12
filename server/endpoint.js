var mysql = require('mysql');
var dateFormat = require('dateformat');

var getConnection = function () {
	return mysql.createConnection({
		host : 'localhost',
		user : 'root',
		password : 'careniet',
		database : 'mydb'
	});
};

module.exports = {

	getClient : function (req, res, next) {
		var db = getConnection();
		console.log('getClients aangeroepen');
		if (!req.params.id) {
			errorReceived(140, "clientId parameter cannot be null", res);
			res.end();
		}
		db.query('SELECT COUNT(*) as id from clients WHERE id =?', [req.params.id], function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			if (rows[0].id == 0) {
				errorReceived(141, "The record with the specified id(s) does not exist", res);
				res.end();
			}
		});
		db.query('SELECT * FROM clients WHERE id =?', [req.params.id], function (err, rows, fields) {
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
					'room' : rows[0].room,
					'martial' : rows[0].martial,
					'weight' : rows[0].weight,
					'detail' : rows[0].detail,
					'sex' : rows[0].sex
				}))
			res.end();
		});
		db.end();
	},

	getEmployee : function (req, res, next) {
		var db = getConnection();
		console.log('getEmployees aangeroepen');
		if (!req.params.id) {
			errorReceived(150, "employeeId parameter cannot be null", res);
			res.end();
		}
		db.query('SELECT COUNT(*) as id from employees WHERE id =?', [req.params.id], function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			if (rows[0].id == 0) {
				errorReceived(151, "The record with the specified id(s) does not exist", res);
				res.end();
			}
		});
		db.query('SELECT * FROM employees WHERE account_id =?', [req.params.id], function (err, rows, fields) {
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
		db.end();
	},

	getActivity : function (req, res, next) {
		var db = getConnection();
		console.log('getActivity aangeroepen');
		if (!req.params.id) {
			errorReceived(160, "activityId parameter cannot be null", res);
			res.end();
		}
		db.query('SELECT COUNT(*) as id from activities WHERE client_id =?', [req.params.id], function (err, rows, fields) {
			console.log(rows);
			if (err) {
				errorReceived(500, err, res);
			}
			if (rows[0].id == 0) {
				errorReceived(161, "The record(s) with the specified id(s) does not exist", res);
				res.end();
			}
		});
		db.query('SELECT * FROM activities WHERE client_id = ? ORDER BY id DESC;', [req.params.id], function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			ids = Array()
				for (row in rows) {
					ids[row] = rows[row];
				}
				res.write(
					JSON.stringify({
						activities : ids
					}))
				res.end();
		});
		db.end();
	},

	getAdmins : function (req, res, next) {
		var db = getConnection();
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
		db.end();
	},

	login : function (req, res, next) {
		var db = getConnection();
		console.log('login aangeroepen', req.params.password, req.params.username);

		db.query('SELECT employees.id as id FROM accounts, employees WHERE username = ? AND password = ? AND employees.account_id = accounts.id', [req.params.username, req.params.password], function (err, rows, fields) {
			if (err) {
				errorReceived(500, err, res);
			}
			console.log(rows);

			if (rows.length > 0) {
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
		db.end();
	},

	logout : function (req, res, next) {
		console.log('logout aangeroepen');

		db.query('INSERT INTO activities')
	},

	addActivity : function (req, res, next) {
		var db = getConnection();
		console.log('addActivity aangeroepen')

		var insert = {
			'account_id' : req.params.account_id,
			'client_id' : req.params.client_id,
			'subjective' : req.params.subjective,
			'objective' : req.params.objective,
			'evaluation' : req.params.evaluation,
			'plan' : req.params.plan
		};

		db.query("INSERT INTO activities SET ?, date=NOW() ", insert, function (err, results) {
			if (err) {
				console.log(err);
				errorReceived(500, err, res);
			} else {
				res.write(
				JSON.stringify({
					'success' : true
				}));
			res.end();
			}
		});
		db.end();
	},

	updateActivity : function (req, res, next) {
		var db = getConnection();
		console.log('updateActitiy aangeroepen')

		var insert = {
			'subjective' : req.params.subjective,
			'objective' : req.params.objective,
			'evaluation' : req.params.evaluation,
			'plan' : req.params.plan,
			'id' : req.params.id
		};

		db.query("UPDATE activities SET ? WHERE id= ?;", insert, function (err, results) {
			if (err) {
				console.log(err);
				errorReceived(500, err, res);
			} else {
				res.write(
				JSON.stringify({
					'success' : true
				}));
			res.end();
			}
		});
		db.end();
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
