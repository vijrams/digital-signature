# Digital Signature Microservice
[![Build Status](https://dev.azure.com/GRC-ELM/Collaboration%20Portal/_apis/build/status/CPS-DigitalSignature?branchName=main)](https://dev.azure.com/GRC-ELM/Collaboration%20Portal/_build/latest?definitionId=148&branchName=main)
[![version](https://img.shields.io/badge/Ver-1.5.0.snapshot-blue.svg)]()

This project uses Spring Boot to implement the service that
signs invoice files and validates the signed invoice file

## Features

- Uses embedded Tomcat 8
- Signs invoice files
- validates signed invoices file
- supports legacy sd3 app endpoints
- Almost all source is Groovy

## Endpoints

* <code>GET</code> `/sign`

```
Paramaters:
1. invoice         (required), Type = File
2. senderCountry   (required), Type = String
3. clientCountry   (required), Type = String
4. senderLawId     (required), Type = String
5. clientLawId     (required), Type = String
6. referenceId     (required), Type = String
7. invoiceCount    (required), Type = Integer
```

* <code>GET</code> `/validate`

```
Paramaters:
1. signedInvoice   (required), Type = File
2. senderCountry   (required), Type = String
3. clientCountry   (required), Type = String
```

* <code>GET</code> `/validateArchive`

```
Paramaters:
1. signedInvoice   (required), Type = File
```


* <code>POST</code> `/digital_signatures/validate`

```
Paramaters:
1. file                 (required), Type = File
2. sender_country       (required), Type = String
3. recipient_country    (required), Type = String
4. law_id               (required), Type = String
```

* <code>POST</code> `/digital_signatures/validate_archive`

```
Paramaters:
1. file                 (required), Type = File
```

## Generate Stubs

To generate wsdl stubs run **gradle wsimport**
* This will generate new stubs from the service
* make sure you fix the SwitchService.java to use url from application.yml file
* you can compare the diff and update the changes

## Notes

This project require Gradle 7.0 to build.

* To build the project, from the root directory run: **gradle clean build**
* To upload the jar to nexus, from the root directory run : **gradle dist upload**
* To run junit tests , from the root directory run : **gradle test**
* To run the application, from the root directory run : **gradle bootrun**