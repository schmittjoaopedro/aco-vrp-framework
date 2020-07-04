$(document).ready(() => {
	
	$('#startBtn').on('click', evt => {
		var instanceName = $('#instanceInput').val();
		var speedInput = $('#speedInput').val();
		printRoute(instanceName, speedInput);
	});
	
});

function printRoute(instanceName, speed, counter = 0) {
	var currentPrint = `log/${instanceName}-${counter}.html`;
	fetch(currentPrint)
        .then(response => {
			if (response.status != 404) {
				return response.text();
			}
		})
        .then((response) => {
			if (response) {
				$('#routePanel').empty();
				$('#routePanel').html(response);
				setTimeout(() => {
					printRoute(instanceName, speed, counter + 1);
				}, speed);
			}
        })
        .catch(err => console.log(err))
}