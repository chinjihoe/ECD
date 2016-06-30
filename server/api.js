var restify = require('restify');
var Endpoint = require('./endpoint');
var port = setPort();
var server = restify.createServer({name: 'Emerging Technologies'});
server.use(restify.bodyParser());

function setPort(){
	var p = process.argv[2];

	if(typeof p == 'undefined'){
		console.log("Vergeet geen server port mee te geven. Henkie.");
		process.exit(1);
	}
	else if(isNaN(p)){
		console.log("Port nummer moet een cijfer zijn");
		process.exit(1);
	}
	else
		return p;
		
}

server.listen(port, function(){
	console.log('server started and listening at '+port);
});



//Returns x information on id request

/**
 * @api {get} /client/:id Get Client information
 * @apiName getClient
 * @apiGroup Client
 *
 * @apiParam {Number} id Clients unique ID.
 *
 * @apiSuccess {String} surname Surname of the Client.
 * @apiSuccess {String} phonenumber Phone Number of the Client.
 * @apiSuccess {String} email Email of the Client.
 * @apiSuccess {String} birthdate Date of Birth of the Client.
 * @apiSuccess {String} room Room Number of the Client.
 * @apiSuccess {String} martial Martial status of the Client.
 * @apiSuccess {Number} weight Weight in Kilo of the Client.
 * @apiSuccess {Number} detail Detail ID of the Client.
 * @apiSuccess {Number} sex Sex at birth of the Client.
 * @apiSuccess {String} extra Extra information regarding the Client.
 */

server.get('/client/:id', Endpoint.getClient);

/**
 * @api {get} /client/:id/activities Get Activities of given client.
 * @apiName getActivitiesWithClientId
 * @apiGroup Client
 *
 * @apiParam {Number} id Clients unique ID.
 *
 * @apiSuccess {Object[]} activities List of Activities.
 * @apiSuccess {Number} activities.id Id of the Activity.
 * @apiSuccess {Number} activities.account_id Id of the employee that added the Activity.
 * @apiSuccess {Number} activities.client_id Id of the Client.
 * @apiSuccess {String} activities.subjective Subjective part from SOEP of the Activity.
 * @apiSuccess {String} activities.subjective Objective part from SOEP of the Activity.
 * @apiSuccess {String} activities.subjective Evaluation part from SOEP of the Activity.
 * @apiSuccess {String} activities.subjective Plan part from SOEP of the Activity.
 * @apiSuccess {String} activities.date Creation date of the Activity.

 */

server.get('/client/:id/activities', Endpoint.getActivity);

/**
 * @api {get} /employee/:id Get Employee information.
 * @apiName getEmployee
 * @apiGroup Employee
 *
 * @apiParam {Number} id Employee's unique ID.
 *
 * @apiSuccess {String} surname Surname of the Employee.
 * @apiSuccess {String} phonenumber Phone Number of the Employee.
 * @apiSuccess {String} email Email of the Employee.
 * @apiSuccess {String} birthdate Date of Birth of the Employee.
 * @apiSuccess {String} city City of the Employee.
 * @apiSuccess {String} postal Postal Code of the Employee.
 * @apiSuccess {String} country Country (residence) of the Employee.
 * @apiSuccess {Number} accounts_id Id of the login Account.

 */

server.get('/employee/:id', Endpoint.getEmployee);

/**
 * @api {get} /activity/:id Get Activity information.
 * @apiName getActivity
 * @apiGroup Activities
 *
 * @apiParam {Number} id Activity's unique ID.
 *
 * @apiSuccess {Number} id Id of the Activity.
 * @apiSuccess {Number} account_id Id of the employee that added the Activity.
 * @apiSuccess {Number} client_id Id of the Client.
 * @apiSuccess {String} subjective Subjective part from SOEP of the Activity.
 * @apiSuccess {String} subjective Objective part from SOEP of the Activity.
 * @apiSuccess {String} subjective Evaluation part from SOEP of the Activity.
 * @apiSuccess {String} subjective Plan part from SOEP of the Activity.
 * @apiSuccess {String} date Creation date of the Activity.

 */

server.get('/activity/:id', Endpoint.getActivity);

/**
 * @api {get} /episodes/:id Get Episode information.
 * @apiName getEpisode
 * @apiGroup Episodes
 *
 * @apiParam {Number} id Episodes unique ID.

 * @apiSuccess {Number} id Id of the Episode.
 * @apiSuccess {String} name Name of the Episode.
 * @apiSuccess {Number} client_id Id of the Client.
*/

server.get('/episodes/:id', Endpoint.getEpisodes);

/**
 * @api {get} /accounts/admin Get all Admins.
 * @apiName getAdmins
 * @apiGroup Accounts
 *

 * @apiSuccess {Array} admins Admin id's of the Accounts.
*/

server.get('/accounts/admin', Endpoint.getAdmins);

/**
 * @api {post} /login Login as employee.
 * @apiName login
 * @apiGroup Accounts
 *
 * @apiParam {String} username Username of the Account.
 * @apiParam {String} username Password of the Account.

 * @apiSuccess {Boolean} login Boolean if you are logged into the Account.
 * @apiSuccess {Number} id Id of the logged in user.
*/

//login
server.post('/login', Endpoint.login);
//logout

/**
 * @api {post} /logout logout as employee.
 * @apiName logout
 * @apiGroup Accounts
 *
 * @apiParam {Number} id Id of the Account.

 * @apiSuccess {Boolean} logout Boolean if you are logged out of the Account.
*/

server.post('/logout', Endpoint.logout);

/**
 * @api {post} /addactivity Add an Activity.
 * @apiName addActivity
 * @apiGroup Activities
 *
 * @apiParam {Number} account_id Id of the Account.
 * @apiParam {Number} client_id Id of the Client.
 * @apiParam {Number} subjective Subjective (SOEP) of the Activity.
 * @apiParam {Number} objective Objective (SOEP) of the Activity.
 * @apiParam {Number} evaluation Evaluation (SOEP) of the Activity.
 * @apiParam {Number} plan Plan (SOEP) of the Activity.

 * @apiSuccess {Boolean} success Boolean of the mutation success state.
*/

server.post('/addactivity', Endpoint.addActivity);

/**
 * @api {post} /updateactivity Add an Activity.
 * @apiName updateActivity
 * @apiGroup Activities
 *
 * @apiParam {Number} id Id of the Activity.
 * @apiParam {Number} subjective Subjective (SOEP) of the Activity.
 * @apiParam {Number} objective Objective (SOEP) of the Activity.
 * @apiParam {Number} evaluation Evaluation (SOEP) of the Activity.
 * @apiParam {Number} plan Plan (SOEP) of the Activity.

 * @apiSuccess {Boolean} success Boolean of the mutation success state.
*/

server.post('/updateactivity', Endpoint.updateActivity);











