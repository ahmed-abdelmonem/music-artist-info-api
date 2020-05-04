# Music Artist Information API 

REST API which provide clients with information about a specific music artist from MusicBrainz, Cover Art Archive and Discogs.

## Description

The API takes a MBID (MusicBrainz Identifier) and return a result containing the following:

- List of all albums the artist has released and links to its corresponding album cover art.

- Description of the artist fetched from Discogs artist profile.

## Running

1. Java 8 or higher and Maven 3 should be installed.

2. Change directory to the root folder of the application.

3. Run the below Maven command.

```bash
mvn spring-boot:run
```
4. Go to the below URL - the URL will be redirected to Swagger UI.

```bash
http://localhost:8080
```

5. To run test cases, run the below Maven command

```bash
mvn test
```

## Endpoints

Get Artist Info (/artist/{mbid}) - HTTP GET

- Sample Request

```
http://localhost:8080/artist/134e6410-6954-45d1-bd4a-0f2d2ad5471d
```

- Sample Response

```
{
  "mbid": "134e6410-6954-45d1-bd4a-0f2d2ad5471d",
  "name": "Zara Larsson",
  "description": "Born December 16, 1997 in Stockholm, Sweden.\r\nSwedish singer, who received national fame for winning the \"TV4\" talent show Talang 2008. Both her 2013 debut EP album \"Introducing\", and the single \"Uncover\" off the EP were certified triple platinum in July 2013 by Universal Music Sweden.",
  "albums": [
    {
      "id": "b984cd2e-a1b6-4efa-91bc-0137fbbede48",
      "title": "1",
      "image": "http://coverartarchive.org/release/90e70a26-11cb-43a2-88f2-f49973a935ab/16086190492.jpg"
    },
    {
      "id": "9fbed96a-d7e9-46f9-8f5f-a9bc7797a04f",
      "title": "So Good",
      "image": "http://coverartarchive.org/release/1535d7f2-6465-4897-be4b-5919194a950a/22065982944.jpg"
    }
  ]
}
```

## Technologies and Tools

1. Java 8

2. Spring boot - Great tool to easily develop Java web application with many benefits (will be described later).

3. Maven 3 - Build, test and manage dependencies of the application.

4. Junit 4 - Unit testing.

5. Swagger UI - Document and test the API.

6. SonarLint - Code quality and coverage.

7. Apache JMeter - Load test and measure performance.

## Different MBIDs for testing

1. b8a7c51f-362c-4dcb-a259-bc6e0095f0a6 (Ed Sheeran).

2. dc99e6fd-c710-4f79-b74b-127b4d0b7849 (Labrinth).

3. 134e6410-6954-45d1-bd4a-0f2d2ad5471d (Zara Larsson).

4. 2f548675-008d-4332-876c-108b0c7ab9c5 (Sia).

5. 122d63fc-8671-43e4-9752-34e846d62a9c (Katy Perry).

## Solution And Assumptions

- Spring @Async is used with CompletableFuture to run tasks in a separate threads and get the results. Two Thread Pool Task Executors are created to run Async tasks (artistInfoAsyncExecutor for artist info calls and apiAsyncExecutor for other API calls).

	1. First Thread Pool Task Executor (artistInfoAsyncExecutor) is responsible for managing the concurrent artist info calls. Number of concurrent threads should be minimized to spread the MusicBrainz calls (rate limit is 1 call/second).
	
	2. Second Thread Pool Task Executor (apiAsyncExecutor) is responsible for managing the concurrent Cover Art Archive and Discogs calls. Number of concurrent threads can be maximized  to give a better response time (Cover Art Archive has no rate limit and Discogs rate limit won't make any issue because the number of requests will be controlled by number of artist info calls, one call per each artist info call). To prove that the response time will be improved by this solution, I tried to run a request in one thread without enabling Async and another time after enabling Async and the response time is changed from 30 seconds to only 3 seconds!  

- To get the artist info the below steps will be performed after creating the artist info thread

	1. Create Artist object from MusicBrainz response (no need to call MusicBrainz in a separate thread). 
	2. Create CompletableFuture object to get description from Discogs API 
	3. Create CompletableFutures for Cover Art Archive calls for each release group (Album). 
	4. Fill description, release groups images from getting the result of the created CompletableFutures.
	5. Get the result of the artist info thread.

- The solution is designed to handle the availability but in case of receiving an error from MusicBrainz, return a business error (service currently unavailable exception). 

- As per MusicBrainz documentation the number of linked entities returned (album release groups and relations) is always limited to 25, if you need the remaining results, you will have to perform a browse request. So to get the other release groups you should call all pages of MusicBrainz release groups which will affect the availability to other requests because of the rate limit. 25 albums is a good average for any artist so no need to to make more calls in most cases (every thing is trade-off!). 

- Data Transfer Object Pattern (DTO) is used to return artist info from the endpoint.

- Because of the limited time to finish the assignment, Only required unit test cases are written.

## The source of the profile information (Discogs)

The source should be chosen based on the below criteria:

1. Is it popular relation for artists in MusicBrainz?
2. Whether it has a free public API or not?
3. Artists descriptions in this source (found and reliable or not?)   
4. API rate limits.

As per the above criteria, Discogs is the best choice because:

1. Discogs is popular and its relation found for a big number of artists in MusicBrainz (As per my search).
2. It has a public and free API.
3. Because it is a popular music market place, A reliable description will be found.
4. The only problem is the API rate limit (60 requests/minute) but it is same as MusicBrainz and you need only to call the API one time per each request.

Other popular Alternatives:

1. Last.fm - API key is needed and to get it you should send a request with application details and wait for decision.
2. SecondHandSongs: The API is good with a better rate limit (100 requests/minute) but the description is not found for many artists. 
3. Wikidata - The API is good without rate limits but the description not found and also data is not reliable.
4. IMDb - No public API.
5. Allmusic - No public API. 
6. BBC Music - No public API.
 

## System design for production

1. Because of the rate limits of the third party APIs, A big number of nodes distributed geographically should be added.

2. Add load balancers to distribute incoming client requests to the compute nodes.

3. Use any external caching solution (e.x Memcached) with proper TTL (Availability vs Consistency). 

