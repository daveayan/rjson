=========
| RJSON |
=========

What is rjson?
rjson is a object to json mapping framework that extensively uses reflection. It allows to serialze java objects to json
and deserialize json to java objects.

Why we need another framework?
While there are many frameworks out there that do the same they rely on getter and setter methods for private and protected
fields. rjson will serialize java objects by looking straight at the fields and using reflection to access them if they are
private or protected

How to use rjson?
Best place to look for examples is the unit tests for rjson. These are located at https://github.com/daveayan/rjson/tree/master/src/test/java/rjson
More tutorial material coming.

How to extend rjson?
Best place to look for examples is the unit tests for rjson. These are located at https://github.com/daveayan/rjson/tree/master/src/test/java/rjson
More tutorial material coming.

Can I modify rjson code to suite my own needs?
rjson is available under MIT Licence. The licence.txt is easy to read and follow, however the gist of it is as follows.
The rjson code is freely available for use as long as:
- The copyright notice is included in all distributions
- The code is used for something good and not evil