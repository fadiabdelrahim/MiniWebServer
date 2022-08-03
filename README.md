# MiniWebServer

Point your browser to:

http://localhost:2540/  or...
http://localhost:2540/WebAdd.fake-cgi  or...
http://localhost:2540/any/string/you/want.abc

...to get a response back. Keep in mind there may be FavIco requests.

Use the WebAdd.html form to submit a query string to WebResponse, based on
the input to the form. You can probably "click on" the file in your
directory. Locally it will have a URL of something like:

file:///C:/Users/Fadi/WebServer/java/MiniWebserver/WebAdd.html

You should see:

Hello Browser World N
...along with some request information.


You can use the Firefox console (control-shift E / Network / Inspector) to
see the Internet traffic. (Note: drag the top line up to give a bigger console
window.)

HTML Reference site:
https://www.w3schools.com/

You may find that including the following in your HTML header helps with
facivon problems:

<head> <link rel="icon" href="data:,"> </head>

https://stackoverflow.com/questions/1321878/how-to-prevent-favicon-ico-requests
