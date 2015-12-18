# Project management – Software Development Project
## « OpenCompare and configurable HTML export »

### 1. Objectives

Opencompare.org has for objectives to help a community of users to import, edit, view and use array of products in a given domain.

The purpose of this project is to generate, an HTML representation, from a comparison matrix template. This export is highly configurable because we want to handle different graphic aspects of the matrix. For example the possibility to:
* add colors on the headers of the features
* add colors on certain types of value (color "yes" boolean value in green)
* reverse the matrix (the products are displayed at the top instead of being displayed on the left side)
 
In terms of web technology, we use HTML5, CSS and Javascript. The solution runs on any devices (cellphones, tablets, computers, etc ...). The procedure takes as input a comparison matrix, some configuration items and generates taticlly a set of HTML, CSS and Javascript files. The result is usable on any browser.

### 2. Result
Since the beginning of the project, we developed several things. Indeed, we first created 2 classes : Param and DataStyle. Objects of Param class contains a collection of DataStyle. This class allows us to get the JSON data with text color or background information,  from a JSON file created by us. <u>For one JSON file, there are several DataStyle created owned <b> to </b> this structure</u>. Param also contains attributes to describe the matrix ; if the name of the PCM is shown, or if the features' name are shown. Those classes are used in an other class, CSSExporter - the second point developed, for the generation of css files. CSSExporter also use Genesis librairy.

Thirdly, we developed HTMLExporterCustom, which looks like the HTMLExporter file which already exists in the project. It's used for the generation of HTML file, and the creation of the matrix from OpenCompare. Param class is an attribute in this class. Param put some coloration on the matrix, and on specifics data, and also change the matrix's look. Futhermore, the exportation is working, the CSS file and the HTML file are generated and are put into an archive zip.

Functions had been developed in order to change the matrix's aspect. Now, there is a possibility to reverse the matrix : the products are at the bottom of the matrix, and the features are on the left. The name of the matrix can be show or not. If the matrix is big, the user can show the features at the bottom of it. <u>We can apply a sort by increasing or decreasing order on feature. Still on features, but with products too, functions rangein and rangeout permits to show data in accordance with a gap or with an exact value.</u>


### 3. License

The project OpenCompare was, before the beginning of this project, accessible [here](https://github.com/OpenCompare/OpenCompare) and was provide with an Apache license file. The export process with follow the same license. To know more about it, you can read the license file [here](https://github.com/quentin29200/GettingStarted/blob/master/LICENSE).

### 4. Technologies used

During this project, we worked on several technologies and several languages. In the one hand, the languages. Java was the base of our development process. It handles the back-end with the current management of the OpenCompare project. It is used to generate the custom parameters of the user in a JSON file and to create an archive to put all the files in. Then, the objective of the project was to generate HTML and CSS file so we handle those languages too. 

In the other hand, the technologies. To improve the productivity of all the developement team, IntelliJ was the chosen integrated development environment. It permits to handle easily the Maven project and provides an internal VCS. Concretely, Maven was the most useful tool during this project. It successfully manages the dependencies and avoid some library version problems. To parse the JSON, we did it manually. We were thinking about using the JSOUP library but it wasn't working like we wanted to. For the CSS, we used the GENESIS library. It creates CSS "module" to generate CSS classes thanks to a JSON parameter/file. It's powerful and easily maintanable. Then, we prefered JUnit to Mockito for the tests management because we've studied it during our courses.

We would like to add something about this. We know that we could automate tasks with Travis (which is already implemented in the project with its .travis.yml) but nobody of the team knew to use it or was able to implement it. The lack of time prevented us from using this tool that could, it's true, save us time.



### 5. Project architecture

 <ul>
  <li>idea/</li>
  <ul>
    <li>Some files of the IDE we use (IntelliJ)</li>
  </ul>
  <li>pcms/</li>
  <ul>
    <li>PCM1</li>
    <ul>
      <li>info1.txt</li>
      <li>param1.json</li>
      <li>param2.json</li>
      <li>param3.json</li>
      <li>param4.json</li>
      <li>tesssvtttt369852147.pcm</li>
    </ul>
    <li>PCM2</li>
    <ul>
      <li>alternative-parsers-mediawiki.pcm</li>
      <li>info2.txt</li>
      <li>param1.json</li>
      <li>param2.json</li>
      <li>param3.json</li>
      <li>param4.json</li>
    </ul>
    <li>PCM3</li>
    <ul>
      <li>index1.html</li>
      <li>info3.txt</li>
      <li>param1.json</li>
      <li>param2.json</li>
      <li>param3.json</li>
      <li>param4.json</li>
      <li>test-matrice-simple.pcm</li>
    </ul>
    <li>PCM4</li>
    <ul>
      <li>info4.txt</li>
      <li>param1.json</li>
      <li>param2.json</li>
      <li>param3.json</li>
      <li>param4.json</li>
      <li>comparison-nikon-dslr.pcm</li>
    </ul>
    <li>TEST</li>
    <ul>
      <li>TESTCSS</li>
      <ul>
        <li>style1.c</li>
        <li>style1.css</li>
        <li>style2.css</li>
        <li>style3.css</li>
        <li>style4.css</li>
      </ul>
      <li>index1.html</li>
      <li>info1.txt</li>
      <li>param1.json</li>
      <li>param2.json</li>
      <li>param3.json</li>
      <li>param4.json</li>
      <li>tesssvtttt369852147.pcm</li>
    </ul>
  </ul>
  <li>src/</li>
  <ul>
    <li>main/</li>
    <ul>
      <li>java/</li>
      <ul>
        <li>org/</li>
        <ul>
          <li>opencompare/</li>
          <ul>
            <li>CSSExporter.java</li>
            <li>DataStyle.java</li>
            <li>HTMLExporterCustom.java</li>
            <li>MyPCMPrinter.java</li>
            <li>Param.java</li>
            <li>ProductOrder.java</li>
          </ul>
        </ul>
      </ul>
    </ul>
    <li>test/</li>
    <ul>
      <li>java/</li>
      <ul>
        <li>org/</li>
        <ul>
          <li>opencompare/</li>
          <ul>
            <li>MyPCMPrinterTest.java</li>
            <li>TestExport.java</li>
            <li>TestParam.java</li>
          </ul>
        </ul>
      </ul>
    </ul>
    <li>HTMLGenerated.html</li>
    <li>index.html</li>
    <li>index2.html</li>
    <li>style.css</li>
  </ul>
  <li>target/</li>
  <ul>
    <li>classes/</li>
    <ul>
      <li>org/</li>
      <ul>
        <li>opencompare/</li>
        <ul>
          <li>CSSExporter.class</li>
          <li>DataStyle.class</li>
          <li>HTMLExporterCustom.class</li>
          <li>MyPCMPrinter.class</li>
          <li>Param.class</li>
          <li>ProductOrder.class</li>
        </ul>
      </ul>
    </ul>
    <li>generated-sources/</li>
    <ul>
      <li>annotations/</li>
    </ul>
    <li>generated-test-sources/</li>
    <ul>
      <li>test-annotations/</li>
    </ul>
    <ul>
      <li>maven-archiver/</li>
      <ul>
        <li>pom.properties</li>
      </ul>
      <li>surefire/</li>
    </ul>
    <ul>
      <li>surefire-reports/</li>
      <ul>
        <li>org.opencompare.MyPCMPrinterTest.txt</li>
        <li>TEST-org.openCompare.MyPCMPrinterTest.xml</li>
      </ul>
    </ul>
    <li>test-classes/</li>
    <ul>
      <li>org/</li>
      <ul>
        <li>opencompare/</li>
        <ul>
          <li>TestMyPCMPrinter.class</li>
          <li>TestExport.class</li>
          <li>TestParam.class</li>
        </ul>
      </ul>
    </ul>
  </ul>
  <ul>
    <li>getting-started-1.0-SNAPSHOT.jar</li>
  </ul>
  <li>.gitignore</li>
  <li>.travis.yml</li>
  <li>getting-started.iml</li>
  <li>LICENSE</li>
  <li>pom.xml</li>
  <li>README.md</li>
</ul>


### 6. Deployment instructions
From the beginning of the project, we were told that, if the project was going well, it could be possible to integrate our work to the OpenCompare project. To do this, it will be necessary to develop an interface which will permits to the user to customize the matrix. It wasn't included in the things asked to this part of the work. When the user will submit his customization, the back-end will have to generate the JSON file corresponding to the parameters. Then, our main will get it and generate the files. The server will juste have to send thoses files to the user.
