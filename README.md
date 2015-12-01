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
      <li>style1.css</li>
      <li>style2.css</li>
      <li>style3.css</li>
      <li>style4.css</li>
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
      <li>style1.css</li>
      <li>style2.css</li>
      <li>style3.css</li>
      <li>style4.css</li>
    </ul>
    <li>PCM3</li>
    <ul>
      <li>info1.txt</li>
      <li>param1.json</li>
      <li>param2.json</li>
      <li>param3.json</li>
      <li>param4.json</li>
      <li>style1.css</li>
      <li>style2.css</li>
      <li>style3.css</li>
      <li>style4.css</li>
      <li>tesssvtttt369852147.pcm</li>
    </ul>
    <li>PCM4</li>
    <ul>
      <li>info1.txt</li>
      <li>param1.json</li>
      <li>param2.json</li>
      <li>param3.json</li>
      <li>param4.json</li>
      <li>style1.css</li>
      <li>style2.css</li>
      <li>style3.css</li>
      <li>style4.css</li>
      <li>tesssvtttt369852147.pcm</li>
    </ul>
    <li>TEST</li>
    <ul>
      <li>info1.txt</li>
      <li>param1.json</li>
      <li>param2.json</li>
      <li>param3.json</li>
      <li>param4.json</li>
      <li>style1.css</li>
      <li>style2.css</li>
      <li>style3.css</li>
      <li>style4.css</li>
      <li>tesssvtttt369852147.pcm</li>
    </ul>
  </ul>
  <li>src/</li>
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
    <li>test-classes/</li>
    <ul>
      <li>org/</li>
      <ul>
        <li>opencompare/</li>
        <ul>
          <li>TestMyPCMPrinter.class</li>
          <li>TestExport.class</li>
        </ul>
      </ul>
    </ul>
  </ul>
  <li>.gitignore</li>
  <li>.travis.yml</li>
  <li>getting-started.iml</li>
  <li>LICENSE</li>
  <li>pom.xml</li>
  <li>README.md</li>
</ul>

        * generated-sources/
            * annotations
        * generated-test-sources/
            * test-annotations
        * test-classes/
            * org/
                * opencompare/
                    * MyPCMPrinterTest.class
                    * TestExport.class
    * .gitignore
    * .travis.yml
    * getting-started.iml
    * LICENSE
    * pom.xml
    * README.md

### 6. Deployment instructions
