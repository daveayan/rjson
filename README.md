Rjson
=====

What is this?
=============
Utility to serialize Java Objects to Json and deserialize Json to Java Objects

Why another framework?
======================
I like other json frameworks, flexjson and gson. However there are a few features that I don't find them all in one single framework. Hence Rjson was created. These features are:
- Use reflection and recursion to work with every single property in an object. Respect the transient properties.
- For application specific classes print the fully qualified class name in json as well with the key "className"
- Support to extend the framework for transformation helpers

How do I use it?
================
- Get an instance of Rjson object using newInstance()
- Use methods toJson or toObject to serialize or deserialize