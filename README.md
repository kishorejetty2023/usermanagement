# UserManagement

_This Application is basic user management application_

### Run Docker images

_This app is enabled with docker, the images are created and may be available in public store._

__Running the application__

Try to run :

    docker run -d -p9090:9090 -e SPRING_PROFILES_ACTIVE=local --name <AppName with which you want to run the docker> <ImageId/name of the docker created>

    -d ==> Represents to run it in detached mode.
    -p9090:9090 ==> Represents the portOfContainer:PortOnWhichApplicationIsRunning
    -e ==> represents the arguments that we want to pass it to the jar that is going to run.


Example:

    docker run -d -p9090:9090 -p8083:8083 -e SPRING_PROFILES_ACTIVE=local -e MY_SQL_URL=host.docker.internal --name usermanagement 88aed3085de4

In the above command we have pass two ports since we are using separate
ports for management endpoints i.e. we are having separate
ports for actuators.


To Use
JASYPT: Java Simplified Encryption
----------------------------------

### Jasypt (Java Simplified Encryption) Θ!
Jasypt is a java library which allows the developer to add basic encryption capabilities to his/her projects with minimum effort and without the need of having deep knowledge on how cryptography works.

1. JASYPT Jar for distribution Download: [jasypt 1.9.3 (binaries and javadocs)](https://github.com/jasypt/jasypt/releases/download/jasypt-1.9.3/jasypt-1.9.3-dist.zip)
      - _This dist will contain all jars that you need for your encryption, out of which we only use jasypt-1.9.3.jar_
      - _To use Jasypt encrypt:_
        - _Download above dist._
        - _Unzip the dist file._
        - _Go to lib directory._
        - _Open cmd from that folder._
        - _Now use below command._
          - java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="TextThatNeedToBeEncrypted" password="YourEncryptionKey" algorithm=PBEWITHHMACSHA512ANDAES_256 ivGeneratorClassName=org.jasypt.iv.RandomIvGenerator saltGeneratorClassName=org.jasypt.salt.RandomSaltGenerator stringOutputType=base64
            ![Alt text](/jasyptExecutionImage.jpg?raw=true "Execution")
          - Now add this configuration in your application yml. The password here is your EncryptyionKey we have used above.

            ![Alt text](/jasyptOutputImage.jpg?raw=true "Output")
          - Now just make sure that you copy the output string and place it the place of password with in **_ENC(paswordBase64Text)_** from above command.
          - _Example: output text from above image need to be copied to password place with ENC()_

          

      user:
          datasource:
              password: ENC(OtYoP4wDerEhzp16Lss3IqIAi5fnrw0UnUakF+jnZKbrmHezMvSvoru1C8Ke5ONLTkh0VT/hCZL/4AZ7BROrFg==)
    
              
          
<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://user-images.githubusercontent.com/25423296/163456776-7f95b81a-f1ed-45f7-b7ab-8fa810d529fa.png">
  <source media="(prefers-color-scheme: light)" srcset="https://user-images.githubusercontent.com/25423296/163456779-a8556205-d0a5-45e2-ac17-42d089e3c3f8.png">
  <img alt="Shows an illustrated sun in light mode and a moon with stars in dark mode." src="https://user-images.githubusercontent.com/25423296/163456779-a8556205-d0a5-45e2-ac17-42d089e3c3f8.png">
</picture>