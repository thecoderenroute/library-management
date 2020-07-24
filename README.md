# library-management
Menu-driven System to manage books at home or in a library

This is a menu driven library management system which stores data in .txt files.

You can add books, remove books, and edit them. The books added will remain saved even after you have closed the program. 
You can search and filter books.

Each book is assigned a name, an Author, a publication year, a status, a location and a computer-generated id. Any of these can be edited any time.
There have to a be a pre-defned set of status' and locations. These have to be defined before you add books. 

You can also, add directly to the books.txt file following the following format:

id, name, author, publication year, status, location

The Author must have a period between first name, last name, and middle. (if you choose to add all 3) You can also have just last name and just lastname and first name.

Setting any status or location other than the pre-defined set will lead to their status/ location being re-named to "N/A".

ID's must be unique

You can add directly to the configs.txt file directly by following the following format:

status1, status2,status3
location1, location2

You can have any number of status' or locations but you must have atleat one.
