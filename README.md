# Marketo-Merge-Duplicate-Lead-s-Data
A dynamic web project to import the marketo's lead database in to local system. Find out the records with the duplicate Email and merge them in marketo

# A Project Documentation for "Merge Duplicate Leads in Marketo"

## Project Description:
The project aims at merging the duplicate lead records in the marketo (Two records having the same email address) using the user friendly interface. Before merging the records with same email address their sfdc types are checked, and records having the same sfdc types and same email address are only merged.
This is done by importing the part of lead database from the marketo in to the local system's database (MySQL Database) and cheking for duplicates. Marketo by default allows only a month's lead data to be imported on a signle import operation. 

## Dependencies:
-> Java (minimum java version reqd. : 6)
-> Database: MySQL. This application assumes database user name as : "root" and password as ""
-> Apache Tomcat WebServer (Version 6 or higher)
-> Access to file system of the machine (to create the file and read it while importing it in the MySQL Database)
-> Web Browser (Recommended : Chrome (version 72.x))
-> Access to Marketo API user (Client ID and Client Secret) 

## The project is divided in to following modules:
1) Import the Data from Marketo's lead database in to Local system
2) Select the Duplicate records
3) Merge the records
4) Clean-up process

All the modules' architecture and implementation is described below:

### Module 1:
-> Import the data from Marketo's lead database in to the local system
The UI has a date picker element which can be used to select the start and the end dates for importing the records from the marketo's database. As mentioned earlier in the introduction maximum odf 31 days' data can be imported from the marketo's lead database in one go. The application makes use of Marketo's bulk export API for exporting the data from marketo's lead database. The data is imported in the  steps as explained below:

1) Create a data export Job: 
A post request has to be sent to the marketo for creation of bulk export job. The body for the post request is sent in the JSOn format and includes the following details:
a) API names of the fields to be imported from the marketo's lead database (eg firstName, lastName, email, SfdcType etc.)
b) format in which data is to be imported from the marketo (we have standardised the use of CSV formats through out the project)
c) Start Date and the end Date for the data import.
Body is auto-generated as per the start date and end date selected by the user and the user does not need to change the body. Following fieds are imported from the lead database :
i)   LeadID 
ii)  Lead first name
iii) Lead last name
iv)  Lead Email 
v)   Lead Country 
vi)  Lead sfdc type 

2) Starting the job: 
A POST request needs to be sent to the marketo in order to enque the created job in the earlier step.

3) Getting the job status:
Once the job has been enqueued, we retrive the job status by sending the GET request with job's export id to get the job status. 

4) Data Retrival
Once the job gets completed we issue a GET request using the exportid of the job to geth the data back in the CSV format.

Once the data is retrived in the form of a GET request a CSV file named: *LeadDataJava.txt* in the user directory. A table in the name of *lead_information* is created in the *test* database and the data from the CSV file *LeadDataJava.txt* is imported in to the table. 

All the above steps of issuing the multiple GET and POST request for importing the data and writing it in the mysql has been automated and user only needs to select the dates start and end date for which he desires to import the data and hit the "Import Data" button on the UI. 

### Module 2:
-> Get the duplicates:
Responsible for selecting all the records having the same email address and displaing them in the form of a table in the UI.

### Module 3:
-> Merge the Duplicate Records:
There are namely two ways in which duplicate records can be merged using the application:
a) Manually selecting the records that are to be merged
One can use the check-boxes to select the records that they wish to merge. However before merging any record with another record(s) following conditions are checked:
i) All the selected records must have same email address
ii) All the selected records should either have the sfdc type as null (not synced with salesforce) or they should have same sfdc type as either *Customer* or *Lead*
Select the records that you wish to merge and select the Merge Records button to merge them. 
b) Let the application select all the Duplicate records automatically andMerge them.

If the records are merged successfully in the marketo as well a status code *True* is returend from the marketo's server and "Lead Records Merged Successfully" is displayed in the UI. 
Incase the selected records don't have same Email addresses then an Error message: *Mail IDs are not same* is printed.
Incase the selected records don't have same SFDC types the then an Error message: *SFDC type for all the records are not same* is printed.

The records that are merged in the marketo are deleted in the local MYSQL database as well just to maintain the data consistency between the marketo and the local database.

### Module 4: 
Clean Up process: 
Clean up removes the created CSV file ("LeadDataJava") from the system and also deletes the table created "lead_information" while importing the lead data from the marketo. "Exit the application" button when pressed does the clean up process in the back-end.


## Snapshots:

