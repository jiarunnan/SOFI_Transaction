SOFI-Rest-Transaction-Service
==========================

Using following dependecy to build a simple rest api run logic
- Jetty embedded 9.x
- Jersey 1.x
- Guice 3.0
- JUnit


## Usage

`mvn exec:java -Dexec.args="pl"`
(for polish )

`mvn exec:java -Dexec.args="fi"`
(for finnish)

http://localhost:8080/user/{user-id} 
(Get Most Frequent Visited Merchant for the given user)

http://localhost:8080/transactions/{tx-id}/post
(POST service to save transaction information in memory)


Using a Interface for data-manager(can be memory, can be database)
- In this project, we use memory. I use three maps 
  - ``Map<String, TreeMap<Integer, Set<String>>> userToFerquencyToMerchantSet;`` 
     - save User's frequent to merchant map (Tree Map)
  - ``Map<String, HashMap<String, Integer>> userToMerchantToFrequency;``
     - save User's merchant to frequent map (HashMap)
  - ``Map<String, Integer> userTransactionNumbers;``
     - save User's transaction number
- In saveTransaction method, I update this three map, and keep the frequent merchantmap size as 3.
- So the TimeComplexity of  save step is O(lg(3)) -> O(1)
- Time Complexity of getMostFrequentVisitedMerchat is O(3) -> O(1)

- And if the transaction number is smaller than 5, it should return InternalException with errorMsg:``The user only has 1 transactions``


##Test

I create 4 testcases which cover most of the situations
```$xslt
When_CallSaveTransactionFiveTimesWithSameInput_Then_CallGetMostVisited_Expected_ListWithOneElement
When_CallSaveTransactionFourTimesWithSameInput_Then_CallGetMostVisited_Expected_InternalException
When_CallSaveTransactionThreeTimesWithMerchantAOneTimeMerchantBOneTimeMerchantC_Then_CallGetMostVisited_Expected_ListOfMerchantABC
When_CallSaveTransactionTwoTimesWithMerchantATwoTimeMerchantBTwoTimeMerchantCOneTimeMerchantD_Then_CallGetMostVisited_Expected_ListOfMerchantABC

```
##Sample Call
- Here is the sample output of GET and POST request
                       
                       
```$xslt
GET http://localhost:8080/user/1

HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 69
Server: Jetty(9.0.4.v20130625)

{
  "user-id": "1",
  "most-frequently-visited-merchants": [
    "Bartell Drugs"
  ]
}

Response code: 200 (OK); Time: 60ms; Content length: 69 bytes
```
```$xslt
POST http://localhost:8080/transactions/107/post

HTTP/1.1 201 Created
Content-Type: application/json
Content-Length: 113
Server: Jetty(9.0.4.v20130625)

{
  "user-id": "1",
  "merchant": "Bartell Drugs abc",
  "price": "5.78",
  "purchase-date": "2019-01-01T22:45:40",
  "tx-id": "100"
}

Response code: 201 (Created); Time: 23ms; Content length: 113 bytes
```
```$xslt
GET http://localhost:8080/user/1

HTTP/1.1 500 Server Error
Cache-Control: must-revalidate,no-cache,no-store
Content-Type: text/html; charset=ISO-8859-1
Content-Length: 377
Server: Jetty(9.0.4.v20130625)

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1"/>
  <title>Error 500 </title>
</head>
<body>
<h2>HTTP ERROR: 500</h2>
<p>Problem accessing /user/1. Reason:
<pre>    jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException: The user only has 1 transactions</pre>
</p>
<hr/>
<i>
  <small>Powered by Jetty://</small>
</i>
</body>
</html>


Response code: 500 (Server Error); Time: 17ms; Content length: 377 bytes
```

