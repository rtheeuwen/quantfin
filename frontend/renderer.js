// Backend and endpoint details
const host     = 'http://127.0.0.1:4567'
const endpoint = '/eurusd'
// Retry configuration
let maxNoOfAttempts        = 50,
    waitTimeBetweenAttempt = 150

let _fetchRates = function(waitTime, maxAttempts, currentAttemptNo) {
  $.getJSON(host + endpoint, function(rates) {
    let output = "";
    for (let i in rates) {
      let r = rates[i]
      output += `Date: ${r.date},
                 Rate: ${r.rate},
                 <br>`
    }
    $('#ratesList').html(output)
  }).fail(function() {
    if (currentAttemptNo < maxAttempts) {
      setTimeout(function() {
        _fetchRates(waitTime, maxAttempts, currentAttemptNo+1)
      }, waitTime)
    }
  })
}

let fetchRatesList = function(waitTimeBetweenAttempt, maxNoOfAttempts) {
  _fetchRates(waitTimeBetweenAttempt, maxNoOfAttempts, 1)
}

// Start trying to fetch the user list
fetchRatesList(waitTimeBetweenAttempt, maxNoOfAttempts)
