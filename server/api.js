var restify = require('restify');
var Endpoint = require('./endpoint');
var port = setPort();
var server = restify.createServer({name: 'Emerging Technologies'});
server.use(restify.bodyParser());

function setPort(){
	p = process.argv[2];

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
server.get('/client/:id', Endpoint.getClient);
server.get('/employee/:id', Endpoint.getEmployee)
server.get('/activity/:id', Endpoint.getActivity)

//Returns all admins
server.get('/accounts/admin', Endpoint.getAdmins);

//login
server.post('/login', Endpoint.login);
//logout
server.post('/logout', Endpoint.logout);

//Update client information
server.post('/activity', Endpoint.addActivity);










