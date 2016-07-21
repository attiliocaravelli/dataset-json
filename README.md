Given the following example dataset, write an app in a language of your 
choice that can be used to extract 
(i) a user's friend list, 
(ii) a users's suggested friends list (friend of a friend)
(iii) a user's suggested friend list sorted by geography. 
Aim to handle datasets reaching into the thousands as quickly as possible
 and with the smallest possible memory footprint.

{
 "id": 1,
 "name": "Alice",
 "city": "Dublin",
 "friends": [1]
},
{
 "id": 2,
 "name": "Bob",
 "city": "Dublin",
 "friends": [1, 3, 4]
},
{
 "id": 3,
 "name": "Charlie",
"city": "London",
"friends": [1, 2]
},
{
 "id": 4,
 "name": "Davina",
 "city": "Belfast",
 "friends": [2]
}

Input Data
File
Output Data
File and Console
Data Format
Json
Problem to resolve
Given the following example dataset
To Extract: 
-a user's friend list, 
- a users's suggested friends list (friend of a friend)
- a user's suggested friend list sorted by geography.
Aim to handle datasets reaching into the thousands as quickly as possible and with the smallest possible memory footprint.
Type of solution 
Java language (in order to no mix several technologies in a same project)
Json Parser CodeHaus Jackson in order to no use big amount of memory
