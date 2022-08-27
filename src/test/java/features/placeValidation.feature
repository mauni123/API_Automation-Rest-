Feature: Validating Place API
@AddPlace
Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI
Given Add Place Payload with "<name>" "<language>" "<address>"
When user calls "AddPlaceAPI" with "Post" http request
Then the API call is success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And verify place_Id created maps to "<name>" using "GetPlaceAPI"

Examples:
| name           | language    | address |
| Abhishek Mauni | Hindi-India | Bhimtal | 
# | Tanisha        | Aati ni h   | Gwalapar|
@DeletePlace
Scenario: Verify if Delete Place functionality is working
Given DeletePlace Payload
When user calls "DeletePlaceAPI" with "Post" http request
Then the API call is success with status code 200
And "status" in response body is "OK"