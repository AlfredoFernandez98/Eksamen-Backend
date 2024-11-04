
###
GitHUB RESP: https://github.com/AlfredoFernandez98/Eksamen-Backend

## Task 3

3.3.2:
GET http://localhost:7070/api/trips

```json

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:17:11 GMT
Content-Type: application/json
Content-Length: 902

[
  {
    "id": 1,
    "starttime": [
      2024,
      11,
      4
    ],
    "endtime": [
      2024,
      11,
      6
    ],
    "startposition": "Beach Resort",
    "name": "Tropical Beach Adventure",
    "price": 499.99,
    "category": "BEACH",
    "guide": {
      "id": 1,
      "firstname": "Alice",
      "lastname": "Johnson",
      "email": "alice.johnson@example.com",
      "phone": 123456789,
      "yearsOfExperience": 5
    }
  },
  {
    "id": 4,
    "starttime": [
      2024,
      11,
      8
    ],
    "endtime": [
      2024,
      11,
      10
    ],
    "startposition": "Forest Cabin",
    "name": "Peaceful Forest Retreat",
    "price": 399.99,
    "category": "FOREST",
    "guide": null
  },
  {
    "id": 3,
    "starttime": [
      2024,
      11,
      7
    ],
    "endtime": [
      2024,
      11,
      9
    ],
    "startposition": "Lake Retreat",
    "name": "Calm Lake Escape",
    "price": 299.99,
    "category": "LAKE",
    "guide": null
  },
  {
    "id": 2,
    "starttime": [
      2024,
      11,
      11
    ],
    "endtime": [
      2024,
      11,
      13
    ],
    "startposition": "Mountain Base",
    "name": "Mountain Climbing Expedition",
    "price": 799.99,
    "category": "SNOW",
    "guide": {
      "id": 2,
      "firstname": "Bob",
      "lastname": "Smith",
      "email": "bob.smith@example.com",
      "phone": 987654321,
      "yearsOfExperience": 10
    }
  }
]
Response file saved.
> 2024-11-04T121711.200.json

Response code: 200 (OK); Time: 163ms (163 ms); Content length: 902 bytes (902 B)
```

GET http://localhost:7070/api/trips/1
``` json

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:17:48 GMT
Content-Type: application/json
Content-Length: 287

{
  "id": 1,
  "starttime": [
    2024,
    11,
    4
  ],
  "endtime": [
    2024,
    11,
    6
  ],
  "startposition": "Beach Resort",
  "name": "Tropical Beach Adventure",
  "price": 499.99,
  "category": "BEACH",
  "guide": {
    "id": 1,
    "firstname": "Alice",
    "lastname": "Johnson",
    "email": "alice.johnson@example.com",
    "phone": 123456789,
    "yearsOfExperience": 5
  }
}
Response file saved.
> 2024-11-04T121748.200.json

Response code: 200 (OK); Time: 30ms (30 ms); Content length: 287 bytes (287 B)
```
POST http://localhost:7070/api/trips/create
```json


HTTP/1.1 201 Created
Date: Mon, 04 Nov 2024 11:28:42 GMT
Content-Type: application/json
Content-Length: 139

{
  "id": 6,
  "starttime": [
    2024,
    11,
    7
  ],
  "endtime": [
    2024,
    11,
    9
  ],
  "startposition": "test1",
  "name": "test1",
  "price": 299.99,
  "category": "LAKE",
  "guide": null
}
Response file saved.
> 2024-11-04T122842.201.json

Response code: 201 (Created); Time: 31ms (31 ms); Content length: 139 bytes (139 B)
```
PUT http://localhost:7070/api/trips/update/1

```json
HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:29:41 GMT
Content-Type: application/json
Content-Length: 143

{
"id": 1,
"starttime": [
2024,
11,
7
],
"endtime": [
2024,
11,
9
],
"startposition": "Alfredo",
"name": "PERUUUU",
"price": 299.99,
"category": "LAKE",
"guide": null
}
Response file saved.
> 2024-11-04T122941.200.json

Response code: 200 (OK); Time: 37ms (37 ms); Content length: 143 bytes (143 B)
```

DELETE http://localhost:7070/api/trips/delete/1
```json

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 11:31:43 GMT
Content-Type: text/plain

<Response body is empty>

Response code: 204 (No Content); Time: 102ms (102 ms); Content length: 0 bytes (0 B)
```

PUT http://localhost:7070/api/trips/addguide/2/guides/3
```json


HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:33:47 GMT
Content-Type: application/json
Content-Length: 90

{
  "error": "dat.exceptions.ApiException: Guide with that id is not found",
  "status": "200 OK"
}
Response file saved.
> 2024-11-04T123347.200.json

Response code: 200 (OK); Time: 64ms (64 ms); Content length: 90 bytes (90 B)
```
## Task 4

4.1
GET http://localhost:7070/api/trips/999
```json
  "statusCode": 404,
  "message": "Invalied ID: Trip not found",
  "suppressed": [],
  "localizedMessage": "Invalied ID: Trip not found"
}
Response file saved.
> 2024-11-04T123706.404.json

Response code: 404 (Not Found); Time: 124ms (124 ms); Content length: 8104 bytes (8.1 kB)
```

DElETE http://localhost:7070/api/trips/delete/999
```json
{
  "statusCode": 404,
  "message": "Invalied ID: Trip not found",
  "suppressed": [],
  "localizedMessage": "Invalied ID: Trip not found"
}
```