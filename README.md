*Table of Contents*  

- [Generic-Testdata-Framework](#generic-testdata-framework)
	- [About this Document](#about-this-document)
	- [Download](#download)
	- [Components](#components)
- [Conceptual Usage Guide](#conceptual-usage-guide)
- [Technical Usage Guide](#technical-usage-guide)
	- [Usage](#usage)
		- [Argument File](#argument-file)
		- [Java](#java)
		- [Ant](#ant)
	- [Metadata, Templates & Configuration](#metadata-templates--configuration)
		- [Metadata](#metadata)
		- [Templates](#templates)
		- [Mapping CSV-Files to Metadata-Files](#mapping-csv-files-to-metadata-files)

- - -

Generic-Testdata-Framework
==========================

The _Generic Testdata Framework_ is designed to work on top of the 
[Robot Test Automation Framework](http://code.google.com/p/robotframework/).

The major goal of this framework is to enable better cooperation between technical team members and functional
specialists of a software development team. This means that:

1. Technical experts should write keywords and combine them to possible _Test Scenarios_.
2. Functional specialists should have an easy (not too technical) way of specifying new _Tests_ based on those existing scenarios.

Let's take a look at an example from an insurance company where customers have the possibility to enter data on their car
using a web application provided by the insurance company. Of course this should be thoroughly tested as mistakes here 
might easily result in some unhappy customers (and bosses).

One _Test Scenario_ here could be filling all possible fields with corresponding values (type of car, age of driver, etc.).
This needs to be technically enabled by implementing keywords and an order in which those keywords are executed. In 
addition at least one keyword would be required to check that the calculated result is correct. 

The _Tests_ now would fill this scenario with life. Probably an insurance company would have a quite big amount of
possible tests checking that various possible combinations are working. By using the _Generic Testdata Framework_
functional specialists are enabled to implement _Tests_ using some kind of GUI without the need to know anything
about the underlying technical implementation.

Actually the supported GUI is Excel for editing and writing new _Tests_ using existing _Test Scenarios_. In the 
long run there will also be a web frontend, but as that one is work in progress it is not yet described here.


About this Document
-------------------

This document contains the complete documentation of the _Generic Testdata Framework_. It is divided
into the following main chapters:

- The chapter you are currently reading contains an introduction on the _Generic Testdata Framework_, the [download section](#download) and an overview on the different [components](#components) this framework is composed of.
- The [Conceptual Usage Guide](#conceptual-usage-guide) describes the ideas and concepts behind this framework.
- The [Technical Usage Guide](#technical-usage-guide) describes how to implement tests using the _Generic Testdata Framework_ together with the _Robot Framework_.



Download
--------

The most recent version of the _Generic Testdata Framework_ can be downloaded from here:

[http://code.google.com/p/generic-testdata-framework/downloads/list](http://code.google.com/p/generic-testdata-framework/downloads/list)

The corresponding release notes for every version can be found [from this page](https://github.com/ThomasJaspers/Generic-Testdata-Framework/wiki/Releases).


Components
----------

The downloaded *robot_gtf* ZIP-file contains the following components:

1. JAR-file implementing the _Generic Testdata Framework_ core functionality.
2. An Excel-based example that can be used as a template for a new project.



Conceptual Usage Guide
======================

The following figure depicts pretty well the most basic concept and idea behind this framework. This is to have a 
clear division between the (technical) implementation of the tests and the (functional) implementation of individual
testcases. Using the differnt input files (input data) the _Generic Testdata Framework_ then generates complete
_Robot Framework_ testsuite files.

![Conceptual Overview](https://raw.github.com/ThomasJaspers/Generic-Testdata-Framework/master/gtf/doc/ConceptualOverview.png)

Therefore so-called _Test Scenarios_ are implemented using a template- and metadata-driven approach. Certainly it
makes sense to develop these _Test Scenarios_ together with the functional experts or at least get the required
input from them. Once the implementation is done different _Tests_ can be easily added by filling in the required
parameters into the different _Test Scenarios_. The following screenshot shows an Excel-file for one _Tests_-file
provided in the example that comes along with the _Generic Testdata Framework_.

![Excel Example](https://raw.github.com/ThomasJaspers/Generic-Testdata-Framework/master/gtf/doc/ExcelSample.png)

As can be seen from the screenshot in the Excel-Sheet it is possible to focus to a great deal on the _Tests_.
Basically it is not at all possible to see how the tests are implemented in the end. It is mandatory to
define in each row the _Test Scenario_ that should be used. Then the name of the test case and a description
can be given to have this information available later on in the Robot Framework report file. And it is of course 
also helpful for structuring the _Tests_ in the Excel-file.
It would be also possible to add _Robot Framework_ tags this way. 
But none of this information is really mandatory beside the name of the _Test Scenario_ and the parameters to the test.

In order to allow the functional specialists to write new tests on their own it would be very beneficial (well,
basically mandatory) to define the available _Test Scenarios_ somewhere together with the meaning of the used
parameters. On the other hand such a description would be anyway good to have to document the possible tests.
It can be seen from the screenshot that some color coding can be used in Excel to make the different purposes of
the different columns more clear. It is also possible to define comment lines by using a "##" in the beginning of
the first cell and to use empty lines for formatting purposes.


Technical Usage Guide
=====================

This part of the documentation is split into explaining how to startup the framework using Java or Ant
and passing in initial arguments. This is followed by an explanation of how to write required 
_Metadata_- and _Template_-files. Those parts can be best checked together with the provided example.

Usage
-----

The _Generic Testdata Framework_ is designed to be started from the command line. 
There are two ways to do so that are supported out-of-the-box:

1. Using Java or
2. Using Ant (currently being implemented, not available yet).

Especially the Ant-Script that is bundled with the download package might be very useful as it does not only 
support starting the _Generic Testdata Framework_, but also the _Robot Framework_. 
No matter which way is used to start the tool, there is only one command line option available, which points 
to an _Argument_-file that contains all required options. This way one can easily configure different 
startup-scenarios and share them among the team (e.g. by checking the _Argument_-file into some version control system).

The following chapter is explaining the content of the _Argument_-file. Afterwards the startup using pure 
Java is explained and then the integration with Ant.

### Argument File

The _Argument_-file contains mandatory and optional parameters that must (can) be passed to the GTF-Tool.
Thus the only command line argument that is accepted is the path to such an _Argument_-file. 
It can have any name, but it must have a proper syntax for [Java property files](http://en.wikipedia.org/wiki/.properties) 
(which is not too complicated to achieve ;)). The following list defines the possible arguments:

* **ConfigurationDirectory** - This is the directory that contains the metadata defintions as well as the template files. 
* **XlsDirectory** - This directory contains the Excel-files that are used as an input to generate the individual Testsuite-Files containing then all the corresponding testcases from such a XSL-File.
* **TestsuiteDirectory** - The resulting Testsuite-Files are generated into this directory.
* **InputType** - Define the input type, currently supported XLS. If no input type is given XLS is assumed. This is designed for future use when also database will be supported as an input type.

The following shows an example of an argument file:

	ConfigurationDirectory = c:\gtf-sample\config  
	XlsDirectory = c:\gtf-sample\xsl
	TestsuiteDirectory = c:\gtf-sample\testsuite  

It should be noted that for the Configuration Directory this results in the following two sub-directories:

* c:\gtf-sample\config\metadata
* c:\gtf-sample\config\template

The main idea of having all arguments bundled in one file is to be able to share the same _Argument_-file inside a 
team and project. Furthermore this makes it easily possible to have different ready-made files for different
environments (local, CI-environment, etc.).


### Java
To execute the _Generic Testdata Framework_ just issue the following command from the directory where the
__robot_gtf.jar__ is located (or add the corresponding path information).

	java -jar robot_gtf.jar sampleArguments.txt

The above example for starting the tool also assumes that the arguments-file is located in the same directory.
This is true for the example that is bundled together with the download. But of course you will adapt this to
the directory structure of your project. Nevertheless there is one recommendation regarding this which applies
to the _Robot Framework_ as well: Always try to setup projects in a way that only relative path information - starting 
from a root-directory - is used. This makes it much easier to share the project in the team and between 
different environments without too big changes required.


### Ant

Please [download](http://ant.apache.org/bindownload.cgi) and [install](http://ant.apache.org/manual/index.html) Ant 
before continuing here.

Ant and the provided build-file allows to easily start the _Generic Testdata Framework_ on different environments
using the same commands. Furthermore it is possible to extend the script to start the _Robot Framework_ (and
additional tool like selenium) all from one script. It is assumed that the Ant-script is executed from the
directory that contains the _Argument_-file and the required directory structure below. (Otherwise the script
must be adepted.) 

For each new project using the _Generic Testdata Framework_ you will probably anyway create a customized copy
of the provided Ant-script. At least the name (or path to) the _Argument_-file must be changed in this one:

	<!-- Configuration of the Generic Testdata Framework -->
	<property name="robot.gtf.options" value="sampleArguments.txt"/>
            
Basically then it is possible to start the framework by just issuing:

	ant -f robot_gtf_ant.xml
             

Metadata & Templates
--------------------

As we have seen in the [Conceptual Usage Guide](#conceptual-usage-guide) the main implementation task for
the _Test Scenarios_ is done in metadata-definitions and templates.

The very basic concept is that every _Test_ is based on one _Test Scenario_. Basically there must one set of metadata-
and template-files for each _Test Scenario_. The _Generic Testdata Framework_ is using the name of a _Test Scenario_
to locate the corresponding _Metadata_-file. The _Metadata_-file then in turn contains information on the test-case templates 
to be used. But there are two more kind of templates: header and footer. Those are not determinded by the metadata,
but by the directory structure of the _Test Scenarios_. This will be explained a little bit later. For the time being:

* A header-template that contains required imports of keyword libraries and potentially some setup- and tear-down keywords.
* A footer-template which is most of the time empty if the TXT-format of the _Robot Framework_ is used.
* The testcase-template that defines the test scenario by implementing one (kind of) Robot Test using variables in those places where values from the real tests must be used.

Due to the way header- and footer-templates are determined it will be always so that the same 
header- and footer-templates are used for a whole set of testcase-templates.


### Metadata

As mentioned there must be one _Metadata_-file provided for each test scenario.  
_Metadata_-files are as well defined as [Java property files](http://en.wikipedia.org/wiki/.properties). 
They have two different kind of entries. 

* **TestcaseTemplateFileName** - Name of the Testcase template file to be used.
* **TestsuiteFilePostfix** - Postfix to be used for generated testsuite files.* 

If the entry for the **TestsuiteFilePostfix** is ommitted "txt" is used as the default.

The second part of the _Metadata_-file does not consist of fixed parameters, but is is a mapping from
variable names used in the _Template_-files to column positions - for that _Test Scenario_ - in the corresponding
Excel-file.

Let's take a look at an example as this is always the easiest way to better understand things:

	Testcase_Name = 2
	Testcase_Documentation = 3
	Param_1 = 4
	Param_2 = 5
	Param_3 = 6
	Expected_Result = 7

This means that in the referenced template file there are expressions used like %Testcase_Name%, %Testcase_Documentation%,
%Param_1% and so on. At the same time we recognice those parameters from the screenshot of the Excel-file defining
the _Tests_ that have been shown above in the [Conceptual Usage Guide](#conceptual-usage-guide). Here it is 
important to note that the (commented) headings used there can (and to some extend should) re-use the names
of the parameters used here, but that has nothing to do with how the _Generic Testdata Framework_ works. The 
only information used is the column number from which the parameter names and values are being used.

This leads us to the last missing item: The template files.


### Templates

The templates are basically parts of a Robot Framework testsuite. In principle it does not even matter which format
is used here, even though it is strongly recommended to use the TXT-format.

The _Template_-files are referenced by the corresponing _Metadata_-files and must thus have the corresponding names.

The templates are simply implementing one kind of test - thus a _Test Scenario_ - using whatever Robot Framework
keywords are required to do so. The used libraries are imported in a template header-file. As mentioned before
the header- and footer-files are the same for all _Template_-files in one sub-directory below the **XLSDirectory**
configured in the _Argument_-file.

Let's take a look to the _Template_-file that is matching the configuration from the previous chapter:

	%Testcase_Name%
	[Documentation]    %Testcase_Documentation%
	${RESULT}=    Calculate Base Function     %Param_1%    %Param_2%    %Param_3%
	Result should be     %Expected_Result%    ${RESULT}  
	
It can be easily seen that the strucutre of the file is simply one Robot Framework testcase. Some high-level
keyword "Calculate Base Functions" is obviously implementing the major parts of the test functionality and 
it is getting then the corresponding variable parameters which will then be filled from the Excel-file when
the _Generic Testdata Framework_ is executed. As the whole section will be repeated for each row
(of that _Test Scenario_) from the Excel-file it makes sense to define the testcase name and description
as well as variables to have proper values for those later on in the report generated by the Robot Framework.











