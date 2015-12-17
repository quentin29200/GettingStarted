# Project management – Software Development Project
## « OpenCompare and configurable HTML export »

### 1. Objectives

Opencompare.org has for objectives to help a community of users to import, edit, view and use array of products in a given domain.

The purpose of this project is to generate, from a comparison matrix template, an HTML representation. This export is highly configurable because we want to handle different graphic aspects of the matrix, for example the ability to:
* add colors on the headers of the features
* add colors on certain types of value (color "yes" boolean value in green)
* reverse the matrix (the products are displayed at the top instead of being displayed on the left side)
 
In terms of web technology, it uses HTML5, CSS and Javascript. The solutions runs on any devices (cellphones, tablets, computers, etc ...). The procedure takes as input a comparison matrix, some configuration items an generates taticlly a set of HTML, CSS and Javascript files. The result is usable on any browser.

### 2. Result




### 3. License

The project OpenCompare was, before the beginning of this project, accessible [here](https://github.com/OpenCompare/OpenCompare) and was provide with an Apache license file. The export process with follow the same license. To know more about it, you can read the license file [here](https://github.com/quentin29200/GettingStarted/blob/master/LICENSE).

### 4. Technologies used

During this project, we worked on several technologies and several languages. In the one hand, the languages. Java was the base of our development process. It handles the back-end with the current management of the OpenCompare project. It is used to generate the custom parameters of the user in a JSON file and to create an archive to put all the files in. Then, the objective of the project was to generate HTML and CSS file so we handle those languages too. 

In the other hand, the technologies. To improve the productivity of all the developement team, IntelliJ was the chosen integrated development environment. It permits to handle easily the Maven project and provides an internal VCS. Concretely, Maven was the most useful tool during this project. It successfully manages the dependencies and avoid some library version problems. To parse the JSON, we did it manually. We were thinking about using the JSOUP library but it wasn't working like we wanted to. For the CSS, we used the GENESIS library. It creates CSS "module" to generate CSS classes thanks to a JSON parameter/file. It's powerful and easily maintanable. Then, we prefered JUnit to Mockito for the tests management because we've studied it during our courses.

We would like to add something about this. We know that we could automate tasks with Travis (which is already implemented in the project with its .travis.yml) but nobody of the team knew to use it or was able to implement it. The lack of time prevented us from using this tool that could, it's true, save us time.



### 5. Project architecture

 idea/
  Some files of the IDE we use (IntelliJ)
  pcms
   PCM1
     info1.txt
     params1.json
     params2.json
     params3.json
     params4.json
     tesssvtttt369852147.pcm
    PCM2
     alternative-parsers-mediawiki.pcm
     info2.txt
     param1.json
     param2.json
     param3.json
     param4.json
    PCM3
     index1.html
     info3.txt
     param1.json
     param2.json
     param3.json
     param4.json
     test-matrice-simple.pcm
    PCM4
     info4.txt
     param1.json
     param2.json
     param3.json
     param4.json
    TEST
     TESTCSS
      style1.css
      style2.css
      style3.css
      style4.css
      comparison-nikon-dslr.pcm
     index1.html
     info.txt



### 6. Deployment instructions
