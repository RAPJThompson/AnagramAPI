# AnagramAPI

This is a basic Spring Boot application that creates and runs a REST API for interacting with anagrams stored in memory. If running locally, runs on Port 3000. To run locally either download the repo and use the command

    gradlew bootrun

or download the jar file and use Java 11 to run the command
  
    java -jar AnagramAPI.jar

Basic usage:

  Get anagrams by word: 
  
    URL: http://localhost:3000/anagrams/{word}.json
    REST Method: GET
    Returns: 
      HTTP code: 200 OK
      Example Response Body:
        {
            "words": [
                "pineapple"
            ]
         }
    
  Add words to anagram list: 

    URL: http://localhost:3000/words.json
    REST Method: POST
    Example Request Body:
      {
          "words": [
              "pineapple"
          ]
       }
    Returns: 
      HTTP code: 201 Created
    
  Remove single word:
  
    URL: http://localhost:3000/words/{word}.json
    REST Method: DELETE
    Returns: 
      HTTP code: 204 No Content
    
  Remove word and all anagrams of that word:
  
    URL: http://localhost:3000/words/anagrams/{words}.json
    REST Method: DELETE
    Returns: 
      HTTP code: 204 No Content
    
  Clear all stored anagrams:
  
    URL: http://localhost:3000/words/words.json
    REST Method: DELETE
    Returns: 
      HTTP code: 204 No Content
    
  Check list for anagrams of eachother:
  
    URL: http://localhost:3000/words/check
    REST Method: POST
    Example Request Body:
      {
          "words": [
            "asps",
            "pass",
            "saps"
          ]
       }
    Returns: 
      HTTP code: 200 OK
      Example Response Body:
        true
    
  Retrieve anagram groups with most anagrams:
  
    URL: http://localhost:3000/anagrams/size
    REST Method: GET
    Returns: 
      HTTP code: 200 OK
      Example Response Body:
       [
            {
                "words": [
                    "terse",
                    "tsere",
                    "ester",
                    "reest",
                    "reset",
                    "stree",
                    "steer",
                    "estre",
                    "stere"
                ]
            },
            {
                "words": [
                    "organ",
                    "groan",
                    "orang",
                    "angor",
                    "grano",
                    "goran",
                    "argon",
                    "nagor",
                    "rogan"
                ]
            }
        ]
    
  Retrieve anagram groups with more than provided value:
  
    URL: http://localhost:3000/anagrams/size?minSize={Number}
    REST Method: GET
    Returns: 
      HTTP code: 200 OK
      Example Response Body:
       [
            {
                "words": [
                    "terse",
                    "tsere",
                    "ester",
                    "reest",
                    "reset",
                    "stree",
                    "steer",
                    "estre",
                    "stere"
                ]
            },
            {
                "words": [
                    "organ",
                    "groan",
                    "orang",
                    "angor",
                    "grano",
                    "goran",
                    "argon",
                    "nagor",
                    "rogan"
                ]
            },
            {
                "words": [
                    "lastre",
                    "relast",
                    "resalt",
                    "salter",
                    "slater",
                    "stelar",
                    "laster",
                    "rastle"
                ]
            },
            {
                "words": [
                    "pleat",
                    "palet",
                    "patel",
                    "petal",
                    "tepal",
                    "plate",
                    "leapt",
                    "pelta"
                ]
            }
        ]
