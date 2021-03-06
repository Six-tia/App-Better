The API is available online. Use it by sending your request to **https://api.boyang.me/{URI}**

All interfaces are tested. You could test them [online](https://reqbin.com/) or using [Postman](https://www.postman.com/) (or using curl in the command line).

Details are given below.

1. **/**

    ```
    URL: https://api.boyang.me/
    Method: GET
    Expected Return: {"code": 200, "result": "hi there"}
    ```

    The only function of this URI is to help you get your hands on RESTful API (or test your network connection). The number 200 means all is good. Basically, you could determine whether your request was successfully operated by the status code. You should never put anything into the HTTP request body when you use the GET method. The format of the message from the server would always be JSON.  

2. **/users/\<name\>/\<password\>**

    This interface is to allow a user to register or login.

    ```
    URL: https://api.boyang.me/users/<name>/<password>
    Method: GET, POST
    ```

    Use POST to register. For instance:

    ```
    curl -X POST https://api.boyang.me/users/test/testpassword
    ```

    This code is to register a user "test" with password "testpassword".

    Expected Return:

    ```
    {"code": 200, "result": "user registered successfully"}
    ```

    Other possible return:

    ```
    {"code": 304, "result": "user already exists"}
    ```

    Use GET to login.

    ```
    curl -X GET https://api.boyang.me/users/test/testpassword
    ```

    Expected Return:

    ```
    {"code": 200, "result": "user login successfully", "token": "7b873e9eeb11ced251401d1ed683ae3c"}
    ```

    Attach this token to every request below to identify the user. Please note that the token would differ every time you make a new request, so you do NOT need to save it permanently.

    Other possible return:

    ```
    {"code": 404, "result": "user does not exist"}
    ```

    or

    ```
    {"code": 403, "result": "wrong password"}
    ```

3. **/test/\<token\>**

    This interface is a temporary one, aiming to let you know you are using the token correctly.
    UPDATE: In order to make debugging convenient, this interface would not be deleted.

    ```
    URL: https://api.boyang.me/test/<token>
    Method: GET
    ```

    Use GET to check the username corresponding to this token.

    Expected Return:

    ```
    {"code": 200, "result": "hi, <username>!"}
    ```

    Other possible return:

    ```
    {"code": 403, "result": "invalid token"}
    ```

4. **/state/\<token\>**

    This interface is to get the state of a user. The initial value would be "NULL". You can change it to whatever value you like (using interface 5).

    ```
    URL: https://api.boyang.me/state/<token>
    Method: GET
    ```

    Expected Return:

    ```
    {"code": 200, "state": "NULL"}
    ```

    Other possible return:

    ```
    {"code": 403, "result": "invalid token"}
    ```

5. **/state/\<newstate\>/\<token\>**

    This interface is to change the state of a user.

    ```
    URL: https://api.boyang.me/state/<newstate>/<token>
    Method: PUT
    ```

    Expected Return:
    ```
    {"code": 200, "result": "state updated successfully"}
    ```

    Other possible return:

    ```
    {"code": 403, "result": "invalid token"}
    ```

6. **/todolist/\<token\>**

    This interface is to get all todolist data of a user. The initial value would be an empty dictionary structure. You can always add a new one (using interface 7).

    ```
    URL: https://api.boyang.me/todolist/<token>
    Method: GET
    ```

    Expected Return:

    ```
    {"code": 200, "todolist": {"160232941433": {"content": "content 1", "state": "state 1"}, "160232943834": {"content": "content 2", "state": "state 2"}}}
    ```

    Other possible return:

    ```
    {"code": 403, "result": "invalid token"}
    ```

7. **/todolist/\<content\>/\<state\>/\<token\>**

    This interface is to add a new todolist item. Noted that the id would be generated by server automatically so you do NOT need to specify one. 

    ```
    URL: https://api.boyang.me/todolist/<content>/<state>/<token>
    Method: POST
    ```

    Expected Return:

    ```
    {"code": 200, "result": "todolist created successfully"}
    ```

    Other possible return:

    ```
    {"code": 403, "result": "invalid token"}
    ```

8. **/todolist/\<todolistid\>/\<content\>/\<state\>/\<token\>**

    This interface is to update an existing todolist item by replacing the old value with the new ones. 

    ```
    URL: https://api.boyang.me/todolist/<todolistid>/<content>/<state>/<token>
    Method: PUT
    ```

    Expected Return:

    ```
    {"code": 200, "result": "todolist updated successfully"}
    ```

    Other possible return:

    ```
    {"code": 404, "result": "wrong id"}
    ```

    or

    ```
    {"code": 403, "result": "invalid token"}
    ```

9. **/todolist/\<todolistid\>/\<token\>**

    This interface is to delete an existing todolist item.

    ```
    URL: https://api.boyang.me/todolist/<todolistid>/<token>
    Method: DELETE
    ```

    Expected Return:

    ```
    {"code": 200, "result": "todolist deleted successfully"}
    ```

    Other possible return:

    ```
    {"code": 404, "result": "wrong id"}
    ```

    or

    ```
    {"code": 403, "result": "invalid token"}
    ```

10. **/memo/\<token\>**

    This interface is to get all memo data of a user. It resembles interface 6.

    ```
    URL: https://api.boyang.me/memo/<token>
    Method: GET
    ```

    Expected Return:

    ```
    {"code": 200, "memo": {}}
    ```

    Other possible return:

    ```
    {"code": 403, "result": "invalid token"}
    ```

11. **/memo/\<title\>/\<content\>/\<token\>**

    This interface is to add a new memo item. It resembles interface 7.

    ```
    URL: https://api.boyang.me/memo/<title>/<content>/<token>
    Method: POST
    ```

    Expected Return:

    ```
    {"code": 200, "result": "memo created successfully"}
    ```

    Other possible return:

    ```
    {"code": 403, "result": "invalid token"}
    ```

12. **/memo/\<memoid\>/\<title\>/\<content\>/\<token\>**

     This interface is to update an existing memo item. It resembles interface 8.

    ```
    URL: https://api.boyang.me/memo/<memoid>/<title>/<content>/<token>
    Method: PUT
    ```

    Expected Return:

    ```
    {"code": 200, "result": "memo updated successfully"}
    ```

    Other possible return:

    ```
    {"code": 404, "result": "wrong id"}
    ```

    or

    ```
    {"code": 403, "result": "invalid token"}

13. **/memo/\<memoid\>/\<token\>**

    This interface is to delete an existing memo item. It resembles interface 9.

    ```
    URL: https://api.boyang.me/memo/<memoid>/<token>
    Method: DELETE
    ```

    Expected Return:

    ```
    {"code": 200, "result": "memo deleted successfully"}
    ```

    Other possible return:

    ```
    {"code": 404, "result": "wrong id"}
    ```

    or

    ```
    {"code": 403, "result": "invalid token"}
    ```