## Person API
### Prerequisites
The following should be installed:
- Java Version 17
- Gradle

_This project uses an in-memory database, so no datasource configuration will be required._

### Building and Running
Open terminal in and navigate to the root of this project.  
Run `gradlew` to initialize.  
Run `gradlew bootRun` to run Spring Boot Application.

---
### Endpoints

GET: http://localhost:8080/person  
Returns all persons  
Sample response:

```json
[
  {
	"id": 1,
	"name": "Frank Smith",
	"birthDate": "1973-01-15",
	"partner": {
	  "id": 2,
	  "name": "Jane Smith",
	  "birthDate": "1975-07-07"
	}
  }
]
```
---
GET: http://localhost:8080/person/{id}  
Returns a person with given ID. Returns 404 if ID not found.  
Optionally, include Query Params to sort returned data. `orderBy` param may be used to sort
on field (only `id` and `name` are options to sort. `id` is default.). `order` param may be
used to define sort order - provide either `ASC` or `DESC` (`ASC` is default option).  
Example: http://localhost:8080/person/{id}?orderBy=name&order=DESC

Sample response:

```json
{
  "id": 1,
  "name": "Frank Smith",
  "birthDate": "1973-01-15",
  "parent1Id": null,
  "parent2Id": null,
  "partnerId": 2
}
```
---

POST: http://localhost:8080/person  
Creates a new person, and returns the saved person.   
Sample request:

```json
{
  "name": "Jake Hathaway",
  "birthDate": "1985-07-07",
  "parent1Id": 1,
  "parent2Id": 2
}
```

Sample response:

```json
{
  "id": 8,
  "name": "Jaky Hathaway",
  "birthDate": "1985-07-07",
  "parent1Id": 1,
  "parent2Id": 5,
  "partnerId": null
}
```
---
PUT: http://localhost:8080/person/{id}  
Updates a new person or saves it if it doesn't exist, and returns the saved person.   
Sample request:

```json
{
  "name": "Jake Hathaway",
  "birthDate": "1985-07-07",
  "parent1Id": 1,
  "parent2Id": 2
}
```

Sample response:

```json
{
  "id": 8,
  "name": "Jaky Hathaway",
  "birthDate": "1985-07-07",
  "parent1Id": 1,
  "parent2Id": 5,
  "partnerId": null
}
```
--- 
DELETE: http://localhost:8080/person/{id}  
Deletes a person with given ID.  
Returns empty OK response.  

---
GET: http://localhost:8080/person/csv?childrenCount=3&maxChildAge=18  
Endpoint will return a list of all persons who:  
a. Have a partner, and three children with that partner.  
b. Any child is younger than 18.  
Sample Response:
```json
[
  {
	"id": 1,
	"name": "Frank Smith",
	"birthDate": "1973-01-15",
	"partner": {
	  "id": 2,
	  "name": "Jane Smith",
	  "birthDate": "1975-07-07"
	}
  }
]
```
---
GET: http://localhost:8080/person/csv?childrenCount=3&maxChildAge=18  
Returns a CSV file with the result from the `filtered` endpoint
